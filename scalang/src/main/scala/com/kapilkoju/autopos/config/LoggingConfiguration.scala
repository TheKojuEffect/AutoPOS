package com.kapilkoju.autopos.config

import ch.qos.logback.classic.spi.LoggerContextListener
import ch.qos.logback.classic.{AsyncAppender, Level, LoggerContext}
import ch.qos.logback.core.spi.ContextAwareBase
import io.github.jhipster.config.JHipsterProperties
import net.logstash.logback.appender.LogstashSocketAppender
import net.logstash.logback.stacktrace.ShortenedThrowableConverter
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class LoggingConfiguration(val jHipsterProperties: JHipsterProperties,
                           @Value("${server.port}") val appName: String,
                           @Value("${server.port}") val serverPort: String) {

  private val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]

  if (jHipsterProperties.getLogging.getLogstash.isEnabled) {
    addLogstashAppender(context)
    // Add context listener
    val loggerContextListener = new LogbackLoggerContextListener
    loggerContextListener.setContext(context)
    context.addListener(loggerContextListener)
  }

  final private val log: Logger = LoggerFactory.getLogger(classOf[LoggingConfiguration])

  def addLogstashAppender(context: LoggerContext) {
    log.info("Initializing Logstash logging")
    val logstashAppender: LogstashSocketAppender = new LogstashSocketAppender
    logstashAppender.setName("LOGSTASH")
    logstashAppender.setContext(context)
    val customFields: String = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort + "\"}"
    // Set the Logstash appender config from JHipster properties
    logstashAppender.setSyslogHost(jHipsterProperties.getLogging.getLogstash.getHost)
    logstashAppender.setPort(jHipsterProperties.getLogging.getLogstash.getPort)
    logstashAppender.setCustomFields(customFields)
    // Limit the maximum length of the forwarded stacktrace so that it won't exceed the 8KB UDP limit of logstash
    val throwableConverter: ShortenedThrowableConverter = new ShortenedThrowableConverter
    throwableConverter.setMaxLength(7500)
    throwableConverter.setRootCauseFirst(true)
    logstashAppender.setThrowableConverter(throwableConverter)
    logstashAppender.start()
    // Wrap the appender in an Async appender for performance
    val asyncLogstashAppender: AsyncAppender = new AsyncAppender
    asyncLogstashAppender.setContext(context)
    asyncLogstashAppender.setName("ASYNC_LOGSTASH")
    asyncLogstashAppender.setQueueSize(jHipsterProperties.getLogging.getLogstash.getQueueSize)
    asyncLogstashAppender.addAppender(logstashAppender)
    asyncLogstashAppender.start()
    context.getLogger("ROOT").addAppender(asyncLogstashAppender)
  }

  /**
    * Logback configuration is achieved by configuration file and API.
    * When configuration file change is detected, the configuration is reset.
    * This listener ensures that the programmatic configuration is also re-applied after reset.
    */
  private[config] class LogbackLoggerContextListener extends ContextAwareBase with LoggerContextListener {
    def isResetResistant: Boolean = {
      true
    }

    def onStart(context: LoggerContext) {
      addLogstashAppender(context)
    }

    def onReset(context: LoggerContext) {
      addLogstashAppender(context)
    }

    def onStop(context: LoggerContext) {
      // Nothing to do.
    }

    def onLevelChange(logger: ch.qos.logback.classic.Logger, level: Level) {
      // Nothing to do.
    }
  }

}
