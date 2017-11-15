package com.technothack.michael.music;

import android.support.v4.util.LruCache;

public class Cache {

    private static Cache instance;
    private LruCache<Object, Object> lru;
    public static int casheSize = 4 * 1024;

    private Cache() {
        lru = new LruCache<Object, Object>(casheSize);
    }

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }
}