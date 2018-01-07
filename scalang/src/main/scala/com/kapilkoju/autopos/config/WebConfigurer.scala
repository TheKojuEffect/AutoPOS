package com.kapilkoju.autopos.config

import java.io.File
import java.nio.file.Paths
import java.util._
import javax.servlet._

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.servlet.InstrumentedFilter
import com.codahale.metrics.servlets.MetricsServlet
import io.github.jhipster.config.{JHipsterConstants, JHipsterProperties}
import io.github.jhipster.web.filter.CachingHttpHeadersFilter
import io.undertow.UndertowOptions
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded._
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment
import org.springframework.web.cors.{CorsConfiguration, UrlBasedCorsConfigurationSource}
import org.springframework.web.filter.CorsFilter

/**
  * Configuration of web application with Servlet 3.0 APIs.
  */
@Configuration
class WebConfigurer(val env: Environment,
                    val jHipsterProperties: JHipsterProperties)
  extends ServletContextInitializer
    with EmbeddedServletContainerCustomizer {

  final private val log: Logger = LoggerFactory.getLogger(classOf[WebConfigurer])

  private var metricRegistry: MetricRegistry = null

  @throws[ServletException]
  def onStartup(servletContext: ServletContext) {
    if (env.getActiveProfiles.length != 0) {
      log.info("Web application configuration, using profiles: {}", env.getActiveProfiles.asInstanceOf[Array[AnyRef]])
    }
    val disps: java.util.EnumSet[DispatcherType] = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC)
    initMetrics(servletContext, disps)
    if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
      initCachingHttpHeadersFilter(servletContext, disps)
    }
    log.info("Web application fully configured")
  }

  /**
    * Customize the Servlet engine: Mime types, the document root, the cache.
    */
  def customize(container: ConfigurableEmbeddedServletContainer) {
    val mappings: MimeMappings = new MimeMappings(MimeMappings.DEFAULT)
    // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
    mappings.add("html", "text/html;charset=utf-8")
    // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
    mappings.add("json", "text/html;charset=utf-8")
    container.setMimeMappings(mappings)
    // When running in an IDE or with ./gradlew bootRun, set location of the static web assets.
    setLocationForStaticAssets(container)
            /*
             * Enable HTTP/2 for Undertow - https://twitter.com/ankinson/status/829256167700492288
             * HTTP/2 requires HTTPS, so HTTP requests will fallback to HTTP/1.1.
             * See the JHipsterProperties class and your application-*.yml configuration files
             * for more information.
             */
//    if (jHipsterProperties.getHttp.getVersion == JHipsterProperties.Http.Version.V_2_0 && container.isInstanceOf[UndertowEmbeddedServletContainerFactory]) {
//      (container.asInstanceOf[UndertowEmbeddedServletContainerFactory]).addBuilderCustomizers(builder =>
//        builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true))
//    }
  }

  private def setLocationForStaticAssets(container: ConfigurableEmbeddedServletContainer) {
    var root: File = null
    val prefixPath: String = resolvePathPrefix
    root = new File(prefixPath + "build/www/")
    if (root.exists && root.isDirectory) {
      container.setDocumentRoot(root)
    }
  }

  /**
    * Resolve path prefix to static resources.
    */
  private def resolvePathPrefix: String = {
    val fullExecutablePath: String = this.getClass.getResource("").getPath
    val rootPath: String = Paths.get(".").toUri.normalize.getPath
    val extractedPath: String = fullExecutablePath.replace(rootPath, "")
    val extractionEndIndex: Int = extractedPath.indexOf("build/")
    if (extractionEndIndex <= 0) {
      return ""
    }
    return extractedPath.substring(0, extractionEndIndex)
  }

  /**
    * Initializes the caching HTTP Headers Filter.
    */
  private def initCachingHttpHeadersFilter(servletContext: ServletContext, disps: java.util.EnumSet[DispatcherType]) {
    log.debug("Registering Caching HTTP Headers Filter")
    val cachingHttpHeadersFilter: FilterRegistration.Dynamic = servletContext.addFilter("cachingHttpHeadersFilter", new CachingHttpHeadersFilter(jHipsterProperties))
    cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/content/*")
    cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*")
    cachingHttpHeadersFilter.setAsyncSupported(true)
  }

  /**
    * Initializes Metrics.
    */
  private def initMetrics(servletContext: ServletContext, disps: java.util.EnumSet[DispatcherType]) {
    log.debug("Initializing Metrics registries")
    servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, metricRegistry)
    servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry)
    log.debug("Registering Metrics Filter")
    val metricsFilter: FilterRegistration.Dynamic = servletContext.addFilter("webappMetricsFilter", new InstrumentedFilter)
    metricsFilter.addMappingForUrlPatterns(disps, true, "/*")
    metricsFilter.setAsyncSupported(true)
    log.debug("Registering Metrics Servlet")
    val metricsAdminServlet: ServletRegistration.Dynamic = servletContext.addServlet("metricsServlet", new MetricsServlet)
    metricsAdminServlet.addMapping("/management/metrics/*")
    metricsAdminServlet.setAsyncSupported(true)
    metricsAdminServlet.setLoadOnStartup(2)
  }

  @Bean def corsFilter: CorsFilter = {
    val source: UrlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource
    val config: CorsConfiguration = jHipsterProperties.getCors
    if (config.getAllowedOrigins != null && !config.getAllowedOrigins.isEmpty) {
      log.debug("Registering CORS filter")
      source.registerCorsConfiguration("/api/**", config)
      source.registerCorsConfiguration("/v2/api-docs", config)
    }
    return new CorsFilter(source)
  }

  @Autowired(required = false) def setMetricRegistry(metricRegistry: MetricRegistry) {
    this.metricRegistry = metricRegistry
  }
}