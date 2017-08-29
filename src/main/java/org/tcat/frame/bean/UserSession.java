package org.tcat.frame.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户Seesion
 * Created by Lin on 2017/8/5.
 */
@ApiModel(value = "UserSession", description = "用户Session对象")
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @ApiModelProperty(value = "ID")
    private Long Id;
    /**
     * 账号名
     */
    @ApiModelProperty(value = "账号名")
    private String account;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String name;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private Integer type;

    /**
     * id
     *
     * @return id
     */
    public Long getId() {
        return Id;
    }

    /**
     * id
     *
     * @param id id
     * @return this
     */
    public UserSession setId(Long id) {
        Id = id;
        return this;
    }

    /**
     * 账号名
     *
     * @return 账号名
     */
    public String getAccount() {
        return account;
    }

    /**
     * 账号名
     *
     * @param account 账号名
     * @return this
     */
    public UserSession setAccount(String account) {
        this.account = account;
        return this;
    }

    /**
     * 用户名
     *
     * @return 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 用户名
     *
     * @param name 用户名
     * @return this
     */
    public UserSession setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 用户类型
     *
     * @return 用户类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 用户类型
     *
     * @param type 用户类型
     * @return this
     */
    public UserSession setType(Integer type) {
        this.type = type;
        return this;
    }
}
