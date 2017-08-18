package org.tcat.frame.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * 基础数据访问类
 * Created by Lin on 2017/8/4.
 */
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
