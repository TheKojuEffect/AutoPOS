package com.kapilkoju.autopos.config

import com.kapilkoju.autopos.web.filter.CachingHttpHeadersFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.MimeMappings
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.io.File
import java.nio.file.Paths
import java.util.*
import javax.servlet.DispatcherType
import javax.servlet.FilterRegistration
import javax.servlet.ServletContext

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
class WebConfigurer(val env: Environment,
                    val jHipsterProperties: JHipsterProperties)
    : ServletContextInitializer, EmbeddedServletContainerCustomizer {

    final private val log: Logger = LoggerFactory.getLogger(WebConfigurer::class.java)

    override fun onStartup(servletContext: ServletContext) {
        if (env.activeProfiles.isNotEmpty()) {
            log.info("Web application configuration, using profiles: {}", env.activeProfiles)
        }
        val disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC)

        if (env.acceptsProfiles(SpringProfiles.PRODUCTION)) {
            initCachingHttpHeadersFilter(servletContext, disps)
        }
        log.info("Web application fully configured")
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    override fun customize(container: ConfigurableEmbeddedServletContainer) {
        val mappings = MimeMappings(MimeMappings.DEFAULT)
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

    private fun setLocationForStaticAssets(container: ConfigurableEmbeddedServletContainer) {
        val prefixPath: String = resolvePathPrefix()
        val root = File(prefixPath + "build/www/")
        if (root.exists() && root.isDirectory) {
            container.setDocumentRoot(root)
        }
    }

    /**
     * Resolve path prefix to static resources.
     */
    private fun resolvePathPrefix(): String {
        val fullExecutablePath: String = javaClass.getResource("").path
        val rootPath: String = Paths.get(".").toUri().normalize().path
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
    private fun initCachingHttpHeadersFilter(servletContext: ServletContext, disps: EnumSet<DispatcherType>) {
        log.debug("Registering Caching HTTP Headers Filter")
        val cachingHttpHeadersFilter: FilterRegistration.Dynamic = servletContext.addFilter("cachingHttpHeadersFilter", CachingHttpHeadersFilter(jHipsterProperties))
        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/content/*")
        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*")
        cachingHttpHeadersFilter.setAsyncSupported(true)
    }


    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config: CorsConfiguration = jHipsterProperties.cors
        if (config.allowedOrigins != null && !config.allowedOrigins.isEmpty()) {
            log.debug("Registering CORS filter")
            source.registerCorsConfiguration("/api/**", config)
            source.registerCorsConfiguration("/v2/api-docs", config)
        }
        return CorsFilter(source)
    }

}