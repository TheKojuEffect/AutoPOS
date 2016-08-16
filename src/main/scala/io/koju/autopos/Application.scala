package io.koju.autopos

import java.net.InetAddress
import javax.annotation.PostConstruct

import io.koju.autopos.config.{Constants, JHipsterProperties}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.{MetricFilterAutoConfiguration, MetricRepositoryAutoConfiguration}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.{Environment, SimpleCommandLinePropertySource}

@ComponentScan
@EnableAutoConfiguration(
  exclude = Array(classOf[MetricFilterAutoConfiguration], classOf[MetricRepositoryAutoConfiguration]))
@EnableConfigurationProperties(Array(classOf[JHipsterProperties], classOf[LiquibaseProperties]))
class Application {

  private val log: Logger = LoggerFactory.getLogger(classOf[Application])

  @Autowired
  private val env: Environment = null

  /**
    * Initializes AutoPOS.
    * <p>
    * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
    * <p>
    * You can find more information on how profiles work with JHipster on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
    */
  @PostConstruct
  def initApplication(): Unit = {
    if (env.getActiveProfiles.length == 0) {
      log.warn("No Spring profile configured, running with default configuration")
    } else {
      log.info("Running with Spring profile(s) : {}", env.getActiveProfiles)

      val activeProfiles = List(env.getActiveProfiles)

      if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants
                                                                                                   .SPRING_PROFILE_PRODUCTION)) {
        log.error("You have mis-configured your application! " +
          "It should not run with both the 'dev' and 'prod' profiles at the same time.")
      }
    }
  }

}

object Application {
  val log: Logger = LoggerFactory.getLogger(classOf[Application])

  def main(args: Array[String]): Unit = {
    val app = new SpringApplication(classOf[Application])
    val source = new SimpleCommandLinePropertySource(args: _*)
    addDefaultProfile(app, source)
    val env = app.run(args: _*).getEnvironment
    log.info("\n----------------------------------------------------------\n\t" +
      "Application '{}' is running! Access URLs:\n\t" +
      "Local: \t\thttp://127.0.0.1:{}\n\t" +
      "External: \thttp://{}:{}\n----------------------------------------------------------",
      env.getProperty("spring.application.name"),
      env.getProperty("server.port"),
      InetAddress.getLocalHost.getHostAddress,
      env.getProperty("server.port"))
  }


  def addDefaultProfile(app: SpringApplication, source: SimpleCommandLinePropertySource): Unit = {
    if (!source.containsProperty("spring.profiles.active") && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
      app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)
    }
  }
}
