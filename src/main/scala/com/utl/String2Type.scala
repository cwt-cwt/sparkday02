package com.utl

object String2Type {
  def toInt(str: String): Int = {
    try {
      str.toInt
    } catch {
      case _: Exception => 0
    }
  }

  def toDouble(str: String): Double = {
    try {
      str.toDouble
    } catch {
      case _: Exception => 0.0
    }
  }
}
