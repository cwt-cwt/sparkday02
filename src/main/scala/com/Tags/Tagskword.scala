package com.Tags

import com.utl.Tag
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.Row

/**
  * 关键字标签
  */
object Tagskword extends Tag{
  override def makeTags(args: Any*): List[(String, Int)]={
    var list=List[(String, Int)]()
    val row=args(0).asInstanceOf[Row]
    //停用词库的内容传过来
    val stopWords=args(1).asInstanceOf[Broadcast[ collection. Map[ String, Int]]]
    //取值判断
    row.getAs[String]("keywords").split("\\|")
    .filter(word=>word.length >= 3 && word.length <= 8 && !stopWords.value.contains(word))
    //查看word是不是在停用词库，不在就是false，双重否定就是true
    //存入list
    .foreach(word=>list:+=("K"+word,1))
    list
  }
}
