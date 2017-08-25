package org.tcat.frame.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 基础数据访问类
 * Created by Lin on 2017/8/4.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * insert or dynamic update entity (will findOne first)
     * @param id entity id
     * @param entity entity
     * @return entity
     */
    T dynamicSave(ID id, T entity);

}
