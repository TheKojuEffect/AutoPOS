package io.koju.autopos.config.apidoc

import java.time.{LocalDate, LocalDateTime, ZonedDateTime}
import java.util.Date

import io.koju.autopos.config.{Constants, AutoposProperties}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.util.StopWatch
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.service.{ApiInfo, Contact}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


object SwaggerConfiguration {
  val DEFAULT_INCLUDE_PATTERN: String = "/api/.*"
}

/**
  * Springfox Swagger configuration.
  *
  * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
  * case, you can use a specific Spring profile for this class, so that only front-end developers
  * have access to the Swagger view.
  */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("#{!environment.acceptsProfiles('" + Constants.SPRING_PROFILE_NO_SWAGGER + "') && " +
  "!environment.acceptsProfiles('" + Constants.SPRING_PROFILE_PRODUCTION + "')}")
class SwaggerConfiguration {
  final private val log: Logger = LoggerFactory.getLogger(classOf[SwaggerConfiguration])

  /**
    * Swagger Springfox configuration.
    *
    * @param jHipsterProperties the properties of the application
    * @return the Swagger Springfox configuration
    */
  @Bean
  def swaggerSpringfoxDocket(jHipsterProperties: AutoposProperties): Docket = {
    log.debug("Starting Swagger")
    val watch: StopWatch = new StopWatch
    watch.start()
    val contact: Contact = new Contact(jHipsterProperties.getSwagger.getContactName, jHipsterProperties.getSwagger
      .getContactUrl, jHipsterProperties.getSwagger.getContactEmail)
    val apiInfo: ApiInfo = new ApiInfo(jHipsterProperties.getSwagger.getTitle, jHipsterProperties.getSwagger
      .getDescription, jHipsterProperties.getSwagger.getVersion, jHipsterProperties.getSwagger.getTermsOfServiceUrl,
      contact, jHipsterProperties.getSwagger.getLicense, jHipsterProperties.getSwagger.getLicenseUrl)
    val docket: Docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).forCodeGeneration(true)
      .genericModelSubstitutes(classOf[ResponseEntity[_]]).ignoredParameterTypes(classOf[Pageable])
      .ignoredParameterTypes(classOf[Date]).directModelSubstitute(classOf[LocalDate], classOf[Date])
      .directModelSubstitute(classOf[ZonedDateTime], classOf[Date]).directModelSubstitute(classOf[LocalDateTime],
      classOf[Date]).select.paths(regex(SwaggerConfiguration.DEFAULT_INCLUDE_PATTERN)).build
    watch.stop()
    log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis)
    docket
  }
}