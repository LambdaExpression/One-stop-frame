package org.tcat.frame.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.annotation.ILogin;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.bean.PageRequest;
import org.tcat.frame.bean.PageResponse;
import org.tcat.frame.controller.BaseController;
import org.tcat.frame.controller.system.vo.AdminVo;
import org.tcat.frame.exception.code.ErrorCode;
import org.tcat.frame.service.gm.*;
import org.tcat.frame.service.gm.dto.*;
import org.tcat.frame.service.gm.enums.AdminDisable;
import org.tcat.frame.service.user.UserIdRepository;
import org.tcat.frame.service.user.dto.UserIdDto;
import org.tcat.frame.service.user.enums.UserIdType;
import org.tcat.frame.util.BeansConverter;
import org.tcat.frame.util.ValidatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/system/account", description = "管理管理员")
@RestController
@RequestMapping("/system/account")
@ILogin(userIdType = UserIdType.STAFF)
public class AdminController extends BaseController {

    @Autowired
    private UserIdRepository userIdRepository;
    @Autowired
    private GmAdminRepository gmAdminRepository;
    @Autowired
    private GmRoleRepository gmRoleRepository;
    @Autowired
    private GmGroupRepository gmGroupRepository;
    @Autowired
    private GmRelAdminRoleRepository gmRelAdminRoleRepository;
    @Autowired
    private GmRelAdminGroupRepository gmRelAdminGroupRepository;

    @ApiOperation(value = "管理管理员页面", hidden = true)
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView("");
        return mv;
    }

    @ApiOperation(value = "查询 管理员", produces = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonObject<PageResponse<AdminVo>> list(@ModelAttribute PageRequest pageRequest) {
        Page<AdminDto> adminDtoPage = gmAdminRepository.findAll(pageRequest);
        Map<Long, GmRoleDto> gmRoleMap = new HashMap<>();
        Map<Long, GmGroupDto> gmGroupDtoMap = new HashMap<>();
        List<AdminVo> adminVoList = BeansConverter.copy(adminDtoPage.getContent(), AdminVo.class);
        if (adminVoList != null) {
            adminVoList.forEach(i -> {
                i.setPassword(null);
                List<GmRelAdminRoleDto> adminRoleDtos = gmRelAdminRoleRepository.findByAdminId(i.getUserId());
                if (adminRoleDtos != null && adminRoleDtos.size() > 0) {
                    List<GmRoleDto> gmRoleDtoList = new ArrayList<>();
                    for (GmRelAdminRoleDto adminRoleDto : adminRoleDtos) {
                        GmRoleDto gmRoleDto = gmRoleMap.get(adminRoleDto.getRoleId());
                        if (gmRoleDto == null) {
                            gmRoleDto = gmRoleRepository.findOne(adminRoleDto.getRoleId());
                            if (gmRoleDto != null) {
                                gmRoleMap.put(gmRoleDto.getId(), gmRoleDto);
                            }
                        }
                        if (gmRoleDto != null) {
                            gmRoleDtoList.add(gmRoleDto);
                        }
                    }
                    i.setGmRoleDtoList(gmRoleDtoList);
                }
                List<GmRelAdminGroupDto> gmRelAdminGroupDtoList = gmRelAdminGroupRepository.findByAdminId(i.getUserId());
                if (gmRelAdminGroupDtoList != null && gmRelAdminGroupDtoList.size() > 0) {
                    List<GmGroupDto> gmGroupDtoList = new ArrayList<>();
                    for (GmRelAdminGroupDto gmRelAdminGroupDto : gmRelAdminGroupDtoList) {
                        GmGroupDto gmGroupDto = gmGroupDtoMap.get(gmRelAdminGroupDto.getGroupId());
                        if (gmGroupDto == null) {
                            gmGroupDto = gmGroupRepository.findOne(gmRelAdminGroupDto.getGroupId());
                            if (gmGroupDto != null) {
                                gmGroupDtoMap.put(gmGroupDto.getId(), gmGroupDto);
                            }
                        }
                        if (gmGroupDto != null) {
                            gmGroupDtoList.add(gmGroupDto);
                        }
                    }
                    i.setGmGroupDtoList(gmGroupDtoList);
                }
            });
        }
        return JsonObject.ok(new PageResponse<>(
                adminDtoPage.getNumber()
                , adminDtoPage.getSize()
                , adminDtoPage.getTotalElements()
                , adminDtoPage.getTotalPages()
                , adminVoList));
    }

    @ApiOperation(value = "创建用户")
    @RequestMapping(value = "", method = {RequestMethod.POST})
    public JsonObject<AdminDto> create(@RequestBody AdminDto adminDto) {
        if (adminDto == null || !ValidatorUtils.validate(adminDto).getResult()) {
            return JsonObject.error(ErrorCode.C_param_missing);
        }
        if (gmAdminRepository.findByAccount(adminDto.getAccount()) != null) {
            return JsonObject.error(ErrorCode.U_account_had);
        }
        adminDto.setDisable(AdminDisable.NO);
        UserIdDto userIdDto = userIdRepository.save(new UserIdDto(UserIdType.USER));
        adminDto.setUserId(userIdDto.getId());
        adminDto = gmAdminRepository.save(adminDto);
        adminDto.setPassword(null);
        return JsonObject.ok(adminDto);
    }

    @ApiOperation(value = "获取用户")
    @RequestMapping(value = "/{id:[0-9]*}", method = {RequestMethod.GET})
    public JsonObject<AdminDto> get(@PathVariable Long id) {
        AdminDto adminDto = gmAdminRepository.getOne(id);
        if (adminDto == null) {
            return JsonObject.error(ErrorCode.C_param_illegal);
        }
        return JsonObject.ok(adminDto);
    }

    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/{id:[0-9]*}", method = {RequestMethod.PUT})
    public JsonObject update(@PathVariable Long id, @RequestBody AdminDto adminDto) {
        if (adminDto == null) {
            return JsonObject.error(ErrorCode.C_param_missing);
        }
        adminDto.setUserId(id);
        gmAdminRepository.save(adminDto);
        return JsonObject.ok();
    }

    @ApiOperation(value = "启用/禁用 用户")
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public JsonObject disable(List<Long> userIds, @ApiParam(value = "1:禁用 ，2:启用") int disable) {
        if (userIds == null || userIds.size() == 0) {
            return JsonObject.error(ErrorCode.C_param_missing);
        }
        if (AdminDisable.findByValue(disable) == null) {
            return JsonObject.error(ErrorCode.C_param_illegal);
        }
        gmAdminRepository.updateDisable(disable, userIds);
        return JsonObject.ok();
    }

    @ApiOperation(value = "用户 授权")
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public JsonObject role(List<Long> userIds, List<Long> roleIds) {
        if (userIds == null || userIds.size() == 0
                || roleIds == null || roleIds.size() == 0) {
            return JsonObject.error(ErrorCode.C_param_missing);
        }
        gmRelAdminRoleRepository.deleteByAdminIds(userIds);
        List<GmRelAdminRoleDto> gmRelAdminRoleDtoList = new ArrayList<>();
        for (Long userId : userIds) {
            for (Long roleId : roleIds) {
                gmRelAdminRoleDtoList.add(new GmRelAdminRoleDto()
                        .setAdminId(userId)
                        .setRoleId(roleId));
            }
        }
        gmRelAdminRoleRepository.save(gmRelAdminRoleDtoList);
        return JsonObject.ok();
    }

}
