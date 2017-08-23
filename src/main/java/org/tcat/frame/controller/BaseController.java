package org.tcat.frame.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tcat.frame.bean.UserSession;
import org.tcat.frame.util.PropertiesUtil;
import org.tcat.frame.util.StringUtils;
import org.tcat.frame.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Properties;

/**
 * 控制器基础类
 * Created by Lin on 2017/8/3.
 */
@Transactional
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final String USER = "USER_IN_SESSION";
    protected final Properties properties = PropertiesUtil.loadPropertiesFromResourceFile("config.properties");


    /**
     * 获取Session信息
     *
     * @return HttpSession
     */
    protected HttpSession getHttpSession() {
        return WebUtils.getHttpSession();
    }

    /**
     * 设置用户Session
     *
     * @param userSession 用户Session
     */
    protected void setUserSession(UserSession userSession) {
        this.getHttpSession().setAttribute(USER, userSession);
    }

    /**
     * 获取用户Session
     *
     * @return 用户Session
     */
    protected UserSession getUserSession() {
        Object userSession = this.getHttpSession().getAttribute(USER);
        if (userSession instanceof UserSession) {
            return (UserSession) userSession;
        }
        return null;
    }

    /**
     * 清除Session中的用户信息，用于用户退出系统
     */
    protected void resetSession() {
        getHttpSession().removeAttribute(USER);
        getHttpSession().invalidate();
    }


    /**
     * 添加cookie
     *
     * @param cookie
     */
    protected void addCookie(Cookie cookie) {
        WebUtils.getHttpServletResponse().addCookie(cookie);
    }

    /**
     * 根据名字取出cookie的值
     *
     * @param name
     * @return
     */
    protected String popCookie(String name) {
        Cookie[] cookies = WebUtils.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 获取 config.properties 的配置参数
     *
     * @param key key
     * @return config.properties 对应的配置参数
     */
    protected String getProperties(String key) {
        if (StringUtils.isEmptyByTrim(key)) {
            return null;
        }
        return properties.getProperty(key);
    }

}
