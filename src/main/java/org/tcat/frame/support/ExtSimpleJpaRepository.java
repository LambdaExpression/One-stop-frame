package org.tcat.frame.support;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.tcat.frame.util.BeansConverter;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by Lin on 2017/8/25.
 */
public class ExtSimpleJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtJpaRepository<T, ID> {

    private final EntityManager em;

    public ExtSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }

    public ExtSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    @Transactional
    public T dynamicSave(ID id, T entity) {
        T managedEntity = this.findOne(id);
        T mergedEntity;
        if (managedEntity == null) {
            em.persist(entity);
            mergedEntity = entity;
        } else {
            BeanUtils.copyProperties(entity, managedEntity, BeansConverter.findNullForBean(entity).toArray(new String[]{}));
            em.merge(managedEntity);
            mergedEntity = managedEntity;
        }
        return mergedEntity;
    }

}
