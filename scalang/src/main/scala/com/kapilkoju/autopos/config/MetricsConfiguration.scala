package com.kapilkoju.autopos.config

import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

import com.codahale.metrics.health.HealthCheckRegistry
import com.codahale.metrics.jvm._
import com.codahale.metrics.{JmxReporter, MetricRegistry, Slf4jReporter}
import com.ryantenney.metrics.spring.config.annotation.{EnableMetrics, MetricsConfigurerAdapter}
import com.zaxxer.hikari.HikariDataSource
import io.github.jhipster.config.JHipsterProperties
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation._

object MetricsConfiguration {
  private val PROP_METRIC_REG_JVM_MEMORY: String = "jvm.memory"
  private val PROP_METRIC_REG_JVM_GARBAGE: String = "jvm.garbage"
  private val PROP_METRIC_REG_JVM_THREADS: String = "jvm.threads"
  private val PROP_METRIC_REG_JVM_FILES: String = "jvm.files"
  private val PROP_METRIC_REG_JVM_BUFFERS: String = "jvm.buffers"
}

@Configuration
@EnableMetrics(proxyTargetClass = true)
class MetricsConfiguration(val jHipsterProperties: JHipsterProperties)
  extends MetricsConfigurerAdapter {

  final private val log: Logger = LoggerFactory.getLogger(classOf[MetricsConfiguration])

  private val metricRegistry: MetricRegistry = new MetricRegistry
  private val healthCheckRegistry: HealthCheckRegistry = new HealthCheckRegistry
  private var hikariDataSource: HikariDataSource = null

  @Autowired(required = false)
  def setHikariDataSource(hikariDataSource: HikariDataSource) {
    this.hikariDataSource = hikariDataSource
  }

  @Bean
  override def getMetricRegistry: MetricRegistry = {
    metricRegistry
  }

  @Bean
  override def getHealthCheckRegistry: HealthCheckRegistry = {
    healthCheckRegistry
  }

  @PostConstruct
  def init() {
    log.debug("Registering JVM gauges")
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_MEMORY, new MemoryUsageGaugeSet)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_GARBAGE, new GarbageCollectorMetricSet)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_THREADS, new ThreadStatesGaugeSet)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_FILES, new FileDescriptorRatioGauge)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_BUFFERS, new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer))
    if (hikariDataSource != null) {
      log.debug("Monitoring the datasource")
      hikariDataSource.setMetricRegistry(metricRegistry)
    }
    if (jHipsterProperties.getMetrics.getJmx.isEnabled) {
      log.debug("Initializing Metrics JMX reporting")
      val jmxReporter: JmxReporter = JmxReporter.forRegistry(metricRegistry).build
      jmxReporter.start()
    }
    if (jHipsterProperties.getMetrics.getLogs.isEnabled) {
      log.info("Initializing Metrics Log reporting")
      val reporter: Slf4jReporter = Slf4jReporter.forRegistry(metricRegistry).outputTo(LoggerFactory.getLogger("metrics")).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build
      reporter.start(jHipsterProperties.getMetrics.getLogs.getReportFrequency, TimeUnit.SECONDS)
    }
  }
}