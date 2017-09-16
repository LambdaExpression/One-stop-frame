package org.tcat.frame.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.tcat.frame.annotation.ILogin;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.bean.UserSession;
import org.tcat.frame.exception.code.ErrorCode;
import org.tcat.frame.service.gm.enums.AdminGrade;
import org.tcat.frame.service.user.enums.UserIdType;
import org.tcat.frame.service.user.enums.UserType;
import org.tcat.frame.util.PropertiesUtil;
import org.tcat.frame.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户访问权限控制 拦截器
 */
public class ILoginInterceptor extends AbstractInterceptor {

    private final static String DOMAIN = PropertiesUtil.getValue("config.properties", "domain");
    private final static String USER = PropertiesUtil.getValue("config.properties", "user.session");

    private final static String USER_LOGIN = DOMAIN + "/user/login";
    private final static String NO_PERMISSION = DOMAIN + "/error/403";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserSession userSession = null;
        Object usession = WebUtils.getHttpSession().getAttribute(USER);
        if (usession instanceof UserSession) {
            userSession = (UserSession) usession;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ILogin iBean = getAnnotationWithBean(handlerMethod, ILogin.class);
            ILogin iMethod = getAnnotationWithMethod(handlerMethod, ILogin.class);
            if (!ILoginCheck(request, response, iBean, userSession)) {
                return false;
            }
            if (!ILoginCheck(request, response, iMethod, userSession)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限控制
     *
     * @param request
     * @param response
     * @param iLogin
     * @param userSession
     * @return
     * @throws Exception
     */
    private boolean ILoginCheck(HttpServletRequest request, HttpServletResponse response
            , ILogin iLogin, UserSession userSession) throws Exception {
        if (iLogin != null) {
            if (iLogin.required()) {
                if (userSession == null) {
                    if (HttpMethod.GET.name().equals(request.getMethod())) {
                        response.sendRedirect(USER_LOGIN);
                    } else {
                        ajaxResponse(request, response, JsonObject.error(ErrorCode.U_login_Un));
                    }
                    return false;
                }
                boolean flag = false;
                if (iLogin.userIdType().length > 0) {
                    Set<UserIdType> userIdTypeSet = new HashSet<>(Arrays.asList(iLogin.userIdType()));
                    if (userIdTypeSet.contains(UserIdType.USER)) {
                        if (iLogin.userType().length > 0) {
                            if (new HashSet<>(Arrays.asList(iLogin.userType())).contains(UserType.findByValue(userSession.getType()))) {
                                flag = true;
                            }
                        } else {
                            flag = true;
                        }
                    }
                    if (userIdTypeSet.contains(UserIdType.STAFF)) {
                        if (iLogin.adminGrade().length > 0) {
                            if (new HashSet<>(Arrays.asList(iLogin.adminGrade())).contains(AdminGrade.findByValue(userSession.getGrade()))) {
                                flag = true;
                            }
                        } else {
                            flag = true;
                        }
                    }
                }
                if (!flag) {
                    if (HttpMethod.GET.name().equals(request.getMethod())) {
                        response.sendRedirect(NO_PERMISSION);
                    } else {
                        ajaxResponse(request, response, JsonObject.error(ErrorCode.U_No_Permission));
                    }
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 封装 返回值
     *
     * @param request
     * @param response
     * @param json
     * @throws Exception
     */
    private void ajaxResponse(HttpServletRequest request, HttpServletResponse response, JsonObject json) throws Exception {
        response.getOutputStream().write(json.toString().getBytes("UTF-8"));
        response.setContentType("text/json; charset=UTF-8");
    }

}
