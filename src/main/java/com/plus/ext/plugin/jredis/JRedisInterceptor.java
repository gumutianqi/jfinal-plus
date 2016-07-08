package com.plus.ext.plugin.jredis;

/**
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import redis.clients.jedis.Jedis;

/**
 * RedisInterceptor 用于在同一线程中共享同一个 jedis 对象，提升性能.
 * 目前只支持主缓存 mainCache，若想更多支持，参考此拦截器创建新的拦截器
 * 改一下Redis.use() 为 Redis.use(otherCache) 即可
 */
public class JRedisInterceptor implements Interceptor {

    /**
     * 通过继承 RedisInterceptor 类并覆盖此方法，可以指定
     * 当前线程所使用的 cache
     */
    protected JCache getCache() {
        return JRedis.use();
    }

    public void intercept(Invocation inv) {
        JCache cache = getCache();
        Jedis jedis = cache.getThreadLocalJedis();
        if (jedis != null) {
            inv.invoke();
            return ;
        }

        try {
            jedis = cache.jedisPool.getResource();
            cache.setThreadLocalJedis(jedis);
            inv.invoke();
        }
        finally {
            cache.removeThreadLocalJedis();
            jedis.close();
        }
    }
}


