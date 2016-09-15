package io.koju.autopos.config

import java.util.concurrent.Executor

import io.koju.autopos.async.ExceptionHandlingAsyncTaskExecutor
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.aop.interceptor.{AsyncUncaughtExceptionHandler, SimpleAsyncUncaughtExceptionHandler}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.scheduling.annotation.{AsyncConfigurer, EnableAsync, EnableScheduling}
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableAsync
@EnableScheduling
class AsyncConfiguration(private val jHipsterProperties: AutoposProperties)
  extends AsyncConfigurer {

  final private val log: Logger = LoggerFactory.getLogger(classOf[AsyncConfiguration])

  @Bean(name = Array("taskExecutor"))
  override def getAsyncExecutor: Executor = {
    log.debug("Creating Async Task Executor")
    val executor: ThreadPoolTaskExecutor = new ThreadPoolTaskExecutor
    executor.setCorePoolSize(jHipsterProperties.getAsync.getCorePoolSize)
    executor.setMaxPoolSize(jHipsterProperties.getAsync.getMaxPoolSize)
    executor.setQueueCapacity(jHipsterProperties.getAsync.getQueueCapacity)
    executor.setThreadNamePrefix("autopos-Executor-")
    new ExceptionHandlingAsyncTaskExecutor(executor)
  }

  override def getAsyncUncaughtExceptionHandler: AsyncUncaughtExceptionHandler =
    new SimpleAsyncUncaughtExceptionHandler
}