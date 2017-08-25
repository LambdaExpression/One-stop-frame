package org.tcat.frame.support;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.tcat.frame.service.BaseRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by Lin on 2017/8/25.
 */
public class ExtJpaRepositoryFactoryBean<R extends BaseRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

    public ExtJpaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new ExtJpaRepositoryFactory(em);
    }

}
