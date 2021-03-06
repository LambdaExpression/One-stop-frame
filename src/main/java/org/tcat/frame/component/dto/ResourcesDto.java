package org.tcat.frame.component.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lin on 2017/8/24.
 */
@ApiModel(value = "ResourcesDto", description = "后台资源对象")
public class ResourcesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "路径")
    private String url;

    @ApiModelProperty(value = "类型 0：菜单 1：功能")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "子资源管理")
    private List<ResourcesDto> child;

    public String getId() {
        return id;
    }

    public ResourcesDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ResourcesDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ResourcesDto setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ResourcesDto setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ResourcesDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public List<ResourcesDto> getChild() {
        return child;
    }

    public ResourcesDto setChild(List<ResourcesDto> child) {
        this.child = child;
        return this;
    }
}
