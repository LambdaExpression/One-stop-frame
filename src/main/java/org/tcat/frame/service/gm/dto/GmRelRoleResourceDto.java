package org.tcat.frame.service.gm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.tcat.frame.service.BaseDto;

import javax.persistence.*;

/**
 * 角色与资源关联表对象
 * Created by Lin on 2017/8/24.
 */
@Entity(name = "gm$rel_role_resource")
@Table(appliesTo = "gm$rel_role_resource", comment = "角色与资源关联表")
@ApiModel(value = "GmRelRoleResourceDto", description = "角色与资源关联表对象")
public class GmRelRoleResourceDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "ID")
    @Column
    private Long id;

    @ApiModelProperty(value = "角色id")
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @ApiModelProperty(value = "资源id")
    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    public Long getId() {
        return id;
    }

    public GmRelRoleResourceDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public GmRelRoleResourceDto setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getResourceId() {
        return resourceId;
    }

    public GmRelRoleResourceDto setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

}
