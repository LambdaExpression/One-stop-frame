package org.tcat.frame.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * BeansConverter 使用的json对象转换工具
 * Created by Lin on 2017/7/14.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface Json {

    /**
     * 转换class类型
     *
     * @return
     */
    Class value();

}
