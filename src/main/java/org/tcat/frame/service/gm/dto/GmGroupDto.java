package org.tcat.frame.service.gm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.tcat.frame.service.BaseDto;
import org.tcat.frame.service.gm.enums.AdminDisable;

import javax.persistence.*;

/**
 * 管理员分组表对象
 * Created by Lin on 2017/8/24.
 */
@Entity(name = "gm$group")
@Table(appliesTo = "gm$group", comment = "管理员分组表")
@ApiModel(value = "GmGroupDto", description = "管理员分组表对象")
public class GmGroupDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "ID")
    @Column
    private Long id;

    @ApiModelProperty(value = "上级id")
    @Column(name = "p_id")
    private Long pid;

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

    public GmGroupDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPid() {
        return pid;
    }

    public GmGroupDto setPid(Long pid) {
        this.pid = pid;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public GmGroupDto setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public GmGroupDto setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public AdminDisable getDisable() {
        return disable;
    }

    public GmGroupDto setDisable(AdminDisable disable) {
        this.disable = disable;
        return this;
    }
}
