package org.tcat.frame.support;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tcat.frame.service.BaseRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2017/8/25.
 */
@Repository
@Transactional(readOnly = true)
public class SimpleExtJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {


    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public SimpleExtJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public SimpleExtJpaRepository(Class<T> domainClass, EntityManager entityManager) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager), entityManager);
    }

    /*
         * (non-Javadoc)
         * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
         */
    @Transactional
    public <S extends T> S save(S entity) {
        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaRepository#saveAndFlush(java.lang.Object)
     */
    @Transactional
    public <S extends T> S saveAndFlush(S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaRepository#save(java.lang.Iterable)
     */
    @Transactional
    public <S extends T> List<S> save(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        if (entities == null) {
            return result;
        }
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     */
    @Transactional
    public void flush() {
        em.flush();
    }

}
