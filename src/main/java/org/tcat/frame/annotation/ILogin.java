package org.tcat.frame.annotation;

import org.tcat.frame.service.gm.enums.AdminGrade;
import org.tcat.frame.service.user.enums.UserIdType;
import org.tcat.frame.service.user.enums.UserType;

import java.lang.annotation.*;

/**
 * 用户访问权限控制的注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ILogin {

    /**
     * 是否需要登录：true 需要登录（默认）；false 不需要登录
     *
     * @return
     */
    boolean required() default true;

    /**
     * 用户id 类型,length为0时全部允许
     *
     * @return
     * @see org.tcat.frame.service.user.enums.UserIdType
     */
    UserIdType[] userIdType() default {};

    /**
     * 用户类型 ,length为0时全部允许
     *
     * @return
     * @see org.tcat.frame.service.user.enums.UserType
     */
    UserType[] userType() default {};

    /**
     * 管理员等级 ,length为0时全部允许
     *
     * @return
     * @see org.tcat.frame.service.gm.enums.AdminGrade
     */
    AdminGrade[] adminGrade() default {};

}
