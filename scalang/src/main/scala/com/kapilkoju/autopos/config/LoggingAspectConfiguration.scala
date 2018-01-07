package com.kapilkoju.autopos.config

import com.kapilkoju.autopos.aop.logging.LoggingAspect
import io.github.jhipster.config.JHipsterConstants
import org.springframework.context.annotation._
import org.springframework.core.env.Environment

@Configuration
@EnableAspectJAutoProxy class LoggingAspectConfiguration {

  @Bean
  @Profile(Array(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))
  def loggingAspect(env: Environment): LoggingAspect = {
    new LoggingAspect(env)
  }
}