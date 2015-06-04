package com.ailink.framework.redis.cluster;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.ShardedJedis;

import com.ailink.framework.redis.cluster.dao.RedisDataSource;

/**
 * 
 * @author jlon
 *
 */
public class RedisTemplate {
	
	
	@Autowired
	private RedisDataSource redisDataSource;
	/**
	 * <p>
	 * 通过连接池对象 构建一个连接池
	 * </p>
	 * 
	 * @param pool
	 *  连接池对象
	 */
	public ShardedJedis getshardedJedis() {
		  ShardedJedis shardedJedis= redisDataSource.getRedisClient();
		return shardedJedis;
	}

	/**
	 * <p>
	 * 通过key获取储存在redis中的value
	 * </p>
	 * <p>
	 * 并释放连接
	 * </p>
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 */
	public String get(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		String value = null;
		try {
			value = getshardedJedis().get(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return value;
	}

	/**
	 * <p>
	 * 向redis存入key和value,并释放连接资源
	 * </p>
	 * <p>
	 * 如果key已经存在 则覆盖
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 成功 返回OK 失败返回 0
	 */
	public String set(String key, String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		try {
			
			return shardedJedis.set(key, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
			return "0";
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
	}

	/**
	 * <p>
	 * 删除指定的key,也可以传入一个包含key的数组
	 * </p>
	 * 
	 * @param keys
	 *            一个key 也可以使 string 数组
	 * @return 返回删除成功的个数
	 */
	public Long del(String... keys) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res =0L;
		try {
			for (int i = 0; i < keys.length; i++) {
				res=res+shardedJedis.del(keys[i]);
			}
		return res;
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
			return 0L;
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
	}

	/**
	 * <p>
	 * 通过key向指定的value值追加值
	 * </p>
	 * 
	 * @param key
	 * @param str
	 * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度 异常返回0L
	 */
	public Long append(String key, String str) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.append(key, str);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
			return 0L;
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 判断key是否存在
	 * </p>
	 * 
	 * @param key
	 * @return true OR false
	 */
	public Boolean exists(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		try {
			
			return shardedJedis.exists(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
			return false;
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
	}

	/**
	 * <p>
	 * 设置key value,如果key已经存在则返回0,nx==> not exist
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 成功返回1 如果存在 和 发生异常 返回 0
	 */
	public Long setnx(String key, String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		try {
			
			return shardedJedis.setnx(key, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
			return 0L;
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
	}

	/**
	 * <p>
	 * 设置key value并制定这个键值的有效期
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            单位:秒
	 * @return 成功返回OK 失败和异常返回null
	 */
	public String setex(String key, String value, int seconds) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.setex(key, seconds, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key 和offset 从指定的位置开始将原先value替换
	 * </p>
	 * <p>
	 * 下标从0开始,offset表示从offset下标开始替换
	 * </p>
	 * <p>
	 * 如果替换的字符串长度过小则会这样
	 * </p>
	 * <p>
	 * example:
	 * </p>
	 * <p>
	 * value : bigsea@zto.cn
	 * </p>
	 * <p>
	 * str : abc
	 * </p>
	 * <P>
	 * 从下标7开始替换 则结果为
	 * </p>
	 * <p>
	 * RES : bigsea.abc.cn
	 * </p>
	 * 
	 * @param key
	 * @param str
	 * @param offset
	 *            下标位置
	 * @return 返回替换后 value 的长度
	 */
	public Long setrange(String key, String str, int offset) {
		ShardedJedis shardedJedis=getshardedJedis();
		try {
			
			return shardedJedis.setrange(key, offset, str);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
			return 0L;
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
	}


	/**
	 * <p>
	 * 设置key的值,并返回一个旧值
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return 旧值 如果key不存在 则返回null
	 */
	public String getset(String key, String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.getSet(key, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过下标 和key 获取指定下标位置的 value
	 * </p>
	 * 
	 * @param key
	 * @param startOffset
	 *            开始位置 从0 开始 负数表示从右边开始截取
	 * @param endOffset
	 * @return 如果没有返回null
	 */
	public String getrange(String key, int startOffset, int endOffset) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
	 * </p>
	 * 
	 * @param key
	 * @return 加值后的结果
	 */
	public Long incr(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.incr(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key给指定的value加值,如果key不存在,则这是value为该值
	 * </p>
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long incrBy(String key, Long integer) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.incrBy(key, integer);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 对key的值做减减操作,如果key不存在,则设置key为-1
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.decr(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 减去指定的值
	 * </p>
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long decrBy(String key, Long integer) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.decrBy(key, integer);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取value值的长度
	 * </p>
	 * 
	 * @param key
	 * @return 失败返回null
	 */
	public Long serlen(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.strlen(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key给field设置指定的值,如果key不存在,则先创建
	 * </p>
	 * 
	 * @param key
	 * @param field
	 *            字段
	 * @param value
	 * @return 如果存在返回0 异常返回null
	 */
	public Long hset(String key, String field, String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.hset(key, field, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hsetnx(String key, String field, String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.hsetnx(key, field, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key同时设置 hash的多个field
	 * </p>
	 * 
	 * @param key
	 * @param hash
	 * @return 返回OK 异常返回null
	 * 
	 *         Map<String, String> hash = new HashMap<String, String>();
	 *         pairs.put("name", "Akshi"); pairs.put("age", "2");
	 *         pairs.put("sex", "Female"); shardedJedis.hmset("key", hash);
	 */
	public String hmset(String key, Map<String, String> hash) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.hmset(key, hash);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key 和 field 获取指定的 value
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @return 没有返回null
	 */
	public String hget(String key, String field) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.hget(key, field);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
	 * </p>
	 * 
	 * @param key
	 * @param fields
	 *            可以使 一个String 也可以是 String数组
	 * @return
	 */
	public List<String> hmget(String key, String... fields) {
		ShardedJedis shardedJedis=getshardedJedis();
		List<String> res = null;
		try {
			
			res = shardedJedis.hmget(key, fields);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key给指定的field的value加上给定的值
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hincrby(String key, String field, Long value) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.hincrBy(key, field, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key和field判断是否有指定的value存在
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hexists(String key, String field) {
		ShardedJedis shardedJedis=getshardedJedis();
		Boolean res = false;
		try {
			
			res = shardedJedis.hexists(key, field);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回field的数量
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long hlen(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.hlen(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;

	}

	/**
	 * <p>
	 * 通过key 删除指定的 field
	 * </p>
	 * 
	 * @param key
	 * @param fields
	 *            可以是 一个 field 也可以是 一个数组
	 * @return
	 */
	public Long hdel(String key, String... fields) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.hdel(key, fields);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回所有的field
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Set<String> res = null;
		try {
			
			res = shardedJedis.hkeys(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回所有和key有关的value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public List<String> hvals(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		List<String> res = null;
		try {
			
			res = shardedJedis.hvals(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取所有的field和value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetall(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Map<String, String> res = null;
		try {
			
			res = shardedJedis.hgetAll(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向list头部添加字符串
	 * </p>
	 * 
	 * @param key
	 * @param strs
	 *            可以使一个string 也可以使string数组
	 * @return 返回list的value个数
	 */
	public Long lpush(String key, String... strs) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.lpush(key, strs);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向list尾部添加字符串
	 * </p>
	 * 
	 * @param key
	 * @param strs
	 *            可以使一个string 也可以使string数组
	 * @return 返回list的value个数
	 */
	public Long rpush(String key, String... strs) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.rpush(key, strs);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key在list指定的位置之前或者之后 添加字符串元素
	 * </p>
	 * 
	 * @param key
	 * @param where
	 *            LIST_POSITION枚举类型
	 * @param pivot
	 *            list里面的value
	 * @param value
	 *            添加的value
	 * @return
	 */
	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.linsert(key, where, pivot, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key设置list指定下标位置的value
	 * </p>
	 * <p>
	 * 如果下标超过list里面value的个数则报错
	 * </p>
	 * 
	 * @param key
	 * @param index
	 *            从0开始
	 * @param value
	 * @return 成功返回OK
	 */
	public String lset(String key, Long index, String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.lset(key, index, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key从对应的list中删除指定的count个 和 value相同的元素
	 * </p>
	 * 
	 * @param key
	 * @param count
	 *            当count为0时删除全部
	 * @param value
	 * @return 返回被删除的个数
	 */
	public Long lrem(String key, long count, String value) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.lrem(key, count, value);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key保留list中从strat下标开始到end下标结束的value值
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return 成功返回OK
	 */
	public String ltrim(String key, long start, long end) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.ltrim(key, start, end);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key从list的头部删除一个value,并返回该value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.lpop(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key从list尾部删除一个value,并返回该元素
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.rpop(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}


	/**
	 * <p>
	 * 通过key获取list中指定下标位置的value
	 * </p>
	 * 
	 * @param key
	 * @param index
	 * @return 如果没有返回null
	 */
	public String lindex(String key, long index) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.lindex(key, index);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回list的长度
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long llen(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.llen(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取list指定下标位置的value
	 * </p>
	 * <p>
	 * 如果start 为 0 end 为 -1 则返回全部的list中的value
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		ShardedJedis shardedJedis=getshardedJedis();
		List<String> res = null;
		try {
			
			res = shardedJedis.lrange(key, start, end);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向指定的set中添加value
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            可以是一个String 也可以是一个String数组
	 * @return 添加成功的个数
	 */
	public Long sadd(String key, String... members) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.sadd(key, members);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key删除set中对应的value值
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            可以是一个String 也可以是一个String数组
	 * @return 删除的个数
	 */
	public Long srem(String key, String... members) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.srem(key, members);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key随机删除一个set中的value并返回该值
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String spop(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.spop(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中value的个数
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long scard(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			res = shardedJedis.scard(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key判断value是否是set中的元素
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Boolean sismember(String key, String member) {
		ShardedJedis shardedJedis=getshardedJedis();
		Boolean res = null;
		try {
			
			res = shardedJedis.sismember(key, member);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中随机的value,不删除元素
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String srandmember(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			
			res = shardedJedis.srandmember(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取set中所有的value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Set<String> res = null;
		try {
			
			res = shardedJedis.smembers(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key向zset中添加value,score,其中score就是用来排序的
	 * </p>
	 * <p>
	 * 如果该value已经存在则根据score更新元素
	 * </p>
	 * 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	/*
	 * public Long zadd(String key,Map<Double, String> scoreMembers){ shardedJedis
	 * shardedJedis= null; Long res = null; try {  res =
	 * shardedJedis.sadd(key, scoreMembers); } catch (Exception e) {
	 * redisDataSource.returnBrokenResource(shardedJedis); e.printStackTrace(); } finally {
	 * redisDataSource.returnResource(shardedJedis); } return res; }
	 */
	/**
	 * <p>
	 * 通过key向zset中添加value,score,其中score就是用来排序的
	 * </p>
	 * <p>
	 * 如果该value已经存在则根据score更新元素
	 * </p>
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key, double score, String member) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zadd(key, score, member);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key删除在zset中指定的value
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public Long zrem(String key, String... members) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zrem(key, members);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key增加该zset中value的score的值
	 * </p>
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(String key, double score, String member) {
		ShardedJedis shardedJedis=getshardedJedis();
		Double res = null;
		try {
			
			res = shardedJedis.zincrby(key, score, member);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回zset中value的排名
	 * </p>
	 * <p>
	 * 下标从小到大排序
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zrank(key, member);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回zset中value的排名
	 * </p>
	 * <p>
	 * 下标从大到小排序
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zrevrank(key, member);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * 返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素
	 * <p>
	 * 通过key将获取score从start到end中zset的value
	 * </p>
	 * <p>
	 * socre从大到小排序
	 * </p>
	 * <p>
	 * 当start为0 end为-1时返回全部
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end) {
		ShardedJedis shardedJedis=getshardedJedis();
		Set<String> res = null;
		try {
			
			res = shardedJedis.zrevrange(key, start, end);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * 返回名称为key的zset中score >= min且score <= max的所有元素
	 * <p>
	 * 通过key返回指定score内zset中的value
	 * </p>
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zrangebyscore(String key, String max, String min) {
		ShardedJedis shardedJedis=getshardedJedis();
		Set<String> res = null;
		try {
			
			res = shardedJedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回指定score内zset中的value
	 * </p>
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double max, double min) {
		ShardedJedis shardedJedis=getshardedJedis();
		Set<String> res = null;
		try {
			
			res = shardedJedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回指定score内zset中的value的数量
	 * </p>
	 * 返回名称为key的zset中score >= min且score <= max的所有元素
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public long zrangeByScore(String key, Long max, Long min) {
		ShardedJedis shardedJedis=getshardedJedis();
		Set<String> res = null;
		try {
			
			res = shardedJedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res.size();
	}

	/**
	 * 
	 * /** 获取指定权重区间内集合的数量 返回指定区间内zset中value的数量
	 * 
	 * @param String
	 *            key
	 * @param double min 最小排序位置
	 * @param double max 最大排序位置
	 * @param key
	 * @return
	 */
	public Long zcount(String key, String min, String max) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zcount(key, min, max);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key返回zset中的value个数
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long zcard(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zcard(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key获取zset中value的score值
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) {
		ShardedJedis shardedJedis=getshardedJedis();
		Double res = null;
		try {
			
			res = shardedJedis.zscore(key, member);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key删除给定区间内的元素
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByRank(String key, long start, long end) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 通过key删除指定score内的元素
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByScore(String key, double start, double end) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}
	

	/**
	 * <p>
	 * 通过key判断值得类型
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String type(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		String res = null;
		try {
			res = shardedJedis.type(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}


	/**
	 * <p>
	 * expire：设定一个key的活动时间（s）秒
	 * </p>
	 * 
	 * @param key
	 * @return 整数，如下的整数结果: 如果设置了过期时间 1 如果没有设置过期时间，或者不能设置过期时间 0
	 */
	public Long expire(String key, int seconds) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}

	/**
	 * <p>
	 * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
	 * </p>
	 * 
	 * @return 返回值
	 * 
	 *         当 key 不存在时，返回 -2 。
	 * 
	 *         当 key 存在但没有设置剩余生存时间时，返回 -1 。
	 * 
	 *         否则，以秒为单位，返回 key 的剩余生存时间。
	 */
	public Long ttl(String key) {
		ShardedJedis shardedJedis=getshardedJedis();
		Long res = null;
		try {
			
			res = shardedJedis.ttl(key);
		} catch (Exception e) {
			redisDataSource.returnBrokenResource(shardedJedis);
			e.printStackTrace();
		} finally {
			redisDataSource.returnResource(shardedJedis);
		}
		return res;
	}
}
