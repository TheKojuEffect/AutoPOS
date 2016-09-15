package io.koju.autopos.config

import io.koju.autopos.aop.logging.LoggingAspect
import org.springframework.context.annotation.{Bean, Configuration, EnableAspectJAutoProxy, Profile}

@Configuration
@EnableAspectJAutoProxy
class LoggingAspectConfiguration {

  @Bean
  @Profile(Array(Constants.SPRING_PROFILE_DEVELOPMENT))
  def loggingAspect: LoggingAspect = new LoggingAspect

}