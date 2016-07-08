package com.plus.ext.plugin.jredis;

/**
 * ICallback.
 * 将多个 redis 操作放在同一个redis连下中使用，另外也可以让同一个
 * Cache 对象使用 select(int) 方法临时切换数据库
 */
public interface JICallback {
    <T> T call(JCache cache);
}
