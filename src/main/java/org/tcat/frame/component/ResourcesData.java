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
import org.tcat.frame.service.gm.dto.GmRelAdminRoleDto;
import org.tcat.frame.service.gm.dto.GmRelRoleResourceDto;
import org.tcat.frame.service.gm.dto.GmRoleDto;
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
public class ResourcesData implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String[] resources = {
            "system_management.yml"
    };
    private List<ResourcesDto> resourcesAll = new Vector<>();
    private List<ResourcesDto> resourcesView = new Vector<>();
    private Map<Long, Set<String>> roleResources = new ConcurrentHashMap<>();
    private Map<String, Set<Long>> resourcesRole = new ConcurrentHashMap<>();
    private Map<Long, List<ResourcesDto>> adminResourcesView = new ConcurrentHashMap<>();

    @Autowired
    private GmRelRoleResourceRepository gmRelRoleResourceRepository;
    @Autowired
    private GmRoleRepository gmRoleRepository;
    @Autowired
    private GmAdminRepository gmAdminRepository;
    @Autowired
    private GmRelAdminRoleRepository gmRelAdminRoleRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        initResources();
        initRoleResouces();
    }

    public synchronized void reInit() {
        initResources();
        initRoleResouces();
    }

    public synchronized void reAdminResourcesView() {
        adminResourcesView.clear();
    }

    private synchronized void initResources() {

        resourcesAll.clear();
        resourcesView.clear();

        for (String resource : resources) {
            InputStream inputStream = ResourcesData.class.getClassLoader().getResourceAsStream("resources" + File.separator + resource);
            Yaml yaml = new Yaml();
            ResourcesDto resourcesDto = new Gson().fromJson(new Gson().toJson(yaml.load(inputStream))
                    , new TypeToken<ResourcesDto>() {
                    }.getType());
            resourcesAll.add(resourcesDto);
        }
        resourcesView = this.getViewResources(resourcesAll, null);
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
     * 筛选 菜单资源
     *
     * @param resourcesList 资源
     * @param purview       可查看的权限（可为空）
     * @return 菜单资源集合
     */
    private synchronized List<ResourcesDto> getViewResources(List<ResourcesDto> resourcesList, Set<String> purview) {
        if (resourcesList == null) {
            return null;
        }
        List<ResourcesDto> resourcesViewList = new Vector<>();
        for (ResourcesDto resourcesDto : resourcesList) {
            if (Integer.valueOf(ResourcesType.menu.value()).equals(resourcesDto.getType())
                    && (purview == null || purview.contains(resourcesDto.getId()))) {
                resourcesDto.setChild(getViewResources(resourcesDto.getChild(), purview));
                resourcesViewList.add(resourcesDto);
            }
        }
        return resourcesViewList.size() == 0 ? null : resourcesViewList;

    }

    /**
     * 获取用户的菜单列表
     *
     * @param adminId 用户id
     * @return 菜单列表
     */
    public synchronized List<ResourcesDto> getViewResourcesForAdminId(Long adminId) {
        List<ResourcesDto> rList = adminResourcesView.get(adminId);
        if (rList == null || rList.size() == 0) {
            List<GmRelAdminRoleDto> rarList = gmRelAdminRoleRepository.findByAdminId(adminId);
            if (rarList != null && rarList.size() > 0) {
                Set<String> purview = new HashSet<>();
                for (GmRelAdminRoleDto gmRelAdminRoleDto : rarList) {
                    Set<String> pu = roleResources.get(gmRelAdminRoleDto.getRoleId());
                    if (pu != null && pu.size() > 0) {
                        purview.addAll(pu);
                    }
                }
                if (purview.size() > 0) {
                    List<ResourcesDto> resList = this.getViewResources(resourcesView, purview);
                    if (resList != null && resList.size() > 0) {
                        rList = resList;
                    }
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

//    public synchronized boolean hasPermissions(Long adminId,String url)

}
