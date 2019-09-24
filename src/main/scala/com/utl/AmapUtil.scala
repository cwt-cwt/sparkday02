package com.utl

import com.alibaba.fastjson.{JSON, JSONObject}

/**
*从高德地图获取商图信息
*/
object Amaputil{
  /**
    *解析经纬度
    *@param Long
    *@param Lat
    *Greturn
    */
  def getBusinessFromAmap(long:Double,lat:Double):String={
    //https://restapi.amap.com/v3/geocode/regeo?
    //location=116.310003,39.9919578key=59283c76b065e4ee401c2b8a4fde8f8bsextensions=all
    val location=long+","+lat
    //获取url
    val url= "https://restapi.amap.com/v3/geocode/regeo?location="+location+"key=59283c76b065e4ee401c2b8a4fde8f8bsextensions=all"
    //调用http接口发送请求,返回一个Jason串
    val jsonstr = HttpUtil.get(url)
    //解析jason
    val jSONObject1 =JSON.parseObject(jsonstr)
    //判断当前状态是否为1，1为发送成功
    val status = jSONObject1.getIntValue("status")
    if(status==0) return ""
    //获取下一层，如果不为空,里面的值还是一个json串，所以不用getString
    val jS0NObject =jSONObject1.getJSONObject("regeocode")
    //查看内容是否为空
    if(jS0NObject == null) return ""
    //解析有内容的字符串
    val jsonObject2 = jSONObject1.getJSONObject("addressComponent")
    //查看内容是否为空
    if(jsonObject2 == null) return ""
    //解析第三层，里面是数组，所以不用getjsonobject()
    val jSONArray = jsonObject2.getJSONArray("businessAreas")
    //判断是否为空
    if(jSONArray == null) return ""

    //定义集合
    val result = collection.mutable.ListBuffer[String]()
    //要取值，循环数组,数组里每个元素都是一个json串，json里是k,V形式的数据
    //数组中的内容放在集合中
    for(item<-jSONArray.toArray()){
      //isInstanceOf 只能判断出对象是否为指定类以及其子类的对象，
      // 而不能精确的判断出，对象就是指定类的对象

      //如果要求精确地判断出对象就是指定类的对象，
      // 那么就只能使用 getClass 和 classOf

      // p.getClass 可以精确地获取对象的类

      //classOf[XX]可以精确的获取类，
      // 然后使用 == 操作符即可判断

      //此处的isInstanceOf表示，判断是否能转换成JSONObject
      if(item.isInstanceOf[JSONObject]){
        //asInstanceOf表示，将item转换为JSONObject格式的
        val json = item.asInstanceOf[JSONObject]
        val name = json.getString("name")
        result.append(name)
      }
    }
    //返回商圈名字
    result.mkString(",")
  }
}
