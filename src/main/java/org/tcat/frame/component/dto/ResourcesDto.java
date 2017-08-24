package org.tcat.frame.component.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Lin on 2017/8/24.
 */
@ApiModel(value = "ResourcesDto", description = "后台资源对象")
public class ResourcesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "上级资源id")
    private String pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "路径")
    private String url;

    @ApiModelProperty(value = "排序 数字越小越靠前")
    private Integer rank;

    @ApiModelProperty(value = "类型 0：菜单 1：功能")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;

    public String getId() {
        return id;
    }

    public ResourcesDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public ResourcesDto setPid(String pid) {
        this.pid = pid;
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

    public Integer getRank() {
        return rank;
    }

    public ResourcesDto setRank(Integer rank) {
        this.rank = rank;
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
    
}
