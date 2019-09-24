//package test.Location
//
//import org.apache.spark.sql.{Dataset, SparkSession}
//
//
///**
//  * 主类
//  */
//
//object Test {
//
//  def main(args: Array[String]): Unit = {
//    var list: List[List[String]] = List()
//    if (args != 1) {
//      println("目录不正确")
//      sys.exit()
//    }
//    //创建程序入口
//    val spark = SparkSession.builder().appName("test").master("local").getOrCreate()
//    //主方法输入是数组，所以要把路径存入数组
//    val Array(inputpath) = args
//    //获取数据，,返回一个dataset
//    val df: Dataset[String] = spark.read.textFile(inputpath)
//
//    //处理需求
//    df.map(line => {
//      //获取处理后的数据
//      val data = GetData(line)
//      //处理1.按照pois，分类businessarea，并统计每个businessarea的总数。
//
//    })
//
//    //先map，再压平
//    val res1: List[(String, Int)] = list.flatMap(x => x)
//      //过滤里面没有内容的，在获取数据成元组
//      .filter(x => x != "[]").map(x => (x, 1))
//      //按照数据分组
//      .groupBy(x => x._1)
//      //获取相应key的个数，根据key排序
//      .mapValues(x => x.size).toList.sortBy(x => x._2)
//
//    //将结果输出
//    res1.foreach(println)
//
//  }
//
//}
//
