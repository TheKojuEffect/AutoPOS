package com.kapilkoju.autopos.config


import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class JacksonScalaConfiguration {

  @Bean
  def scalaModule: Module= new DefaultScalaModule()
}
