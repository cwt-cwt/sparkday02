package com.utl

import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.SparkSession

/**
  * 测试工具类
  */
object Test {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("test").getOrCreate()

    val arr = Array("https://restapi.amap.com/v3/geocode/regeo?location=116.310003,39.991957&key=59283c76b065e4ee401c2b8a4fde8f8b&extensions=all")
    val rdd = spark.sparkContext.makeRDD(arr)
    rdd.map(t => {
      HttpUtil.get(t)
    }).foreach(println)


  }
}
