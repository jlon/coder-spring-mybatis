package com.ailink.framework.redis.cluster.dao;

import redis.clients.jedis.ShardedJedis;

/**
 * 
 * @author jlon
 *
 */
public interface RedisDataSource {
	/**
	 * 取得redis的客户端，可以执行命令了。 ShardedJedis是基于一致性哈希算法实现的分布式Redis集群客户端
	 * 
	 * @return
	 */
	ShardedJedis getRedisClient();

	/**
	 * //释放redis对象
	 * 
	 * @param shardedJedis
	 * @param broken
	 */
	void returnBrokenResource(ShardedJedis shardedJedis);

	/**
	 * 将资源返还给pool
	 * 
	 * @param shardedJedis
	 */
	void returnResource(ShardedJedis shardedJedis);
}
