package org.tcat.frame.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.tcat.frame.bean.UserSession;
import org.tcat.frame.support.ExtJpaRepositoryFactoryBean;
import org.tcat.frame.util.PropertiesUtil;
import org.tcat.frame.util.WebUtils;

/**
 * Created by Lin on 2017/8/3.
 */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = ExtJpaRepositoryFactoryBean.class, value = {"org.tcat.frame.service.*.**"})
@EnableJpaAuditing
public class JpaConfiguration implements AuditorAware<Long> {

    private final String USER = PropertiesUtil.getValue("config.properties", "user.session");

    @Override
    public Long getCurrentAuditor() {
        if (getUserSession() == null) {
            return null;
        }
        return getUserSession().getId();
    }

    /**
     * 获取用户Session
     *
     * @return 用户Session
     */
    private UserSession getUserSession() {
        Object userSession = WebUtils.getHttpSession().getAttribute(USER);
        if (userSession instanceof UserSession) {
            return (UserSession) userSession;
        }
        return null;
    }

}
