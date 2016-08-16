package io.koju.autopos

import io.koju.autopos.config.Constants
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer

class ApplicationWebXml extends SpringBootServletInitializer {

  private val log: Logger = LoggerFactory.getLogger(classOf[ApplicationWebXml])

  override def configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
    application.profiles(addDefaultProfile())
    .sources(classOf[Application])

  private def addDefaultProfile(): String = {
    val profile = System.getProperty("spring.profiles.active")
    if (profile != null) {
      log.info("Running with Spring profile(s) : {}", profile)
      return profile
    }
    log.warn("No Spring profile configured, running with default configuration")
    Constants.SPRING_PROFILE_DEVELOPMENT
  }

}
