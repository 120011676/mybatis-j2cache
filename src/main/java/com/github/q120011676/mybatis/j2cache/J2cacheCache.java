package com.github.q120011676.mybatis.j2cache;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import org.apache.ibatis.cache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by say on 3/21/16.
 */
public class J2cacheCache implements Cache {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private CacheChannel cache = J2Cache.getChannel();
    private String id;
    private Map<Object, Object> map = new HashMap<Object, Object>();

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
        this.map.put(o, null);
    }

    @Override
    public Object getObject(Object o) {
        return this.cache.get(this.id, o).getValue();
    }

    @Override
    public Object removeObject(Object o) {
        Object obj = this.cache.get(this.id, o).getValue();
        this.cache.evict(this.id, o);
        this.map.remove(o);
        return obj;
    }

    @Override
    public void clear() {
        this.cache.clear(this.id);
    }

    @Override
    public int getSize() {
        return this.map.size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
