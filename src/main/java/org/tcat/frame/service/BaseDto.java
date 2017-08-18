package org.tcat.frame.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lin on 2017/8/6.
 */
@ApiModel(value = "BaseDto", description = "基础对象")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_date", columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'", updatable = false)
    private Date createDate;
    /**
     * 创建用户id
     */
    @CreatedBy
    @ApiModelProperty(value = "创建用户id")
    @Column(name = "create_by", columnDefinition = "bigint(20) DEFAULT NULL COMMENT '创建人' ")
    private Long createBy;
    /**
     * 更新时间
     */
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_date", columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'")
    private Date updateDate;
    /**
     * 更新用户id
     */
    @LastModifiedBy
    @ApiModelProperty(value = "更新用户id")
    @Column(name = "update_by", columnDefinition = "bigint(20) DEFAULT NULL COMMENT '更新人' ")
    private Long updateBy;

    /**
     * 创建时间
     *
     * @return 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     *
     * @param createDate 创建时间
     * @return this
     */
    public BaseDto setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    /**
     * 创建用户id
     *
     * @return 创建用户id
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 创建用户id
     *
     * @param createBy 创建用户id
     * @return this
     */
    public BaseDto setCreateBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    /**
     * 更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 更新时间
     *
     * @param updateDate 更新时间
     * @return this
     */
    public BaseDto setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    /**
     * 更新用户id
     *
     * @return 更新用户id
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * 更新用户id
     *
     * @param updateBy 更新用户id
     * @return this
     */
    public BaseDto setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
        return this;
    }
}
