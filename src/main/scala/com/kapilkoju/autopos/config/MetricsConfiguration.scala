package com.kapilkoju.autopos.config

import io.github.jhipster.config.JHipsterProperties
import com.codahale.metrics.JmxReporter
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.Slf4jReporter
import com.codahale.metrics.health.HealthCheckRegistry
import com.codahale.metrics.jvm._
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation._
import javax.annotation.PostConstruct
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

@Configuration
@EnableMetrics(proxyTargetClass = true) object MetricsConfiguration {
  private val PROP_METRIC_REG_JVM_MEMORY: String = "jvm.memory"
  private val PROP_METRIC_REG_JVM_GARBAGE: String = "jvm.garbage"
  private val PROP_METRIC_REG_JVM_THREADS: String = "jvm.threads"
  private val PROP_METRIC_REG_JVM_FILES: String = "jvm.files"
  private val PROP_METRIC_REG_JVM_BUFFERS: String = "jvm.buffers"
}

@Configuration
@EnableMetrics(proxyTargetClass = true) class MetricsConfiguration(val jHipsterProperties: JHipsterProperties) extends MetricsConfigurerAdapter {
  final private val log: Logger = LoggerFactory.getLogger(classOf[MetricsConfiguration])
  private val metricRegistry: MetricRegistry = new MetricRegistry
  private val healthCheckRegistry: HealthCheckRegistry = new HealthCheckRegistry
  private var hikariDataSource: HikariDataSource = null

  @Autowired(required = false) def setHikariDataSource(hikariDataSource: HikariDataSource) {
    this.hikariDataSource = hikariDataSource
  }

  @Bean override def getMetricRegistry: MetricRegistry = {
    return metricRegistry
  }

  @Bean override def getHealthCheckRegistry: HealthCheckRegistry = {
    return healthCheckRegistry
  }

  @PostConstruct def init() {
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