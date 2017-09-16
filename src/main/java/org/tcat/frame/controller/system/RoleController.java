package org.tcat.frame.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.annotation.ILogin;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.bean.PageRequest;
import org.tcat.frame.bean.PageResponse;
import org.tcat.frame.component.ResourcesData;
import org.tcat.frame.controller.BaseController;
import org.tcat.frame.exception.code.ErrorCode;
import org.tcat.frame.service.gm.GmRelRoleResourceRepository;
import org.tcat.frame.service.gm.GmRoleRepository;
import org.tcat.frame.service.gm.dto.GmRelRoleResourceDto;
import org.tcat.frame.service.gm.dto.GmRoleDto;
import org.tcat.frame.service.user.enums.UserIdType;
import org.tcat.frame.util.PageUtils;

import java.util.ArrayList;
import java.util.List;

@Api(value = "/system/role", description = "角色管理")
@RestController
@RequestMapping("/system/role")
@ILogin(userIdType = UserIdType.STAFF)
public class RoleController extends BaseController {

    @Autowired
    private ResourcesData resourcesData;
    @Autowired
    private GmRelRoleResourceRepository gmRelRoleResourceRepository;
    @Autowired
    private GmRoleRepository gmRoleRepository;

    @ApiOperation(value = "角色管理页面", hidden = true)
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView("");
        return mv;
    }

    @ApiOperation(value = "分页获取 角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonObject<PageResponse<GmRoleDto>> list(@ModelAttribute PageRequest pageRequest) {
        return JsonObject.ok(PageUtils.pageConversion(gmRoleRepository.findAll(pageRequest)));
    }

    @ApiOperation(value = "获取角色")
    @RequestMapping(value = "/{roleId:[0-9]*}", method = {RequestMethod.GET})
    public JsonObject<GmRoleDto> get(@PathVariable Long roleId) {
        GmRoleDto gmRoleDto = gmRoleRepository.getOne(roleId);
        if (gmRoleDto == null) {
            return JsonObject.error(ErrorCode.C_param_illegal);
        }
        return JsonObject.ok(gmRoleDto);
    }

    @ApiOperation(value = "获取角色权限")
    @RequestMapping(value = "/resource/{roleId:[0-9]*}", method = RequestMethod.GET)
    public JsonObject<List<GmRelRoleResourceDto>> getResource(@PathVariable Long roleId) {
        return JsonObject.ok(gmRelRoleResourceRepository.findByRoleId(roleId));
    }

    @ApiOperation(value = "更新角色权限")
    @RequestMapping(value = "/resource/{roleId:[0-9]*}", method = RequestMethod.PUT)
    public JsonObject updateResource(@PathVariable Long roleId, List<String> resourceIds) {
        if (resourceIds == null || resourceIds.size() == 0) {
            return JsonObject.error(ErrorCode.C_param_missing);
        }
        gmRelRoleResourceRepository.deleteByRoleId(roleId);
        List<GmRelRoleResourceDto> gmRelRoleResourceDtoList = new ArrayList<>();
        for (String resourceId : resourceIds) {
            gmRelRoleResourceDtoList.add(new GmRelRoleResourceDto()
                    .setResourceId(resourceId)
                    .setRoleId(roleId));
        }
        gmRelRoleResourceRepository.save(gmRelRoleResourceDtoList);
        return JsonObject.ok();
    }

}
