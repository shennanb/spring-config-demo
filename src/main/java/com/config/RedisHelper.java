package com.config;


import cn.hutool.json.JSONUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作
 */
public class RedisHelper {

    public static final Charset utf8 = Charset.forName("utf8");

    /**
     * 设置值
     */
    public static void set(RedisTemplate<String, String> redis, final String hkey, final String value) {
        redis.opsForValue().set(hkey, value);
    }

    /**
     * 设置值-并设置过期时间
     */
    public static void set(RedisTemplate<String, String> redis, final String hkey, final String value, final Long timeout, final TimeUnit unit) {
        redis.opsForValue().set(hkey, value, timeout, unit);
    }

    /**
     * 获取值
     */
    public static String get(RedisTemplate<String, String> redis, final String hmKey) {
        return redis.opsForValue().get(hmKey);
    }

    /**
     * 获取值
     */
    public static <T> T get(RedisTemplate<String, String> redis, final String hmKey, Class<T> clazz) {
        String str = get(redis, hmKey);
        if (!StringUtils.isEmpty(str)) {
            return JSONUtil.toBean(str, clazz);
        }
        return null;
    }

    /**
     * 删除
     */
    public static void delete(RedisTemplate<String, String> redis, String key) {
        redis.delete(key);
    }

    /**
     * 设置值,如果存在，则忽略并返回false
     */
    public static boolean setNX(RedisTemplate<String, String> redis, final String hkey, final String value) {
        return redis.opsForValue().setIfAbsent(hkey, value);
    }

    /**
     * 设置值-并设置过期时间,如果存在，则忽略并返回false
     */
    public static boolean setNX(RedisTemplate<String, String> redis, final String hkey, final String value, final Integer timeout) {
        return setNX(redis, hkey, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置值-并设置过期时间,如果存在，则忽略并返回false
     */
    public static boolean setNX(RedisTemplate<String, String> redis, final String hkey, final String value, final Integer timeout, TimeUnit timeUnit) {
        return redis.execute(new SessionCallback<Boolean>() {
            @Override
            public <K, V> Boolean execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                redisOperations.multi();
                redis.opsForValue().setIfAbsent(hkey, value);
                redis.expire(hkey, timeout, timeUnit);
                List<Object> exec = redisOperations.exec();
                if (exec.size() > 0) {
                    return (Boolean) exec.get(0);
                }
                return false;
            }
        });
    }

    /**
     * 过期key
     */
    public static boolean expire(RedisTemplate<String, String> redis, final String key, Long timeout) {
        return redis.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 过期key
     */
    public static boolean expire(RedisTemplate<String, String> redis, final String key) {
        return redis.expire(key, 0, TimeUnit.SECONDS);
    }

    /**
     * 验证是否过期
     */
    public static boolean isExpired(RedisTemplate<String, String> redis, final String key) {
        return redis.hasKey(key) && redis.getExpire(key) > 0;
    }

    /**
     * 获取唯一Id
     *
     * @param key
     * @param delta 增加量（不传采用1）
     * @return
     * @throws Exception
     */
    public static String incrementString(RedisTemplate<String, String> redis, String key, Long delta) {
        try {
            if (null == delta) {
                delta = 1L;
            }
            return redis.opsForValue().increment(key, delta).toString();
        } catch (Exception e) {
            //redis宕机时采用uuid的方式生成唯一id
            int first = new Random(10).nextInt(8) + 1;
            int randNo = UUID.randomUUID().hashCode();
            if (randNo < 0) {
                randNo = -randNo;
            }
            return first + String.format("%16d", randNo);
        }
    }

    /**
     * 自增值
     *
     * @param redis
     * @param key
     * @param delta 增加量（不传采用1）
     * @return
     */
    public static Long increment(RedisTemplate<String, String> redis, String key, Long delta) {
        try {
            if (null == delta){
                delta = 1L;}
            return redis.opsForValue().increment(key, delta);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 自增值1
     *
     * @param redis
     * @param key
     * @return
     */
    public static Long increment(RedisTemplate<String, String> redis, String key) {
        return increment(redis, key, 1L);
    }

    /**
     * 返回集合中所有元素
     *
     * @return
     * @paramkey
     */
    public static Set<String> members(RedisTemplate<String, String> redis, String key) {
        return redis.opsForSet().members(key);
    }

    /**
     * 添加set元素
     *
     * @return
     * @paramkey
     * @paramvalues
     */
    public static Long addSet(RedisTemplate<String, String> redis, String key, String... values) {
        return redis.opsForSet().add(key, values);
    }

    /**
     * 判断set集合中是否有value
     *
     * @return
     * @paramkey
     * @paramvalue
     */
    public static boolean isMember(RedisTemplate<String, String> redis, String key, Object value) {
        return redis.opsForSet().isMember(key, value);
    }

//    public static Boolean zAdd(RedisTemplate<String, String> redis, final String key, final Object value, final double score) {
//        return redis.execute(new RedisCallback<Boolean>() {
//
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                String v;
//                if (value instanceof Object) {
//                    v = JSONHelper.objectToJson(value);
//                } else {
//                    v = value.toString();
//                }
//                return connection.zAdd(key.getBytes(utf8), score, v.getBytes(utf8));
//            }
//        });
//    }

    /**
     * 指定list从左入栈
     *
     * @paramkey
     * @return当前队列的长度
     */
    public static Long leftPush(RedisTemplate<String, String> redis, String key, String value) {
        return redis.opsForList().leftPush(key, value);
    }

    /**
     * 指定list从左出栈
     * 如果列表没有元素,会在100毫秒内返回空，或者获取到100毫秒内的新值
     *
     * @paramkey
     * @return出栈的值
     */
    public static String leftPop(RedisTemplate<String, String> redis, String key) {
        return redis.opsForList().leftPop(key, 100L, TimeUnit.MILLISECONDS);
    }

    /**
     * 指定list从左入栈
     *
     * @paramkey
     * @return当前队列的长度
     */
    public static Long leftPushAll(RedisTemplate<String, String> redis, String key, Collection<String> value) {
        return redis.opsForList().leftPushAll(key, value);
    }

    /**
     * 获取队列的长度
     *
     * @paramkey
     * @return出栈的值
     */
    public static Long listSize(RedisTemplate<String, String> redis, String key) {
        return redis.opsForList().size(key);
    }

}
