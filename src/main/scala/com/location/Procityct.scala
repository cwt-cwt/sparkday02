package com.location
/*
把需要的数据存储到mysql中
统计省市指标
 */
import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{SaveMode, SparkSession}

object Procityct {
  def main(args:Array[String]):Unit={
    if(args.length!=1) {
      println("输入目录不正确")
      sys.exit()
    }
      val Array(inputPath)=args
      val spark= SparkSession
        .builder()
      .appName("ct")
      .master("local")
        //序列化方式
      .config("spark.serializer","org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()
      //获取数据
      val df=spark.read.parquet(inputPath)
      //注册临时视图
      df.createTempView("log")
      //处理sql语句
      val df2=spark
        .sql("select provincename,cityname, count(*) ct from log group by provincename, cityname")
      //输出存成jason格式
      //df2.write.partitionBy("provincename","cityname").json("")

      //存mysql

      //通过config配置文件依赖加载相关配置信息
      //加载 application.conf
      val load=ConfigFactory. load()

      //创建Properties对象
      val prop=new Properties()
      prop.setProperty("user",load.getString("jdbc.user"))
      prop.setProperty("password",load.getString("jdbc.password"))
      //存储
      //通过追加的方式，加载jdbc的地址和名称
      df2.write.mode(SaveMode.Append).jdbc(
        load.getString("jdbc.url"),load.getString("jdbc.tableName"),prop)
      //关闭
      spark.stop()
    }
}
