//package test.Utl
//
//import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
//
//
//object GetData {
//  def main(args: Array[String]): JSONArray = {
//
//    //获取数据,传过来就是数组，可以直接使用
//    //{k:v,regeocode:{roads:[{,,"pois":[{businessarea:"v"}]},{},{}]}}
//    val jsonstr = args
//    //遍历数组，解析json串
//    for (i <- 0 until jsonstr.length) {
//      //得到数组里的json串，json格式转为string
//      val str: String = jsonstr(i).toString
//      //解析json
//      val jsonparse: JSONObject = JSON.parseObject(str)
//      //查看json解析后有没有获取到数据
//      //判断当前状态是否为1，1为发送成功
//      val status = jsonparse.getIntValue("status")
//      //如果没有数据就返回空，结束本次循环
//      if (status == 0) return null
//      //有数据，获取下一层，如果不为空,里面的值还是一个json串，所以不用getString
//      val regeocodeJson = jsonparse.getJSONObject("regeocode")
//      //判断是否有内容
//      if (regeocodeJson == null)
//        return null
//      //有内容解析查找pois[{}]
//      val poisArray: JSONArray = regeocodeJson.getJSONArray("pois")
//      //判断是否为空
//      if (poisArray == null) return null
//
//      poisArray
//    }
//  }
//}
