package io.koju.autopos.async

import java.util.concurrent.{Callable, Future}

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.{DisposableBean, InitializingBean}
import org.springframework.core.task.AsyncTaskExecutor

import scala.util.{Failure, Success, Try}

class ExceptionHandlingAsyncTaskExecutor(val executor: AsyncTaskExecutor)
  extends AsyncTaskExecutor
    with InitializingBean
    with DisposableBean {

  final private val log: Logger = LoggerFactory.getLogger(classOf[ExceptionHandlingAsyncTaskExecutor])

  def execute(task: Runnable) {
    executor.execute(createWrappedRunnable(task))
  }

  def execute(task: Runnable, startTimeout: Long) {
    executor.execute(createWrappedRunnable(task), startTimeout)
  }

  private def createCallable[T](task: Callable[T]): Callable[T] =
    new Callable[T] {
      override def call() =
        Try(task.call()) match {
          case Success(t) => t
          case Failure(ex: Exception) =>
            handle(ex)
            throw ex
        }
    }

  private def createWrappedRunnable(task: Runnable): Runnable =
    new Runnable {
      override def run() {
        Try(task.run()) match {
          case Failure(_) => handle _
          case _ =>
        }
      }
    }

  protected def handle(e: Exception) {
    log.error("Caught async exception", e)
  }

  def submit(task: Runnable): Future[_] = executor.submit(createWrappedRunnable(task))

  def submit[T](task: Callable[T]): Future[T] = executor.submit(createCallable(task))

  def destroy() {
    executor match {
      case bean: DisposableBean =>
        bean.destroy()
      case _ =>
    }
  }

  def afterPropertiesSet() {
    executor match {
      case bean: InitializingBean =>
        bean.afterPropertiesSet()
      case _ =>
    }
  }
}