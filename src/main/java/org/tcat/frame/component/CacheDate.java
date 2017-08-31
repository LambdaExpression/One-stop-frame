package org.tcat.frame.component;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tcat.frame.util.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lin on 2017/8/30.
 */
@Component("cacheDate")
public final class CacheDate {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存最少相差时间（秒）
     */
    private static final int LEAST_SAVE_TIME_SECONDS = 30;
    /**
     * 默认保存时间(秒)
     */
    private static final int DEFAULT_SAVE_TIME_SECONDS = 24 * 60 * 60;

    private static Map<String, CacheDto> cacheMap = new ConcurrentHashMap<>();

    @Autowired
    private DynamicTimer dynamicTimer;

    public synchronized boolean put(String key, Object value) {
        return putSeconds(key, value, DEFAULT_SAVE_TIME_SECONDS);
    }

    public synchronized boolean putSeconds(String key, Object value, int amount) {
        Date expiration = DateUtils.addSeconds(new Date(), amount);
        return put(key, value, expiration);
    }

    public synchronized boolean putMinutes(String key, Object value, int amount) {
        Date expiration = DateUtils.addMinutes(new Date(), amount);
        return put(key, value, expiration);
    }

    public synchronized boolean putDays(String key, Object value, int amount) {
        Date expiration = DateUtils.addDays(new Date(), amount);
        return put(key, value, expiration);
    }

    private synchronized boolean put(String key, Object value, Date expiration) {
        if (expiration == null || expiration.getTime() < DateUtils.addSeconds(new Date(), LEAST_SAVE_TIME_SECONDS).getTime()) {
            logger.error("添加缓存数据 过期时间空或已过期 data={}", expiration);
            return false;
        }
        cacheMap.put(key, new CacheDto(value, expiration));
        dynamicTimer.addCronTask(
                new TimerRunnable() {
                    @Override
                    public void customize() {
                        if (expiration.equals(getExpiration(key))) {
                            remove(key);
                        }
                    }
                }.setOneTime(dynamicTimer)
                , expiration);
        return true;
    }

    public synchronized Object get(String key) {
        CacheDto cache = cacheMap.get(key);
        if (cache != null) {
            return cache.getData();
        }
        return null;
    }

    public synchronized <T> T get(String key, Class<T> t) {
        CacheDto cache = cacheMap.get(key);
        if (cache != null) {
            return (T) cache.getData();
        }
        return null;
    }

    public synchronized Date getExpiration(String key) {
        CacheDto cache = cacheMap.get(key);
        if (cache != null) {
            return cache.getExpiration();
        }
        return null;
    }

    public synchronized void remove(String key) {
        cacheMap.remove(key);
    }

    public synchronized String show() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(cacheMap);
    }

}

/**
 * 缓存对象
 */
final class CacheDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 缓存数据
     */
    private Object data;
    /**
     * 过期时间
     */
    private Date expiration;

    public CacheDto(Object data, Date expiration) {
        this.data = data;
        this.expiration = expiration;
    }

    public Object getData() {
        return data;
    }

    public CacheDto setData(Object data) {
        this.data = data;
        return this;
    }

    public Date getExpiration() {
        return expiration;
    }

    public CacheDto setExpiration(Date expiration) {
        this.expiration = expiration;
        return this;
    }

}