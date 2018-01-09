package com.kapilkoju.autopos.config

import io.github.jhipster.async.ExceptionHandlingAsyncTaskExecutor
import io.github.jhipster.config.JHipsterProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
@EnableScheduling
class AsyncConfiguration(val jHipsterProperties: JHipsterProperties) : AsyncConfigurer {

    final private val log: Logger = LoggerFactory.getLogger(AsyncConfiguration::class.java)

    @Bean(name = ["taskExecutor"])
    override fun getAsyncExecutor(): Executor {
        log.debug("Creating Async Task Executor")
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = jHipsterProperties.async.corePoolSize
        executor.maxPoolSize = jHipsterProperties.async.maxPoolSize
        executor.setQueueCapacity(jHipsterProperties.async.queueCapacity)
        executor.threadNamePrefix = "autopos-Executor-"
        return ExceptionHandlingAsyncTaskExecutor(executor)
    }

    override fun getAsyncUncaughtExceptionHandler() = SimpleAsyncUncaughtExceptionHandler()
}