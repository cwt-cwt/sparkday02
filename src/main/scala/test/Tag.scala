package test

trait Tag {
  //给标签造型
  def makeTags(args:Any*):List[(String,Int)]
}
