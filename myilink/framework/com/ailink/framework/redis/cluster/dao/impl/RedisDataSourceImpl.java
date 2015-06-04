package com.ailink.framework.redis.cluster.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.ailink.framework.redis.cluster.dao.RedisDataSource;

/**
 * 
 * @author jlon
 * ShardedJedis是基于一致性哈希算法实现的分布式Redis集群客户端；
 */
@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {

	@Autowired
	private ShardedJedisPool shardedJedisPool;

	public ShardedJedis getRedisClient() {
		try {
			ShardedJedis shardJedis = shardedJedisPool.getResource();
			return shardJedis;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void returnResource(ShardedJedis shardedJedis) {
		shardedJedisPool.returnResource(shardedJedis);
	}

	public void returnBrokenResource(ShardedJedis shardedJedis) {
		// 释放redis对象
		shardedJedisPool.returnBrokenResource(shardedJedis);
	}
}
