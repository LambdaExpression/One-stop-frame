package org.tcat.frame.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.tcat.frame.annotation.ILogin;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.bean.PageRequest;
import org.tcat.frame.bean.PageResponse;
import org.tcat.frame.bean.ResultObject;
import org.tcat.frame.controller.BaseController;
import org.tcat.frame.controller.user.vo.UserVo;
import org.tcat.frame.exception.code.ErrorCode;
import org.tcat.frame.service.user.UserIdRepository;
import org.tcat.frame.service.user.UserRepository;
import org.tcat.frame.service.user.dto.UserDto;
import org.tcat.frame.service.user.dto.UserIdDto;
import org.tcat.frame.service.user.enums.UserIdType;
import org.tcat.frame.service.user.enums.UserType;
import org.tcat.frame.util.BeansConverter;
import org.tcat.frame.util.PageUtils;
import org.tcat.frame.util.ValidatorUtils;

/**
 * Created by Lin on 2017/8/3.
 */
@Api(value = "/user", description = "用户管理")
@RestController
@RequestMapping("user")
@ILogin(userIdType = UserIdType.STAFF)
public class UserController extends BaseController {

    @Autowired
    private UserIdRepository userIdRepository;
    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "创建用户")
    @RequestMapping(value = "", method = {RequestMethod.POST})
    public JsonObject create(@RequestBody UserVo userVo) {
        if (userVo == null) {
            return JsonObject.error(ErrorCode.C_param_missing);
        }
        if (userRepository.findByAccount(userVo.getAccount()) != null) {
            return JsonObject.error(ErrorCode.U_account_had);
        }
        UserIdDto userIdDto = userIdRepository.save(new UserIdDto(UserIdType.USER));
        userVo.setId(userIdDto.getId());
        UserDto userDto = BeansConverter.copy(userVo, UserDto.class)
                .setUserId(userIdDto.getId())
                .setType(UserType.ORDINARY);
        ResultObject resultObject = ValidatorUtils.validate(userDto);
        if (!resultObject.getResult()) {
            return JsonObject.error(ErrorCode.C_param_missing, resultObject.getMessage());
        }
        userRepository.save(userDto);
        return JsonObject.ok();
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

    @ApiOperation(value = "查询用户", produces = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonObject<PageResponse<UserDto>> list(@ModelAttribute PageRequest pageRequest) {
        Page<UserDto> userDtoPage = userRepository.findAll(pageRequest);
        userDtoPage.getContent().forEach(i -> i.setPassword(null));
        return JsonObject.ok(PageUtils.pageConversion(userDtoPage));
    }

}
