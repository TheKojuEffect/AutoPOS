package com.kapilkoju.autopos.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration class DateTimeFormatConfiguration extends WebMvcConfigurerAdapter {

  override def addFormatters(registry: FormatterRegistry) {
    val registrar: DateTimeFormatterRegistrar = new DateTimeFormatterRegistrar
    registrar.setUseIsoFormat(true)
    registrar.registerFormatters(registry)
  }

}