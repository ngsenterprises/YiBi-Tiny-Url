package com.ngs.yibi.util

import scala.collection.mutable.ListBuffer

object Util {

  val alphanum = "abcdefghijkmnopqrstuvwxyz23456789".toList
  val base = alphanum.length

  def urlEncode( n : Int ): String = {
    require( 0 < n )

    if ( n == 0 ) alphanum.head.toString else {
      val buf = ListBuffer.empty[Char]
      var k = n
      while ( 0 < k ) {
        alphanum( k % base ) +=: buf
        k = k / base
      }
      buf.mkString
    }
  }

  def urlDecode( s: String ): Long = s.foldLeft( 0 ){ (ac, c) => ac*base +alphanum.indexOf( c ) }

}
