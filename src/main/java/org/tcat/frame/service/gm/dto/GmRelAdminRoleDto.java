package org.tcat.frame.service.gm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.tcat.frame.service.BaseDto;

import javax.persistence.*;

/**
 * Created by Lin on 2017/8/24.
 */
@Entity(name = "gm$rel_admin_role")
@Table(appliesTo = "gm$rel_admin_role", comment = "管理员与角色关联表")
@ApiModel(value = "GmRelAdminRoleDto", description = "管理员与角色关联表对象")
public class GmRelAdminRoleDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "ID")
    @Column
    private Long id;

    @ApiModelProperty(value = "管理员id")
    @Column(name = "admin_id", nullable = false)
    private Long adminId;

    @ApiModelProperty(value = "角色id")
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    public Long getId() {
        return id;
    }

    public GmRelAdminRoleDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAdminId() {
        return adminId;
    }

    public GmRelAdminRoleDto setAdminId(Long adminId) {
        this.adminId = adminId;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public GmRelAdminRoleDto setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }
}
