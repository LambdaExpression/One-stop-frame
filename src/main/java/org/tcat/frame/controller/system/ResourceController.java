package org.tcat.frame.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.annotation.ILogin;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.component.ResourcesData;
import org.tcat.frame.component.dto.ResourcesDto;
import org.tcat.frame.controller.BaseController;
import org.tcat.frame.service.user.enums.UserIdType;

import java.util.List;

@Api(value = "/system/resource", description = "资源管理")
@RestController
@RequestMapping("/system/resource")
@ILogin(userIdType = UserIdType.STAFF)
public class ResourceController extends BaseController {

    @Autowired
    private ResourcesData resourcesData;

    @ApiOperation(value = "资源管理页面", hidden = true)
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView("");
        return mv;
    }

    @ApiOperation(value = "获取 全部资源列表", hidden = true)
    @RequestMapping(value = {"/all_list"}, method = RequestMethod.GET)
    public JsonObject<List<ResourcesDto>> getResourcesAll() {
        return JsonObject.ok(resourcesData.getResourcesAll());
    }


}
