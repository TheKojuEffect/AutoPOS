package com.kapilkoju.autopos.config

import java.lang.management.ManagementFactory
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

import com.codahale.metrics.graphite.{Graphite, GraphiteReporter}
import com.codahale.metrics.health.HealthCheckRegistry
import com.codahale.metrics.jvm._
import com.codahale.metrics.{JmxReporter, MetricRegistry, Slf4jReporter}
import com.ryantenney.metrics.spring.config.annotation.{EnableMetrics, MetricsConfigurerAdapter}
import fr.ippon.spark.metrics.SparkReporter
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.{Bean, Configuration}

object MetricsConfiguration {
  private val PROP_METRIC_REG_JVM_MEMORY: String = "jvm.memory"
  private val PROP_METRIC_REG_JVM_GARBAGE: String = "jvm.garbage"
  private val PROP_METRIC_REG_JVM_THREADS: String = "jvm.threads"
  private val PROP_METRIC_REG_JVM_FILES: String = "jvm.files"
  private val PROP_METRIC_REG_JVM_BUFFERS: String = "jvm.buffers"

  @Configuration
  @ConditionalOnClass(Array(classOf[Graphite]))
  class GraphiteRegistry(private val metricRegistry: MetricRegistry,
                         private val jHipsterProperties: AutoposProperties) {
    final private val log: Logger = LoggerFactory.getLogger(classOf[MetricsConfiguration.GraphiteRegistry])

    @PostConstruct
    private def init() {
      if (jHipsterProperties.getMetrics.getGraphite.isEnabled) {
        log.info("Initializing Metrics Graphite reporting")
        val graphiteHost: String = jHipsterProperties.getMetrics.getGraphite.getHost
        val graphitePort: Integer = jHipsterProperties.getMetrics.getGraphite.getPort
        val graphitePrefix: String = jHipsterProperties.getMetrics.getGraphite.getPrefix
        val graphite: Graphite = new Graphite(new InetSocketAddress(graphiteHost, graphitePort))
        val graphiteReporter: GraphiteReporter = GraphiteReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit
          .SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).prefixedWith(graphitePrefix).build(graphite)
        graphiteReporter.start(1, TimeUnit.MINUTES)
      }
    }
  }

  @Configuration
  @ConditionalOnClass(Array(classOf[SparkReporter]))
  class SparkRegistry(private val metricRegistry: MetricRegistry,
                      private val jHipsterProperties: AutoposProperties) {
    final private val log: Logger = LoggerFactory.getLogger(classOf[MetricsConfiguration.SparkRegistry])

    @PostConstruct
    private def init() {
      if (jHipsterProperties.getMetrics.getSpark.isEnabled) {
        log.info("Initializing Metrics Spark reporting")
        val sparkHost: String = jHipsterProperties.getMetrics.getSpark.getHost
        val sparkPort: Integer = jHipsterProperties.getMetrics.getSpark.getPort
        val sparkReporter: SparkReporter = SparkReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS)
          .convertDurationsTo(TimeUnit.MILLISECONDS).build(sparkHost, sparkPort)
        sparkReporter.start(1, TimeUnit.MINUTES)
      }
    }
  }

}

@Configuration
@EnableMetrics(proxyTargetClass = true)
class MetricsConfiguration(private val jHipsterProperties: AutoposProperties)
  extends MetricsConfigurerAdapter {

  final private val log: Logger = LoggerFactory.getLogger(classOf[MetricsConfiguration])

  private val metricRegistry: MetricRegistry = new MetricRegistry
  private val healthCheckRegistry: HealthCheckRegistry = new HealthCheckRegistry

  @Bean
  override def getMetricRegistry: MetricRegistry = metricRegistry

  @Bean
  override def getHealthCheckRegistry: HealthCheckRegistry = healthCheckRegistry

  @PostConstruct def init() {
    log.debug("Registering JVM gauges")
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_MEMORY, new MemoryUsageGaugeSet)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_GARBAGE, new GarbageCollectorMetricSet)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_THREADS, new ThreadStatesGaugeSet)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_FILES, new FileDescriptorRatioGauge)
    metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_BUFFERS, new BufferPoolMetricSet
    (ManagementFactory.getPlatformMBeanServer))
    if (jHipsterProperties.getMetrics.getJmx.isEnabled) {
      log.debug("Initializing Metrics JMX reporting")
      val jmxReporter: JmxReporter = JmxReporter.forRegistry(metricRegistry).build
      jmxReporter.start()
    }
    if (jHipsterProperties.getMetrics.getLogs.isEnabled) {
      log.info("Initializing Metrics Log reporting")
      val reporter: Slf4jReporter = Slf4jReporter.forRegistry(metricRegistry).outputTo(LoggerFactory.getLogger
      ("metrics")).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build
      reporter.start(jHipsterProperties.getMetrics.getLogs.getReportFrequency, TimeUnit.SECONDS)
    }
  }
}
