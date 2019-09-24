package com.location

import com.utl.RptUtils
import org.apache.spark.sql.{DataFrame, SparkSession}

/*
*处理地域指标
*/
object LocationRpt {
  def main(args: Array[String]): Unit = {
    //System.setProperty("hadoop.home.dir","D:\\Huohu\\下载\\hadoop-common-2.2.0-bin-master")
    if (args.length != 2) {
      println("输入目录不正确")
      sys.exit()
    }
    val Array(inputPath, outputPath) = args
    val spark = SparkSession
      .builder()
      .appName("ct")
      .master("local")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()
    //获取数据,类型为dataframe
    val df:DataFrame = spark.read.parquet(inputPath)
    //用转换算子进行处理
    //根据指标的字段获取当前数据结果
    //REQUESTMODE PROCESSNODE ISEFFECTIVE ISBILLING ISBID ISWIN ADORDEERID 
    //getAs可以自己设定类型,括号中是key

    //转为rdd计算
    df.rdd.map(row=>{
    val requestmode = row.getAs[Int]("requestmode")
    val processnode = row.getAs[Int]("processnode")
    val iseffective = row.getAs[Int]("iseffective")
    val isbilling = row.getAs[Int]("isbilling")
    val isbid = row.getAs[Int]("isbid")
    val iswin = row.getAs[Int]("iswin")
    val adordeerid = row.getAs[Int]("adorderid")
    val winprice = row.getAs[Double]("winprice")
    val adpayment = row.getAs[Double]("adpayment")
    //处理请求数
    val rptList = RptUtils.ReqPt(requestmode, processnode)
    //处理展示点击
    val clickList = RptUtils.clickPt(requestmode, iseffective)
    //处理广告
    val adList = RptUtils.adPt(iseffective, isbilling, isbid, iswin, adordeerid, winprice, adpayment)
    //所有指标
    val allList: List[Double] = rptList ++ clickList ++ adList
    ((row.getAs[String]("provincename"), row.getAs[String]("cityname")), allList)
  })
  .reduceByKey((list1, list2) => {
    //list1(1,1,1,1).zip(list2(1,1,1,1))=list((1,1),(1,1),(1,1),(1,1))   
    list1.zip(list2).map(t => t._1 + t._2)
  })
    .map(t=> t._1+"," +t._2.mkString(","))
  .saveAsTextFile(outputPath)
}

}
