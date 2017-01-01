package com.kapilkoju.autopos.config

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaModule}
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class JacksonConfiguration {

  @Bean
  def hibernate5Module: Module = new Hibernate5Module()

  @Bean
  def scalaModule: Module = new DefaultScalaModule()

}
