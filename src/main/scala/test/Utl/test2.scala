package test.Utl

import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object test2 {
  def main(args: Array[String]): Unit = {
    var list: List[String] = List()

    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
    val sQLContext = new SQLContext(sc)

    //获取数据
    val jsonRDD: RDD[String] = sc.textFile("E:\\json.txt")
    //jsonRDD.foreach(println)
    //将RDD转为string类型数组
    val jsonstr: mutable.Buffer[String] = jsonRDD.collect().toBuffer
    //遍历数组，解析json串
    for (i <- 0 until jsonstr.length) {
      //得到每一个数组里的元素，json格式转为string
      val str: String = jsonstr(i).toString
      val jsonparse: JSONObject = JSON.parseObject(str)

      val status = jsonparse.getIntValue("status")
      if (status == 0) return ""

      val regeocodeJson = jsonparse.getJSONObject("regeocode")
      if (regeocodeJson == null || regeocodeJson.keySet().isEmpty) return ""

      val poisArray = regeocodeJson.getJSONArray("pois")
      if (poisArray == null || poisArray.isEmpty) return null

      // 创建集合 保存数据
      val buffer = collection.mutable.ListBuffer[String]()
      // 循环输出
      for (item <- poisArray.toArray) {
        if (item.isInstanceOf[JSONObject]) {
          val json = item.asInstanceOf[JSONObject]
          buffer.append(json.getString("type"))
        }
      }

      list:+=buffer.mkString(";")
    }

    val res2: List[(String, Int)] = list.flatMap(x => x.split(";"))
      .map(x => ("type：" + x, 1))
      .groupBy(x => x._1)
      .mapValues(x => x.size).toList.sortBy(x => x._2)

    res2.foreach(x => println(x))
  }
}
