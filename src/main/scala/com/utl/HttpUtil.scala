package com.utl

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

/*
http请求协议,get请求
*/
object HttpUtil{
  /*
  GET请求
  输入 url
  输出 jason
  */
  def get(url:String):String={
    //创建一个HttpClient对象
    val client = HttpClients.createDefault()
    //创建一个get对象
    val httpGet = new HttpGet(url)
    //获取发送请求,使用HttpClient来执行get请求，得到对方的response(响应)
    //.execute表示执行
    val httpResponse = client.execute(httpGet)
    //处理返回响应结果
    //返回一个String字符串，结果会乱码才写这句
    EntityUtils.toString(httpResponse.getEntity,"UTF-8")
  }
}
