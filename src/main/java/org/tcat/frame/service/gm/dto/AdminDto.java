package org.tcat.frame.service.gm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.tcat.frame.service.BaseDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * 管理员帐户表对象
 * Created by Lin on 2017/8/18.
 */
@Entity(name = "gm$admin")
@Table(appliesTo = "gm$admin", comment = "管理员帐户表")
@ApiModel(value = "AdminDto", description = "管理员帐户表对象")
public class AdminDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(value = "用户id")
    @Column(nullable = false)
    private Long userId;

    @ApiModelProperty(value = "账号")
    @Column(nullable = false)
    private String account;

    @ApiModelProperty(value = "密码")
    @Column(nullable = false)
    private String password;

    private String nameCn;

    private String nameEn;

//    private

}
