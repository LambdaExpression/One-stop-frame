package org.tcat.frame.service.gm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.hibernate.validator.constraints.NotEmpty;
import org.tcat.frame.service.BaseDto;
import org.tcat.frame.service.gm.enums.AdminDisable;
import org.tcat.frame.service.gm.enums.AdminGrade;
import org.tcat.frame.service.user.enums.UserGender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


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
    @Column(name = "user_id", nullable = false)
    @NotEmpty(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "账号")
    @Column(nullable = false)
    @NotEmpty(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码")
    @Column(nullable = false)
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "中文名")
    @Column(name = "name_cn", nullable = false)
    @NotEmpty(message = "中文名不能为空")
    private String nameCn;

    @ApiModelProperty(value = "英文名")
    @Column(name = "name_en", nullable = false)
    @NotEmpty(message = "英文名不能为空")
    private String nameEn;

    @ApiModelProperty(value = "性别")
    @Column(nullable = false)
    @NotEmpty(message = "性别不能为空")
    private UserGender gender;

    @ApiModelProperty(value = "邮箱")
    @Column()
    private String email;

    @ApiModelProperty(value = "联系电话")
    @Column()
    private String phone;

    @ApiModelProperty(value = "微信")
    @Column()
    private String wechat;

    @ApiModelProperty(value = "QQ")
    @Column()
    private String qq;

    @ApiModelProperty(value = "管理员等级")
    @Column(nullable = false)
    @NotEmpty(message = "管理员等级不能为空")
    private AdminGrade grade;

    @ApiModelProperty(value = "是否禁用")
    @Column(nullable = false)
    @NotEmpty(message = "是否禁用不能为空")
    private AdminDisable disable;

    @ApiModelProperty(value = "最后登录时间")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @ApiModelProperty(value = "备注")
    @Column()
    private String remark;

    public Long getUserId() {
        return userId;
    }

    public AdminDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public AdminDto setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AdminDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public AdminDto setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public AdminDto setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public UserGender getGender() {
        return gender;
    }

    public AdminDto setGender(UserGender gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AdminDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AdminDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWechat() {
        return wechat;
    }

    public AdminDto setWechat(String wechat) {
        this.wechat = wechat;
        return this;
    }

    public String getQq() {
        return qq;
    }

    public AdminDto setQq(String qq) {
        this.qq = qq;
        return this;
    }

    public AdminGrade getGrade() {
        return grade;
    }

    public AdminDto setGrade(AdminGrade grade) {
        this.grade = grade;
        return this;
    }

    public AdminDisable getDisable() {
        return disable;
    }

    public AdminDto setDisable(AdminDisable disable) {
        this.disable = disable;
        return this;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public AdminDto setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AdminDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
