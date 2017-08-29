package org.tcat.frame.service;

import org.tcat.frame.support.ExtJpaRepository;

import java.io.Serializable;

/**
 * Created by Lin on 2017/8/29.
 */
public interface BaseRepository<T, ID extends Serializable> extends ExtJpaRepository<T, ID> {
}
