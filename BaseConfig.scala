package com.ngs.yibi.baseconfig

import com.typesafe.config.{Config, ConfigFactory}

object BaseConfig {
  def conf = ConfigFactory.load()
}
