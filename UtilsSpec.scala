package com.ngs.yibi.utiltest

import org.scalatest.FlatSpec
import com.ngs.yibi.util._

class UtilSpec extends FlatSpec {

  "For a shortened url, urlDecode( urlEncode )" should "return the url." in {

    val n = scala.util.Random.nextInt( Int.MaxValue )
    val res = Util.urlDecode( Util.urlEncode( n ) )

    assert( res == n )
  }

}
