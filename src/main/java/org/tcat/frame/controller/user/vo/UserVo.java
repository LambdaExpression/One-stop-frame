package org.tcat.frame.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by Lin on 2017/8/4.
 */
@ApiModel(value = "UserVo", description = "用户对象")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID ,创建时不需要传输")
    private Long id;
    @ApiModelProperty(value = "账号")
    @NotEmpty(message = "账号不能为空")
    private String account;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    public Long getId() {
        return id;
    }

    public UserVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public UserVo setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserVo setPassword(String password) {
        this.password = password;
        return this;
    }

}
