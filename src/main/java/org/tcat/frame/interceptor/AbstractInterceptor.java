package org.tcat.frame.interceptor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.annotation.Annotation;

public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 获取类级别的注解
	 * 
	 * @param handlerMethod
	 * @param type
	 * @return
	 */
	protected <T extends Annotation> T getAnnotationWithBean(HandlerMethod handlerMethod, Class<T> type) {
		return AnnotationUtils.getAnnotation(handlerMethod.getBeanType(), type);
	}

	/**
	 * 获取方法级别的注解
	 * 
	 * @param handlerMethod
	 * @param type
	 * @return
	 */
	protected <T extends Annotation> T getAnnotationWithMethod(HandlerMethod handlerMethod, Class<T> type) {
		return handlerMethod.getMethodAnnotation(type);
	}

}