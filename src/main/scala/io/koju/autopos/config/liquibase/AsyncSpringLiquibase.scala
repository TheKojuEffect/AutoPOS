package io.koju.autopos.config.liquibase

import javax.inject.Inject

import io.koju.autopos.config.Constants
import liquibase.exception.LiquibaseException
import liquibase.integration.spring.SpringLiquibase
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.env.Environment
import org.springframework.core.task.TaskExecutor
import org.springframework.util.StopWatch

import scala.util.{Failure, Try}

/**
  * Specific liquibase.integration.spring.SpringLiquibase that will update the database asynchronously.
  * <p>
  * By default, this asynchronous version only works when using the "dev" profile.<p>
  * The standard liquibase.integration.spring.SpringLiquibase starts Liquibase in the current thread:
  * <ul>
  * <li>This is needed if you want to do some database requests at startup</li>
  * <li>This ensure that the database is ready when the application starts</li>
  * </ul>
  * But as this is a rather slow process, we use this asynchronous version to speed up our start-up time:
  * <ul>
  * <li>On a recent MacBook Pro, start-up time is down from 14 seconds to 8 seconds</li>
  * <li>In production, this can help your application run on platforms like Heroku, where it must start/restart very 
  * quickly</li>
  * </ul>
  */
class AsyncSpringLiquibase
  extends SpringLiquibase {

  final private val logger: Logger = LoggerFactory.getLogger(classOf[AsyncSpringLiquibase])

  @Inject
  @Qualifier("taskExecutor") private val taskExecutor: TaskExecutor = null

  @Inject val env: Environment = null

  override def afterPropertiesSet() {
    if (env.acceptsProfiles(Constants.SPRING_PROFILE_NO_LIQUIBASE)) {
      logger.debug("Liquibase is disabled")
    } else {
      if (env.acceptsProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)) {
        taskExecutor.execute(initDbRunnable)
      } else {
        logger.debug("Starting Liquibase synchronously")
        initDb()
      }
    }
  }

  def initDbRunnable: Runnable = {
    new Runnable {
      override def run() {
        Try {
          logger.warn("Starting Liquibase asynchronously, your database might not be ready at startup!")
          initDb()
        } match {
          case Failure(e: LiquibaseException) =>
            logger.error(s"Liquibase could not start correctly, your database is NOT ready: ${e.getMessage}", e)
        }
      }
    }
  }

  private def initDb() {
    val watch: StopWatch = new StopWatch
    watch.start()
    super.afterPropertiesSet()
    watch.stop()
    logger.debug("Started Liquibase in {} ms", watch.getTotalTimeMillis)
  }
}