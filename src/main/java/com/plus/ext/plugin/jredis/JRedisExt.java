package com.plus.ext.plugin.jredis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * Redis 扩展
 */
public enum JRedisExt {
    ice;

    /**
     * clean 清空一个Redis SCAN结果集
     *
     * @param count   要clean的数量,为0时为不限制数量
     * @param pattern 正则表达式,例如: USER:1:*
     * @return
     */
    public Boolean clean(Integer count, String pattern) {
        String cursor = "0";
        Boolean bool = false;
        try {
            if (pattern != "*") {   //不允许扫描全库
                ScanParams scanParams;
                if (count > 0) {
                    if (StringUtils.isNotBlank(pattern)) {
                        scanParams = new ScanParams().count(count).match(pattern);
                    } else {
                        scanParams = new ScanParams().count(count);
                    }
                } else {
                    if (StringUtils.isNotBlank(pattern)) {
                        scanParams = new ScanParams().match(pattern);
                    } else {
                        scanParams = new ScanParams();
                    }
                }
                while (true) {
                    ScanResult<String> sr = JRedis.use().scan(cursor, scanParams);
                    String[] keys = new String[sr.getResult().size()];
                    for (int i = 0; i < sr.getResult().size(); i++) {
                        keys[i] = sr.getResult().get(i);
                    }
                    JRedis.use().del(keys);     //删除多个key,不存在的key会被忽略
                    cursor = sr.getStringCursor();
                    if (cursor.equals("0")) {
                        bool = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            bool = false;
        } finally {
            return bool;
        }
    }


}
