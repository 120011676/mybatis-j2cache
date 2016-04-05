package com.github.q120011676.mybatis.j2cache;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.J2Cache;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by say on 3/21/16.
 */
public class J2cacheCache implements Cache {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private CacheChannel cache = J2Cache.getChannel();
    private String id;
    private static final Logger LOGGER = LoggerFactory.getLogger(J2cacheCache.class);

    public J2cacheCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        this.cache.set(this.id, o, o1);
    }

    @Override
    public Object getObject(Object o) {
        Object obj = this.cache.get(this.id, o).getValue();
        return obj;
    }

    @Override
    public Object removeObject(Object o) {
        Object obj = this.cache.get(this.id, o).getValue();
        if (obj != null) {
            try {
                this.cache.evict(this.id, o);
            } catch (CacheException e) {
                LOGGER.warn(e.getLocalizedMessage());
            }
        }
        return obj;
    }

    @Override
    public void clear() {
        List keys = this.cache.keys(this.getId());
        if (keys != null && keys.size() > 0) {
            try {
                this.cache.clear(this.getId());
            } catch (CacheException e) {
                LOGGER.warn(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public int getSize() {
        List keys = this.cache.keys(this.getId());
        return keys != null ? keys.size() : 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
