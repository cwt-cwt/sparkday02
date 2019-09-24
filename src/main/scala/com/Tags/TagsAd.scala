package com.Tags

import com.utl.Tag

import org.apache.spark.sql.Row

/**
  * 广告标签
  */
object TagsAd extends Tag {
  override def makeTags(args: Any*): List[(String, Int)] = {
    var list = List[(String, Int)]()
    //获取数据类型，引用类型的地址只有一个
    val row = args(0).asInstanceOf[Row]
    //获取广告位类型
    val adType = row.getAs[Int]("adspacetype")
    //模式匹配，上标签
    //广告位类型标签
    adType match {
      case v if v > 9 => list :+= ("LC" + v, 1)
      case v if v > 0 && v <= 9 => list :+= ("LC0" + v, 1)
    }
    //获取广告名称
    val adName = row.getAs[String]("adspacetypename")
    //判断
    if (adName != null) {
      list :+= ("LN" + adName, 1)
    }
    list
  }
}
