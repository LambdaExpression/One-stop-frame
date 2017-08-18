package org.tcat.frame.service.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Table;
import org.tcat.frame.service.BaseDto;
import org.tcat.frame.service.user.enums.UserIdType;

import javax.persistence.*;

/**
 * 用户id表 对象
 * Created by Lin on 2017/8/7.
 */
@ApiModel(value = "UserIdDto", description = "用户id对象")
@Table(appliesTo = "user_id", comment = "用户id表")
@Entity(name = "user_id")
public class UserIdDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 用户id类型
     */
    @Column(nullable = false)
    @ApiModelProperty(value = "用户id类型 1:员工 2:普通用户")
    private UserIdType type;

    /**
     * id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     *
     * @param id id
     * @return this
     */
    public UserIdDto setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * 用户id类型
     *
     * @return 用户id类型
     */
    public UserIdType getType() {
        return type;
    }

    /**
     * 用户id类型
     *
     * @param type 用户id类型
     * @return this
     */
    public UserIdDto setType(UserIdType type) {
        this.type = type;
        return this;
    }
}
