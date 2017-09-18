package org.tcat.frame.controller.login;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.bean.UserSession;
import org.tcat.frame.controller.BaseController;
import org.tcat.frame.exception.code.ErrorCode;
import org.tcat.frame.service.gm.GmAdminRepository;
import org.tcat.frame.service.gm.dto.AdminDto;
import org.tcat.frame.service.user.enums.UserIdType;
import org.tcat.frame.util.StringUtils;

@Api(value = "/gm", description = "管理员登录控制器")
@RestController
@RequestMapping("/gm")
public class GmLoginController extends BaseController {

    @Autowired
    private GmAdminRepository gmAdminRepository;

    @ApiOperation(value = "用户登录页面")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonObject login(String account, String password) {
        if (StringUtils.isEmptyByTrim(account) || StringUtils.isEmptyByTrim(password)) {
            return JsonObject.error((ErrorCode.C_param_missing));
        }
        AdminDto adminDto = gmAdminRepository.findByAccountAndPassword(account, password);
        if (adminDto == null) {
            return JsonObject.error(ErrorCode.U_login_fail);
        }
        this.setUserSession(new UserSession()
                .setId(adminDto.getUserId())
                .setAccount(adminDto.getAccount())
                .setGrade(adminDto.getGrade().value())
                .setIdType(UserIdType.STAFF.value()));
        return JsonObject.ok();
    }

    @ApiOperation(value = "用户退出")
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public JsonObject logout() {
        this.resetSession();
        return JsonObject.ok();
    }

    @ApiOperation(value = "检查用户登录状态")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public JsonObject<UserSession> check() {
        UserSession userSession = this.getUserSession();
        if (userSession == null) {
            return JsonObject.error(ErrorCode.U_login_Un);
        }
        return JsonObject.ok(userSession);
    }

}
