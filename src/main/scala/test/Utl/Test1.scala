//package test.Utl
//
//import com.alibaba.fastjson.{JSON, JSONObject}
//
//
//object Test1 {
//
//  def main(args: Array[String]): Unit = {
//    var list: List[List[String]] = List()
//
//    val poisArray: Array[String] = poisArray
//    //判断是否为空
//    if (poisArray == null) return null
//
//    // 创建集合 保存数据
//    val buffer = collection.mutable.ListBuffer[String]()
//    for (i <- 0 until poisArray.length) {
//      //得到数组里的json串，json格式转为string
//      val str: String = poisArray(i).toString
//      //解析json
//      val jsonparse: JSONObject = JSON.parseObject(str)
//      //查看json解析后有没有获取到数据
//      //判断当前状态是否为1，1为发送成功
//      val status = jsonparse.getIntValue("status")
//      //如果没有数据就返回空，结束本次循环
//      if (status == 0) return null
//      //有数据，获取下一层，如果不为空,里面的值还是一个json串，所以不用getString
//      val regeocodeJson = jsonparse.getJSONObject("businessarea")
//
//      //先map，再压平
//      val res1: List[(String, Int)] = list.flatMap(x => x)
//        //过滤里面没有内容的，在获取数据成元组
//        .filter(x => x != "[]").map(x => (x, 1))
//        //按照数据分组
//        .groupBy(x => x._1)
//        //获取相应key的个数，根据key排序
//        .mapValues(x => x.size).toList.sortBy(x => x._2)
//
//      //将结果输出
//      res1.foreach(println)
//
//    }
//
//  }
