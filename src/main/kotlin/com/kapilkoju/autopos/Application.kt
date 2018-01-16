package com.kapilkoju.autopos

import com.kapilkoju.autopos.config.ApplicationProperties
import com.kapilkoju.autopos.config.DefaultProfileUtil
import com.kapilkoju.autopos.config.JHipsterConstants
import com.kapilkoju.autopos.config.JHipsterProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.Environment
import java.net.InetAddress
import javax.annotation.PostConstruct

@ComponentScan
@EnableAutoConfiguration(exclude = [MetricFilterAutoConfiguration::class, MetricRepositoryAutoConfiguration::class])
@EnableConfigurationProperties(value = [LiquibaseProperties::class, ApplicationProperties::class, JHipsterProperties::class])
class Application(private val env: Environment) {

    /**
     * Initializes AutoPOS.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with AutoPOS on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    fun initApplication() {
        val activeProfiles = env.activeProfiles
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time.")
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" + "run with both the 'dev' and 'cloud' profiles at the same time.")
        }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(Application::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val app = SpringApplication(Application::class.java)
            DefaultProfileUtil.addDefaultProfile(app)
            val env: Environment = app.run(*args).environment
            var protocol = "http"
            if (env.getProperty("server.ssl.key-store") != null) {
                protocol = "https"
            }
            log.info("\n----------------------------------------------------------\n\t" + "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t" + "External: \t{}://{}:{}\n\t" + "Profile(s): \t{}\n----------------------------------------------------------",
                    env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol, InetAddress.getLocalHost().hostAddress, env.getProperty("server.port"), env.activeProfiles)
        }
    }

}


