package com.utl

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
  * Redis连接
  */
object JedisConnectionPool {
  val config = new JedisPoolConfig()
  //总的最大连接数
  config.setMaxTotal(20)
  //
  config.setMaxIdle(10)
  //配置jedis
  private val pool = new JedisPool(config, "192.168.182.4", 6379, 10000)

  //获取链接
  def getConnection(): Jedis = {
    //获取资源
    pool.getResource

  }
}
