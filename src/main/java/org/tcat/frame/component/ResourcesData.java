package org.tcat.frame.component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tcat.frame.component.dto.ResourcesDto;
import org.tcat.frame.component.enums.ResourcesType;
import org.tcat.frame.service.gm.GmAdminRepository;
import org.tcat.frame.service.gm.GmRelAdminRoleRepository;
import org.tcat.frame.service.gm.GmRelRoleResourceRepository;
import org.tcat.frame.service.gm.GmRoleRepository;
import org.tcat.frame.service.gm.dto.AdminDto;
import org.tcat.frame.service.gm.dto.GmRelAdminRoleDto;
import org.tcat.frame.service.gm.dto.GmRelRoleResourceDto;
import org.tcat.frame.service.gm.dto.GmRoleDto;
import org.tcat.frame.service.gm.enums.AdminDisable;
import org.tcat.frame.service.gm.enums.AdminGrade;
import org.tcat.frame.util.PropertiesUtil;
import org.tcat.frame.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源管理
 * Created by Lin on 2017/8/24.
 */
@Component("resourcesData")
public final class ResourcesData implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String[] resources = {
            "system_management.yml"
    };
    private List<ResourcesDto> resourcesAll = new Vector<>();
    private List<ResourcesDto> resourcesView = new Vector<>();
    /**
     * key: resourcesUrl
     * <br/>value: resourcesId
     */
    private Map<String, String> resourcesUrlId = new ConcurrentHashMap<>();
    /**
     * key: resourcesId
     * <br/>value: resourcesUrl
     */
    private Map<String, String> resourcesIdUrl = new ConcurrentHashMap<>();
    /**
     * key: roleId
     * <br/>value: resourcesId
     */
    private Map<Long, Set<String>> roleResources = new ConcurrentHashMap<>();
    /**
     * key: resourcesId
     * <br/>value: roleId
     */
    private Map<String, Set<Long>> resourcesRole = new ConcurrentHashMap<>();
    /**
     * key: adminId
     * <br/>value: ResourcesDto
     */
    private Map<Long, List<ResourcesDto>> adminResourcesView = new ConcurrentHashMap<>();
    /**
     * key: adminId
     * <br/>value: roleId
     */
    private Map<Long, Set<Long>> adminIdRole = new ConcurrentHashMap<>();

    private static final String ADMINDTO = PropertiesUtil.getValue("config.properties", "resources.adminDto");

    @Autowired
    private GmRelRoleResourceRepository gmRelRoleResourceRepository;
    @Autowired
    private GmRoleRepository gmRoleRepository;
    @Autowired
    private GmAdminRepository gmAdminRepository;
    @Autowired
    private GmRelAdminRoleRepository gmRelAdminRoleRepository;
    @Autowired
    private CacheDate cacheDate;

    @Override
    public void afterPropertiesSet() throws Exception {
        initResources();
        initRoleResouces();
    }

    public synchronized void reInit() {
        initRoleResouces();
    }

    public synchronized void reAdminResourcesView() {
        adminResourcesView.clear();
    }

    private synchronized void initResources() {

        resourcesAll.clear();
        resourcesView.clear();
        resourcesUrlId.clear();
        resourcesIdUrl.clear();

        for (String resource : resources) {
            InputStream inputStream = ResourcesData.class.getClassLoader().getResourceAsStream("resources" + File.separator + resource);
            Yaml yaml = new Yaml();
            ResourcesDto resourcesDto = new Gson().fromJson(new Gson().toJson(yaml.load(inputStream))
                    , new TypeToken<ResourcesDto>() {
                    }.getType());
            resourcesAll.add(resourcesDto);
            initResourcesUrl(resourcesDto);
        }
        resourcesView = this.getViewResources(resourcesAll, ResourcesType.menu, null);
    }


    private synchronized void initRoleResouces() {

        roleResources.clear();
        resourcesRole.clear();
        adminResourcesView.clear();

        List<GmRoleDto> gmRoleDtoList = gmRoleRepository.findAll();
        if (gmRoleDtoList != null) {
            for (GmRoleDto gmRoleDto : gmRoleDtoList) {
                List<GmRelRoleResourceDto> relRoleResourceList = gmRelRoleResourceRepository.findByRoleId(gmRoleDto.getId());
                Set<String> resourcesSet = Collections.synchronizedSet(new HashSet<>());
                if (relRoleResourceList != null && relRoleResourceList.size() > 0) {
                    for (GmRelRoleResourceDto gmRelRoleResourceDto : relRoleResourceList) {
                        resourcesSet.add(gmRelRoleResourceDto.getResourceId());
                        Set<Long> roleSet = resourcesRole.get(gmRelRoleResourceDto.getResourceId());
                        if (roleSet == null) {
                            roleSet = Collections.synchronizedSet(new HashSet<>());
                        }
                        roleSet.add(gmRoleDto.getId());
                        resourcesRole.put(gmRelRoleResourceDto.getResourceId(), roleSet);
                    }
                }
                if (resourcesSet.size() > 0) {
                    roleResources.put(gmRoleDto.getId(), resourcesSet);
                }
            }
        }

    }

    /**
     * 初始化 资源数据
     *
     * @param resourcesDto 资源数据
     */
    private synchronized void initResourcesUrl(ResourcesDto resourcesDto) {
        if (resourcesDto == null) {
            return;
        }
        if (StringUtils.isEmptyByTrim(resourcesDto.getId()) || StringUtils.isEmptyByTrim(resourcesDto.getUrl())) {
            throw new RuntimeException("Error: 资源数据不完整，ID 或 Url 为空\n\n" + new Gson().toJson(resourcesDto) + "\n\n");
        }
        if (resourcesUrlId.get(resourcesDto.getUrl()) != null) {
            throw new RuntimeException("Error: 资源url：" + resourcesDto.getUrl() + " 重复\n\n" + new Gson().toJson(resourcesDto) + "\n\n");
        }
        if (resourcesIdUrl.get(resourcesDto.getId()) != null) {
            throw new RuntimeException("Error: 资源Id：" + resourcesDto.getId() + " 重复\n\n" + new Gson().toJson(resourcesDto) + "\n\n");
        }
        resourcesUrlId.put(resourcesDto.getUrl(), resourcesDto.getId());
        resourcesIdUrl.put(resourcesDto.getId(), resourcesDto.getUrl());
        if (resourcesDto.getChild() != null) {
            for (ResourcesDto dto : resourcesDto.getChild()) {
                initResourcesUrl(dto);
            }
        }
    }

    /**
     * 筛选 菜单资源
     *
     * @param resourcesList 资源
     * @param type          类型(可为空，为空时，不做类型筛选)
     * @param purview       可查看的权限（可为空）
     * @return 菜单资源集合
     */
    private synchronized List<ResourcesDto> getViewResources(List<ResourcesDto> resourcesList, ResourcesType type, Set<String> purview) {
        if (resourcesList == null) {
            return null;
        }
        List<ResourcesDto> resourcesViewList = new Vector<>();
        for (ResourcesDto resourcesDto : resourcesList) {
            if ((type == null || Integer.valueOf(type.value()).equals(resourcesDto.getType()))
                    && (purview == null || purview.contains(resourcesDto.getId()))) {
                resourcesDto.setChild(getViewResources(resourcesDto.getChild(), type, purview));
                resourcesViewList.add(resourcesDto);
            }
        }
        return resourcesViewList.size() == 0 ? null : resourcesViewList;

    }

    /**
     * 获取 全部资源列表
     *
     * @return 全部资源列表
     */
    public synchronized List<ResourcesDto> getResourcesAll() {
        return resourcesAll;
    }

    /**
     * 获取 用户的菜单列表
     *
     * @param adminId 用户id
     * @return 菜单列表
     */
    public synchronized List<ResourcesDto> getViewResourcesForAdminId(Long adminId) {
        return getResourcesForAdminIdAndType(adminId, ResourcesType.menu);
    }

    /**
     * 获取 用户的资源列表
     *
     * @param adminId 用户id
     * @return 资源列表
     */
    public synchronized List<ResourcesDto> getResourcesForAdminId(Long adminId) {
        return getResourcesForAdminIdAndType(adminId, null);
    }

    /**
     * 获取 用户的资源列表
     *
     * @param adminId 用户id
     * @param type    资源类型（为空时不做筛选）
     * @return 资源列表
     */
    private synchronized List<ResourcesDto> getResourcesForAdminIdAndType(Long adminId, ResourcesType type) {
        List<ResourcesDto> rList = adminResourcesView.get(adminId);
        if (rList == null || rList.size() == 0) {
            Set<Long> roleIdSet = getRoleIdForAdminId(adminId);
            Set<String> purview = new HashSet<>();
            for (Long roleId : roleIdSet) {
                Set<String> pu = roleResources.get(roleId);
                if (pu != null && pu.size() > 0) {
                    purview.addAll(pu);
                }
            }
            if (purview.size() > 0) {
                List<ResourcesDto> resList = this.getViewResources(resourcesView, type, purview);
                if (resList != null && resList.size() > 0) {
                    rList = resList;
                }
            }
            if (rList == null) {
                rList = new ArrayList<>();
            } else {
                adminResourcesView.put(adminId, rList);
            }
        }
        return rList;
    }

    /**
     * 根据用户id 获取 角色id
     *
     * @param adminId 用户id
     * @return 角色id集合
     */
    private synchronized Set<Long> getRoleIdForAdminId(Long adminId) {
        Set<Long> roleIdSet = adminIdRole.get(adminId);
        if (roleIdSet == null) {
            roleIdSet = new HashSet<>();
            List<GmRelAdminRoleDto> rarList = gmRelAdminRoleRepository.findByAdminId(adminId);
            if (rarList != null && rarList.size() > 0) {
                for (GmRelAdminRoleDto gmRelAdminRoleDto : rarList) {
                    roleIdSet.add(gmRelAdminRoleDto.getRoleId());
                }
            }
            if (roleIdSet.size() > 0) {
                adminIdRole.put(adminId, roleIdSet);
            }
        }
        return roleIdSet;
    }

    /**
     * 判断用户是否有访问该url的权限
     *
     * @param adminId 用户id
     * @param url     url资源
     * @return turn 有权限 / false 无权限
     */
    public synchronized boolean hasPermissions(Long adminId, String url) {
        if (adminId == null || StringUtils.isEmptyByTrim(url)) {
            return false;
        }
        AdminDto adminDto = this.getAdminDto(adminId);
        if (adminDto == null || AdminDisable.YES.equals(adminDto.getDisable())) {
            return false;
        }
        if (AdminGrade.SUPER.equals(adminDto.getGrade())) {
            return true;
        }
        String resourcesId = resourcesUrlId.get(url);
        if (StringUtils.isEmptyByTrim(resourcesId)) {
            return false;
        }
        Set<Long> roleIdSet = getRoleIdForAdminId(adminId);
        for (Long roleId : roleIdSet) {
            Set<String> roleSet = roleResources.get(roleId);
            if (roleSet != null && roleIdSet.contains(resourcesId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 用户Dto
     *
     * @param adminId 用户Id
     * @return 用户Dto
     */
    private synchronized AdminDto getAdminDto(Long adminId) {
        if (adminId == null) {
            return null;
        }
        AdminDto adminDto = cacheDate.get(ADMINDTO + adminId, AdminDto.class);
        if (adminDto == null) {
            adminDto = gmAdminRepository.getOne(adminId);
            if (adminDto != null) {
                cacheDate.put(ADMINDTO + adminId, adminDto);
            }
        }
        return adminDto;
    }

}
