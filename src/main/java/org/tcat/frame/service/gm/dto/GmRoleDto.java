package org.tcat.frame.service.gm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.tcat.frame.service.BaseDto;
import org.tcat.frame.service.gm.enums.AdminDisable;

import javax.persistence.*;

/**
 * 管理员角色表对象
 * Created by Lin on 2017/8/24.
 */
@Entity(name = "gm$role")
@Table(appliesTo = "gm$role", comment = "管理员角色表")
@ApiModel(value = "GmRoleDto", description = "管理员角色表对象")
public class GmRoleDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "ID")
    @Column
    private Long id;

    @ApiModelProperty(value = "中文名称")
    @Column(name = "name_cn")
    private String nameCn;

    @ApiModelProperty(value = "英文名称")
    @Column(name = "name_en")
    private String nameEn;

    @ApiModelProperty(value = "是否禁用")
    @Column(name = "disable")
    private AdminDisable disable;

    public Long getId() {
        return id;
    }

    public GmRoleDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public GmRoleDto setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public GmRoleDto setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public AdminDisable getDisable() {
        return disable;
    }

    public GmRoleDto setDisable(AdminDisable disable) {
        this.disable = disable;
        return this;
    }
}
