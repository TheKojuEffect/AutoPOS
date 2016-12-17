package com.kapilkoju.autopos.config

import java.io.File
import java.util
import javax.servlet._

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.servlet.InstrumentedFilter
import com.codahale.metrics.servlets.MetricsServlet
import com.kapilkoju.autopos.web.filter.CachingHttpHeadersFilter
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.{ConfigurableEmbeddedServletContainer,
EmbeddedServletContainerCustomizer, MimeMappings}
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment
import org.springframework.web.cors.{CorsConfiguration, UrlBasedCorsConfigurationSource}
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
  * Configuration of web application with Servlet 3.0 APIs.
  */
@Configuration
class WebConfigurer(private val env: Environment,
                    private val jHipsterProperties: AutoposProperties)
  extends WebMvcConfigurerAdapter
    with ServletContextInitializer
    with EmbeddedServletContainerCustomizer {

  final private val log: Logger = LoggerFactory.getLogger(classOf[WebConfigurer])

  @Autowired(required = false)
  private val metricRegistry: MetricRegistry = null

  @throws[ServletException]
  def onStartup(servletContext: ServletContext) {
    log.info("Web application configuration, using profiles: {}", env.getActiveProfiles)
    val disps: util.EnumSet[DispatcherType] = util.EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
      DispatcherType.ASYNC)
    initMetrics(servletContext, disps)
    if (env.acceptsProfiles(Constants.SPRING_PROFILE_PRODUCTION)) {
      initCachingHttpHeadersFilter(servletContext, disps)
    }
    log.info("Web application fully configured")
  }

  /**
    * Set up Mime types and, if needed, set the document root.
    */
  def customize(container: ConfigurableEmbeddedServletContainer) {
    val mappings: MimeMappings = new MimeMappings(MimeMappings.DEFAULT)
    // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
    mappings.add("html", "text/html;charset=utf-8")
    // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
    mappings.add("json", "text/html;charset=utf-8")
    container.setMimeMappings(mappings)
    // When running in an IDE or with ./gradlew bootRun, set location of the static web assets.
    var root: File = null
    if (env.acceptsProfiles(Constants.SPRING_PROFILE_PRODUCTION)) {
      root = new File("build/www/")
    }
    else {
      root = new File("src/main/webapp/")
    }
    if (root.exists && root.isDirectory) {
      container.setDocumentRoot(root)
    }
  }

  /**
    * Initializes the caching HTTP Headers Filter.
    */
  private def initCachingHttpHeadersFilter(servletContext: ServletContext, disps: util.EnumSet[DispatcherType]) {
    log.debug("Registering Caching HTTP Headers Filter")
    val cachingHttpHeadersFilter: FilterRegistration.Dynamic = servletContext.addFilter("cachingHttpHeadersFilter",
      new CachingHttpHeadersFilter(jHipsterProperties))
    cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/content/*")
    cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*")
    cachingHttpHeadersFilter.setAsyncSupported(true)
  }

  /**
    * Initializes Metrics.
    */
  private def initMetrics(servletContext: ServletContext, disps: util.EnumSet[DispatcherType]) {
    log.debug("Initializing Metrics registries")
    servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, metricRegistry)
    servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry)
    log.debug("Registering Metrics Filter")
    val metricsFilter: FilterRegistration.Dynamic = servletContext.addFilter("webappMetricsFilter", new
        InstrumentedFilter)
    metricsFilter.addMappingForUrlPatterns(disps, true, "/*")
    metricsFilter.setAsyncSupported(true)
    log.debug("Registering Metrics Servlet")
    val metricsAdminServlet: ServletRegistration.Dynamic = servletContext.addServlet("metricsServlet", new
        MetricsServlet)
    metricsAdminServlet.addMapping("/metrics/metrics/*")
    metricsAdminServlet.setAsyncSupported(true)
    metricsAdminServlet.setLoadOnStartup(2)
  }


  @Bean def corsFilter: CorsFilter = {
    val source: UrlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource
    val config: CorsConfiguration = jHipsterProperties.getCors
    if (config.getAllowedOrigins != null && !config.getAllowedOrigins.isEmpty) {
      source.registerCorsConfiguration("/api/**", config)
    }
    new CorsFilter(source)
  }
}
