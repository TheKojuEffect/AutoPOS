package com.kapilkoju.autopos

import java.net.{InetAddress, UnknownHostException}
import javax.annotation.PostConstruct

import com.kapilkoju.autopos.Application.log
import com.kapilkoju.autopos.config.{ApplicationProperties, DefaultProfileUtil}
import io.github.jhipster.config.JHipsterConstants
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure._
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.Environment

@ComponentScan
@EnableAutoConfiguration(
  exclude = Array(classOf[MetricFilterAutoConfiguration], classOf[MetricRepositoryAutoConfiguration]))
@EnableConfigurationProperties(Array(classOf[LiquibaseProperties], classOf[ApplicationProperties]))
class Application(val env: Environment) {

  /**
    * Initializes AutoPOS.
    * <p>
    * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
    * <p>
    * You can find more information on how profiles work with AutoPOS on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
    */
  @PostConstruct def initApplication() {
    val activeProfiles = List(env.getActiveProfiles)
    if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
      log.error("You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time.")
    }
    if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
      log.error("You have misconfigured your application! It should not" + "run with both the 'dev' and 'cloud' profiles at the same time.")
    }
  }
}


object Application {
  final val log: Logger = LoggerFactory.getLogger(classOf[Application])

  /**
    * Main method, used to run the application.
    *
    * @param args the command line arguments
    * @throws UnknownHostException if the local host name could not be resolved into an address
    */
  def main(args: Array[String]) {
    val app: SpringApplication = new SpringApplication(classOf[Application])
    DefaultProfileUtil.addDefaultProfile(app)
    val env: Environment = app.run(args: _*).getEnvironment
    var protocol: String = "http"
    if (env.getProperty("server.ssl.key-store") != null) {
      protocol = "https"
    }
    log.info("\n----------------------------------------------------------\n\t" + "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t" + "External: \t{}://{}:{}\n\t" + "Profile(s): \t{}\n----------------------------------------------------------", env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol, InetAddress.getLocalHost.getHostAddress, env.getProperty("server.port"), env.getActiveProfiles)
  }
}
