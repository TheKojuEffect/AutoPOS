package com.kapilkoju.autopos.config

import com.kapilkoju.autopos.aop.logging.LoggingAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment

@Configuration
@EnableAspectJAutoProxy
class LoggingAspectConfiguration {

    @Bean
    @Profile(value = [Profiles.DEVELOPMENT])
    fun loggingAspect(env: Environment): LoggingAspect {
        return LoggingAspect(env)
    }
}