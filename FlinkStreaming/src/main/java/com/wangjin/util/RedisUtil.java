package com.wangjin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2018/10/28 0028.
 */
public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static JedisPool jedisPool = null;
    //初始化redis连接池
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(200);
            config.setMaxIdle(50);
            config.setMaxWaitMillis(1000 * 100);
            config.setTestOnBorrow(false);
            jedisPool = new JedisPool(config, "198.168.184.203",6379);
        }catch (Exception e) {
            logger.error("Jedis pool create fail!!!");
            e.printStackTrace();
        }
    }
    /**
     * 获取Jedis实例
     * @return
     */
    public static synchronized Jedis getJedis() {
        try{
            if (jedisPool != null){
                Jedis resource = jedisPool.getResource();
                return resource;
            }
            else {
                return null;
            }
        }catch (Exception e) {
            logger.error("Jedis get fail!!!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void closeResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
