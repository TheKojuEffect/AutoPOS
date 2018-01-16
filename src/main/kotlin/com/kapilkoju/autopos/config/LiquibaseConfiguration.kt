package com.kapilkoju.autopos.config

import com.kapilkoju.autopos.config.liquibase.AsyncSpringLiquibase
import liquibase.integration.spring.SpringLiquibase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.task.TaskExecutor
import javax.sql.DataSource

@Configuration
class LiquibaseConfiguration(private val env: Environment) {

    final private val log: Logger = LoggerFactory.getLogger(LiquibaseConfiguration::class.java)

    @Bean
    fun liquibase(@Qualifier("taskExecutor") taskExecutor: TaskExecutor,
                  dataSource: DataSource,
                  liquibaseProperties: LiquibaseProperties
    ): SpringLiquibase {
        // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
        val liquibase = AsyncSpringLiquibase(taskExecutor, env).apply {
            setDataSource(dataSource)
            changeLog = "classpath:/config/liquibase/master.xml"
            contexts = liquibaseProperties.contexts
            defaultSchema = liquibaseProperties.defaultSchema
            isDropFirst = liquibaseProperties.isDropFirst
        }
        if (env.acceptsProfiles(SpringProfiles.NO_LIQUIBASE)) {
            liquibase.setShouldRun(false)
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled)
            log.debug("Configuring Liquibase")
        }
        return liquibase
    }

}
