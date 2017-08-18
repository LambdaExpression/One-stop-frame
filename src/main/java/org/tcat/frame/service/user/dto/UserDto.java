package org.tcat.frame.service.user.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.tcat.frame.service.BaseDto;

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
    @ApiModelProperty(value = "ID")
    private Long id;
    @Column
    @ApiModelProperty(value = "账号名")
    private String account;
    @Column
    @ApiModelProperty(value = "密码")
    private String password;
    @Column
    @ApiModelProperty(value = "用户类型")
    private Integer type;


    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
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

}
