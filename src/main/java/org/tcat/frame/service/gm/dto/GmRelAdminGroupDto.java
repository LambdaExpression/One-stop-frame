package org.tcat.frame.service.gm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.tcat.frame.service.BaseDto;

import javax.persistence.*;

/**
 * 管理员与分组关联表对象
 * Created by Lin on 2017/8/24.
 */
@Entity(name = "gm$rel_admin_group")
@Table(appliesTo = "gm$rel_admin_group", comment = "管理员与分组关联表")
@ApiModel(value = "GmRelAdminGroupDto", description = "管理员与分组关联表对象")
public class GmRelAdminGroupDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "ID")
    @Column
    private Long id;

    @ApiModelProperty(value = "管理员id")
    @Column(name = "admin_id", nullable = false)
    private Long adminId;

    @ApiModelProperty(value = "分组id")
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    public Long getId() {
        return id;
    }

    public GmRelAdminGroupDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAdminId() {
        return adminId;
    }

    public GmRelAdminGroupDto setAdminId(Long adminId) {
        this.adminId = adminId;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public GmRelAdminGroupDto setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }
}
