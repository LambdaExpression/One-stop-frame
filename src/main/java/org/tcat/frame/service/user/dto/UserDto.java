package org.tcat.frame.service.user.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.tcat.frame.service.BaseDto;
import org.tcat.frame.service.user.enums.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Lin on 2017/8/3.
 */
@ApiModel(value = "UserDto", description = "用户对象")
@Entity(name = "user$user")
public class UserDto extends BaseDto {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_id")
    @ApiModelProperty(value = "ID")
    private Long userId;
    @Column(unique = true, nullable = false)
    @ApiModelProperty(value = "账号名")
    private String account;
    @Column(nullable = false)
    @ApiModelProperty(value = "密码")
    private String password;
    @Column(nullable = false)
    @ApiModelProperty(value = "用户类型")
    private UserType type;

    public Long getUserId() {
        return userId;
    }

    public UserDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public UserDto setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserType getType() {
        return type;
    }

    public UserDto setType(UserType type) {
        this.type = type;
        return this;
    }
}
