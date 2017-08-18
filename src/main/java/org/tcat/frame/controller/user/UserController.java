package org.tcat.frame.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.bean.PageRequest;
import org.tcat.frame.bean.PageResponse;
import org.tcat.frame.bean.UserSession;
import org.tcat.frame.controller.BaseController;
import org.tcat.frame.controller.user.vo.UserVo;
import org.tcat.frame.exception.code.ErrorCode;
import org.tcat.frame.service.user.UserRepository;
import org.tcat.frame.service.user.dto.UserDto;
import org.tcat.frame.util.BeansConverter;
import org.tcat.frame.util.PageUtils;
import org.tcat.frame.util.StringUtils;
import org.tcat.frame.util.ValidatorUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by Lin on 2017/8/3.
 */
@Api(value = "/user", description = "用户管理")
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "创建用户", produces = "application/json")
    @RequestMapping(value = "", method = {RequestMethod.POST})
    public JsonObject<UserDto> create(@RequestBody UserVo userVo) {
        JsonObject<UserDto> json = new JsonObject<>();
        if (userVo == null || !ValidatorUtils.validate(userVo).getResult()) {
            return json.setCode(ErrorCode.fail);
        }
        return json.setData(userRepository.save(BeansConverter.copy(userVo, UserDto.class)));
    }

    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    public JsonObject update(Long id, @RequestBody UserVo userVo) {
        JsonObject json = new JsonObject();
        if (id == null || userVo == null || !ValidatorUtils.validate(userVo).getResult()) {
            return json.setCode(ErrorCode.fail);
        }
        userVo.setId(id);
        userRepository.save(BeansConverter.copy(userVo, UserDto.class));
        return json;
    }

    @ApiOperation(value = "通过id查询用户")
    @RequestMapping(value = "/{id:[0-9]*}", method = {RequestMethod.GET})
    public JsonObject<UserDto> getById(@PathVariable Long id) {
        JsonObject<UserDto> json = new JsonObject<>();
        return json.setData(userRepository.findById(id));
    }

    @ApiOperation(value = "查询用户")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonObject<PageResponse<UserDto>> list(@ModelAttribute  PageRequest pageRequest) {
        JsonObject<PageResponse<UserDto>> json = new JsonObject<>();
        Page<UserDto> userDtoPage = userRepository.findAll(pageRequest);
        return json.setData(PageUtils.pageConversion(userDtoPage));
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonObject login(String account, String password, HttpSession session) {
        JsonObject json = new JsonObject();
        if (StringUtils.isEmptyByTrim(account) || StringUtils.isEmptyByTrim(password)) {
            return json.setCode(ErrorCode.C_param_missing);
        }
        UserDto userDto = userRepository.findByAccountAndPassword(account, password);
        if (userDto == null) {
            return json.setCode(ErrorCode.U_login_fail);
        }
        this.setUserSession(new UserSession()
                .setId(userDto.getId())
                .setAccount(userDto.getAccount()));
        return json;
    }

    @ApiOperation(value = "用户退出")
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public JsonObject logout() {
        this.resetSession();
        return new JsonObject();
    }

    @ApiOperation(value = "检查用户登录状态")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public JsonObject<UserSession> check() {
        JsonObject<UserSession> json = new JsonObject<>();
        UserSession userSession = this.getUserSession();
        if (userSession == null) {
            return json.setCode(ErrorCode.U_login_Un);
        }
        return json.setData(userSession);
    }

}
