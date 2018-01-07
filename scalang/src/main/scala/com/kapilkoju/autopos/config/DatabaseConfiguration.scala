package com.kapilkoju.autopos.config

import javax.sql.DataSource

import _root_.liquibase.integration.spring.SpringLiquibase
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.kapilkoju.autopos.accounting.domain.AccountingDomainPackage
import com.kapilkoju.autopos.accounting.repo.AccountingRepoPackage
import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.catalog.service.CatalogServicePackage
import com.kapilkoju.autopos.domain.DomainPackage
import com.kapilkoju.autopos.kernel.domain.AbstractEntity
import com.kapilkoju.autopos.party.domain.PartyDomainPackage
import com.kapilkoju.autopos.party.service.PartyServicePackage
import com.kapilkoju.autopos.repository.RepositoryPackage
import com.kapilkoju.autopos.security.SecurityUtils
import com.kapilkoju.autopos.shared.domain.SharedDomainPackage
import com.kapilkoju.autopos.shared.service.SharedServicePackage
import com.kapilkoju.autopos.stockbook.domain.StockBookDomainPackage
import com.kapilkoju.autopos.stockbook.service.StockBookServicePackage
import com.kapilkoju.autopos.trade.domain.TradeDomainPackage
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseDomainPackage
import com.kapilkoju.autopos.trade.purchase.service.PurchaseServicePackage
import com.kapilkoju.autopos.trade.sale.domain.SaleDomainPackage
import com.kapilkoju.autopos.trade.sale.repo.SaleRepoPackage
import com.kapilkoju.autopos.trade.service.TradeServicePackage
import com.kapilkoju.autopos.transaction.domain.TransactionDomainPackage
import com.kapilkoju.autopos.transaction.service.TransactionServicePackage
import com.kapilkoju.autopos.user.domain.User
import com.kapilkoju.autopos.user.service.UserServicePackage
import io.github.jhipster.config.JHipsterConstants
import io.github.jhipster.config.liquibase.AsyncSpringLiquibase
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment
import org.springframework.core.task.TaskExecutor
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.{EnableJpaAuditing, EnableJpaRepositories}
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackageClasses = Array(
    classOf[RepositoryPackage],
    classOf[SharedServicePackage],
    classOf[CatalogServicePackage],
    classOf[UserServicePackage],
    classOf[AccountingRepoPackage],
    classOf[PartyServicePackage],
    classOf[TradeServicePackage],
    classOf[SaleRepoPackage],
    classOf[PurchaseServicePackage],
    classOf[TransactionServicePackage],
    classOf[CatalogServicePackage],
    classOf[StockBookServicePackage]
  ))
@EntityScan(
  basePackageClasses = Array(
    classOf[AbstractEntity[_ <: Serializable]],
    classOf[DomainPackage],
    classOf[SharedDomainPackage],
    classOf[User],
    classOf[Item],
    classOf[AccountingDomainPackage],
    classOf[PartyDomainPackage],
    classOf[TradeDomainPackage],
    classOf[SaleDomainPackage],
    classOf[PurchaseDomainPackage],
    classOf[TransactionDomainPackage],
    classOf[StockBookDomainPackage])
)
@EnableJpaAuditing
class DatabaseConfiguration(private val env: Environment) {

  final private val log: Logger = LoggerFactory.getLogger(classOf[DatabaseConfiguration])

  @Bean def liquibase(@Qualifier("taskExecutor") taskExecutor: TaskExecutor,
                      dataSource: DataSource,
                      liquibaseProperties: LiquibaseProperties
                     ): SpringLiquibase = {
    // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
    val liquibase: SpringLiquibase = new AsyncSpringLiquibase(taskExecutor, env)
    liquibase.setDataSource(dataSource)
    liquibase.setChangeLog("classpath:/config/liquibase/master.xml")
    liquibase.setContexts(liquibaseProperties.getContexts)
    liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema)
    liquibase.setDropFirst(liquibaseProperties.isDropFirst)
    if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_NO_LIQUIBASE)) {
      liquibase.setShouldRun(false)
    }
    else {
      liquibase.setShouldRun(liquibaseProperties.isEnabled)
      log.debug("Configuring Liquibase")
    }
    liquibase
  }

  @Bean def hibernate5Module: Hibernate5Module = {
    new Hibernate5Module
  }

  @Bean
  def auditorAware: AuditorAware[User] = new AuditorAware[User] {
    override def getCurrentAuditor = SecurityUtils.getCurrentUser
  }
}
