/**
 * Copyright (c) 2009-2016, LarryKoo 老古 (gumutianqi@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plus.ext.plugin.jredis;

/**
 * 继承自JFinal的Redis对象
 * <p/>
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.jfinal.kit.StrKit;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis.
 * redis 工具类
 * <pre>
 * 例如：
 * Redis.use().set("key", "value");
 * Redis.use().get("key");
 * </pre>
 */
public class JRedis {

    static JCache mainCache = null;

    private static final ConcurrentHashMap<String, JCache> cacheMap = new ConcurrentHashMap<String, JCache>();

    static synchronized void addCache(JCache cache) {
        if (cache == null)
            throw new IllegalArgumentException("cache can not be null");
        if (cacheMap.containsKey(cache.getName()))
            throw new IllegalArgumentException("cache already exists");

        cacheMap.put(cache.getName(), cache);
        if (mainCache == null)
            mainCache = cache;
    }

    static JCache removeCache(String cacheName) {
        return cacheMap.remove(cacheName);
    }

    /**
     * 提供一个设置设置主缓存 mainCache 的机会，否则第一个被初始化的 Cache 将成为 mainCache
     */
    public static void setMainCache(String cacheName) {
        if (StrKit.isBlank(cacheName))
            throw new IllegalArgumentException("cacheName can not be blank");
        cacheName = cacheName.trim();
        JCache cache = cacheMap.get(cacheName);
        if (cache == null)
            throw new IllegalArgumentException("the cache not exists: " + cacheName);

        JRedis.mainCache = cache;
    }

    public static JCache use() {
        return mainCache;
    }

    public static JCache use(String cacheName) {
        return cacheMap.get(cacheName);
    }

    public static Object call(JICallback callback) {
        return call(callback, null);
    }

    public static Object call(JICallback callback, String cacheName) {
        JCache cache = (cacheName != null ? use(cacheName) : use());
        Jedis jedis = cache.getThreadLocalJedis();
        boolean notThreadLocalJedis = (jedis == null);
        if (notThreadLocalJedis) {
            jedis = cache.jedisPool.getResource();
            cache.setThreadLocalJedis(jedis);
        }
        try {
            return callback.call(cache);
        } finally {
            if (notThreadLocalJedis) {
                cache.removeThreadLocalJedis();
                jedis.close();
            }
        }
    }
}





