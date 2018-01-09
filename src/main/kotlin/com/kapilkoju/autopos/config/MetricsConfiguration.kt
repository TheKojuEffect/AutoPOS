package com.kapilkoju.autopos.config

import com.codahale.metrics.JmxReporter
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.Slf4jReporter
import com.codahale.metrics.health.HealthCheckRegistry
import com.codahale.metrics.jvm.*
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter
import com.zaxxer.hikari.HikariDataSource
import io.github.jhipster.config.JHipsterProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Configuration
@EnableMetrics(proxyTargetClass = true)
class MetricsConfiguration(val jHipsterProperties: JHipsterProperties)
    : MetricsConfigurerAdapter() {

    final private val log: Logger = LoggerFactory.getLogger(MetricsConfiguration::class.java)

    private val metricRegistry: MetricRegistry = MetricRegistry()
    private val healthCheckRegistry: HealthCheckRegistry = HealthCheckRegistry()
    private var hikariDataSource: HikariDataSource? = null

    @Autowired(required = false)
    fun setHikariDataSource(hikariDataSource: HikariDataSource) {
        this.hikariDataSource = hikariDataSource
    }

    @Bean
    override fun getMetricRegistry(): MetricRegistry {
        return metricRegistry
    }

    @Bean
    override fun getHealthCheckRegistry(): HealthCheckRegistry {
        return healthCheckRegistry
    }

    @PostConstruct
    fun initialize() {
        log.debug("Registering JVM gauges")
        metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_MEMORY, MemoryUsageGaugeSet())
        metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_GARBAGE, GarbageCollectorMetricSet())
        metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_THREADS, ThreadStatesGaugeSet())
        metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_FILES, FileDescriptorRatioGauge())
        metricRegistry.register(MetricsConfiguration.PROP_METRIC_REG_JVM_BUFFERS, BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()))
        if (hikariDataSource != null) {
            log.debug("Monitoring the datasource")
            hikariDataSource!!.setMetricRegistry(metricRegistry)
        }
        if (jHipsterProperties.metrics.jmx.isEnabled) {
            log.debug("Initializing Metrics JMX reporting")
            val jmxReporter: JmxReporter = JmxReporter.forRegistry(metricRegistry).build()
            jmxReporter.start()
        }
        if (jHipsterProperties.metrics.logs.isEnabled) {
            log.info("Initializing Metrics Log reporting")
            val reporter: Slf4jReporter = Slf4jReporter.forRegistry(metricRegistry).outputTo(LoggerFactory.getLogger("metrics"))
                    .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build()
            reporter.start(jHipsterProperties.metrics.logs.reportFrequency, TimeUnit.SECONDS)
        }
    }

    companion object MetricsConfiguration {
        private val PROP_METRIC_REG_JVM_MEMORY: String = "jvm.memory"
        private val PROP_METRIC_REG_JVM_GARBAGE: String = "jvm.garbage"
        private val PROP_METRIC_REG_JVM_THREADS: String = "jvm.threads"
        private val PROP_METRIC_REG_JVM_FILES: String = "jvm.files"
        private val PROP_METRIC_REG_JVM_BUFFERS: String = "jvm.buffers"
    }
}