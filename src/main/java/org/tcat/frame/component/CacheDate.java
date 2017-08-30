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
public class CacheDate {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存最少相差时间（秒）
     */
    private static final int LEAST_SAVE_TIME_SECONDS = 30;
    /**
     * 默认保存时间(秒)
     */
    private static final int DEFAULT_SAVE_TIME_SECONDS = 24 * 60 * 60;

    private static Map<String, Cache> cacheMap = new ConcurrentHashMap<>();

    @Autowired
    private DynamicTimer dynamicTimer;

    public synchronized boolean put(String key, Object value) {
        return putSeconds(key, value, DEFAULT_SAVE_TIME_SECONDS);
    }

    public synchronized boolean putSeconds(String key, Object value, int amount) {
        Date expiration = DateUtils.addSeconds(new Date(), DEFAULT_SAVE_TIME_SECONDS);
        return put(key, value, expiration);
    }

    public synchronized boolean putMinutes(String key, Object value, int amount) {
        Date expiration = DateUtils.addMinutes(new Date(), DEFAULT_SAVE_TIME_SECONDS);
        return put(key, value, expiration);
    }

    public synchronized boolean putDays(String key, Object value, int amount) {
        Date expiration = DateUtils.addDays(new Date(), DEFAULT_SAVE_TIME_SECONDS);
        return put(key, value, expiration);
    }

    private synchronized boolean put(String key, Object value, Date expiration) {
        if (expiration == null || expiration.getTime() < DateUtils.addSeconds(new Date(), LEAST_SAVE_TIME_SECONDS).getTime()) {
            logger.error("添加缓存数据 过期时间空或已过期 data={}", expiration);
            return false;
        }
        cacheMap.put(key, new Cache(value, expiration));
        dynamicTimer.addCronTask(new Runnable() {
            @Override
            public void run() {
                if (expiration.equals(getExpiration(key))) {
                    remove(key);
                }
                System.out.println("expiration =" + DateUtils.format(expiration, DateUtils.TIMESTAMP_PATTERN));
                System.out.println("key =" + key);
            }
        }, expiration);
        return true;
    }

    public synchronized Object get(String key) {
        Cache cache = cacheMap.get(key);
        if (cache != null) {
            return cache.getData();
        }
        return null;
    }

    public synchronized Date getExpiration(String key) {
        Cache cache = cacheMap.get(key);
        if (cache != null) {
            return cache.getExpiration();
        }
        return null;
    }

    public synchronized void remove(String key) {
        cacheMap.remove(key);
    }

    public synchronized void show() {
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(cacheMap));
    }

    /**
     * 缓存对象
     */
    private class Cache implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 缓存数据
         */
        private Object data;
        /**
         * 过期时间
         */
        private Date expiration;

        public Cache(Object data, Date expiration) {
            this.data = data;
            this.expiration = expiration;
        }

        public Object getData() {
            return data;
        }

        public Cache setData(Object data) {
            this.data = data;
            return this;
        }

        public Date getExpiration() {
            return expiration;
        }

        public Cache setExpiration(Date expiration) {
            this.expiration = expiration;
            return this;
        }
    }

}
