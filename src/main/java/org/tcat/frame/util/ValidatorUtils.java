package org.tcat.frame.util;

import org.tcat.frame.bean.ResultObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 通过ValidatorFactory得到了一个 Validator 的实例. </br>
 * Validator是线程安全的,并且可以重复使用, 所以我们把它保存成一个类变量<br>
 *
 * @author shadow
 * @Description: 验证对象是否合法
 */
public class ValidatorUtils {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /**
     * 对目标对象的所有属性进行验证
     *
     * @param obj
     */
    public static ResultObject validate(Object obj) {
        ResultObject result = new ResultObject();
        if (obj != null) {
            Set<ConstraintViolation<Object>> violations = validator.validate(obj);
            if (violations != null && !violations.isEmpty()) {
                ConstraintViolation<Object> cv = violations.iterator().next();
                return result.setResult(false)
                        .setMessage(obj.getClass().getName() + "[" + cv.getPropertyPath() + "]:" + cv.getMessage());
            }
        }
        return result;
    }

    /**
     * nspaces校验帮助方法，在ctrl中调用，配合hibernate的valid注解，ctrl中需要
     *
     * @param obj
     * @param ignoreFields 忽略字段列表，逗号分隔
     */
    public static ResultObject validate(Object obj, String ignoreFields) {
        ResultObject result = new ResultObject();
        if (obj == null) {
            return result;
        }
        // 忽略列表
        Set<String> ignoreSet = new HashSet<>();
        if (ignoreFields != null) {
            String[] ignoreFieldArr = ignoreFields.replace(" ", "").split(",");
            for (String str : ignoreFieldArr) {
                ignoreSet.add(str);
            }
        }

        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (violations != null && !violations.isEmpty()) {
            Iterator<ConstraintViolation<Object>> it = violations.iterator();
            ConstraintViolation<Object> cv = null;
            while (it.hasNext()) {
                cv = it.next();
                if (ignoreSet.contains(cv.getPropertyPath().toString())) {
                    continue;
                }
                return result.setResult(false)
                        .setMessage(obj.getClass().getName() + "[" + cv.getPropertyPath() + "]:" + cv.getMessage());
            }
        }
        return result;
    }

    /**
     * 对目标对象的非空属性进行验证
     *
     * @param obj
     */
    public static ResultObject validateNotNull(Object obj) {
        ResultObject result = new ResultObject();
        if (obj == null)
            return result;
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (violations == null || violations.isEmpty()) {
            return result;
        }

        for (ConstraintViolation<Object> cv : validator.validate(obj)) {
            if (cv.getInvalidValue() != null) {
                return result.setResult(false)
                        .setMessage(obj.getClass().getName() + "[" + cv.getPropertyPath() + "]:" + cv.getMessage());
            }
        }
        return result;
    }
}
