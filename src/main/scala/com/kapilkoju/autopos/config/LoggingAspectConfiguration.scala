package com.kapilkoju.autopos.config

import com.kapilkoju.autopos.aop.logging.LoggingAspect
import org.springframework.context.annotation.{Bean, Configuration, EnableAspectJAutoProxy, Profile}

@Configuration
@EnableAspectJAutoProxy
class LoggingAspectConfiguration {

  @Bean
  @Profile(Array("dev"))
  def loggingAspect: LoggingAspect = new LoggingAspect

}
