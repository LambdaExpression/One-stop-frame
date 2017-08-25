package org.tcat.frame.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.tcat.frame.support.ExtJpaRepositoryFactoryBean;

/**
 * Created by Lin on 2017/8/3.
 */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = ExtJpaRepositoryFactoryBean.class, value = {"org.tcat.frame.service.*.**"})
@EnableJpaAuditing
public class JpaConfiguration {


}
