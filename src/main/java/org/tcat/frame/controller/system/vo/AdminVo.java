package org.tcat.frame.controller.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.tcat.frame.service.gm.dto.AdminDto;
import org.tcat.frame.service.gm.dto.GmGroupDto;
import org.tcat.frame.service.gm.dto.GmRoleDto;

import java.util.List;

@ApiModel(value = "AdminVo", description = "管理员帐户表对象")
public class AdminVo extends AdminDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属角色")
    List<GmRoleDto> gmRoleDtoList;

    @ApiModelProperty(value = "所属分组")
    List<GmGroupDto> gmGroupDtoList;

    public List<GmRoleDto> getGmRoleDtoList() {
        return gmRoleDtoList;
    }

    public AdminVo setGmRoleDtoList(List<GmRoleDto> gmRoleDtoList) {
        this.gmRoleDtoList = gmRoleDtoList;
        return this;
    }

    public List<GmGroupDto> getGmGroupDtoList() {
        return gmGroupDtoList;
    }

    public AdminVo setGmGroupDtoList(List<GmGroupDto> gmGroupDtoList) {
        this.gmGroupDtoList = gmGroupDtoList;
        return this;
    }
}
