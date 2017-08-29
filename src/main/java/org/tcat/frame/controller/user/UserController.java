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
import org.tcat.frame.service.user.UserIdRepository;
import org.tcat.frame.service.user.UserRepository;
import org.tcat.frame.service.user.dto.UserDto;
import org.tcat.frame.service.user.dto.UserIdDto;
import org.tcat.frame.service.user.enums.UserIdType;
import org.tcat.frame.util.BeansConverter;
import org.tcat.frame.util.PageUtils;
import org.tcat.frame.util.StringUtils;
import org.tcat.frame.util.ValidatorUtils;

/**
 * Created by Lin on 2017/8/3.
 */
@Api(value = "/user", description = "用户管理")
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserIdRepository userIdRepository;
    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "创建用户", produces = "application/json")
    @RequestMapping(value = "", method = {RequestMethod.POST})
    public JsonObject<UserDto> create(@RequestBody UserVo userVo) {
        if (userVo == null || !ValidatorUtils.validate(userVo).getResult()) {
            return JsonObject.error(ErrorCode.C_param_missing);
        }
        if (userRepository.findByAccount(userVo.getAccount()) != null) {
            return JsonObject.error(ErrorCode.U_account_had);
        }
        UserIdDto userIdDto = userIdRepository.save(new UserIdDto(UserIdType.USER));
        userVo.setId(userIdDto.getId());
        return JsonObject.ok(userRepository.save(BeansConverter.copy(userVo, UserDto.class)));
    }

    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    public JsonObject update(Long id, @RequestBody UserVo userVo) {
        if (id == null || userVo == null || !ValidatorUtils.validate(userVo).getResult()) {
            return JsonObject.error(ErrorCode.fail);
        }
        userRepository.dynamicSave(id, BeansConverter.copy(userVo, UserDto.class));
        return JsonObject.ok();
    }

    @ApiOperation(value = "通过id查询用户")
    @RequestMapping(value = "/{id:[0-9]*}", method = {RequestMethod.GET})
    public JsonObject<UserDto> getById(@PathVariable Long id) {
        return JsonObject.ok(userRepository.getOne(id));
    }

    @ApiOperation(value = "查询用户")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonObject<PageResponse<UserDto>> list(@ModelAttribute PageRequest pageRequest) {
        Page<UserDto> userDtoPage = userRepository.findAll(pageRequest);
        return JsonObject.ok(PageUtils.pageConversion(userDtoPage));
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonObject login(String account, String password) {
        if (StringUtils.isEmptyByTrim(account) || StringUtils.isEmptyByTrim(password)) {
            return JsonObject.error((ErrorCode.C_param_missing));
        }
        UserDto userDto = userRepository.findByAccountAndPassword(account, password);
        if (userDto == null) {
            return JsonObject.error(ErrorCode.U_login_fail);
        }
        this.setUserSession(new UserSession()
                .setId(userDto.getId())
                .setAccount(userDto.getAccount()));
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
