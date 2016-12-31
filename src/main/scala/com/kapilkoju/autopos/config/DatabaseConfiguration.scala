package com.kapilkoju.autopos.config

import javax.sql.DataSource

import _root_.liquibase.integration.spring.SpringLiquibase
import com.codahale.metrics.MetricRegistry
import com.kapilkoju.autopos.accounting.domain.AccountingDomainPackage
import com.kapilkoju.autopos.accounting.repo.AccountingRepoPackage
import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.catalog.repo.CatalogRepoPackage
import com.kapilkoju.autopos.catalog.service.CatalogServicePackage
import com.kapilkoju.autopos.config.liquibase.AsyncSpringLiquibase
import com.kapilkoju.autopos.domain.DomainPackage
import com.kapilkoju.autopos.kernel.domain.AbstractEntity
import com.kapilkoju.autopos.party.domain.PartyDomainPackage
import com.kapilkoju.autopos.party.service.PartyServicePackage
import com.kapilkoju.autopos.repository.RepositoryPackage
import com.kapilkoju.autopos.shared.domain.SharedDomainPackage
import com.kapilkoju.autopos.shared.service.SharedServicePackage
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
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.{DataSourceBuilder, DataSourceProperties}
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.ApplicationContextException
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
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
    classOf[CatalogRepoPackage]
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
    classOf[TransactionDomainPackage]))
class DatabaseConfiguration(private val env: Environment) {

  final private val log: Logger = LoggerFactory.getLogger(classOf[DatabaseConfiguration])

  @Autowired(required = false)
  private val metricRegistry: MetricRegistry = null

  @Bean(destroyMethod = "close")
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  def dataSource(dataSourceProperties: DataSourceProperties): DataSource = {
    log.debug("Configuring Datasource")
    if (dataSourceProperties.getUrl == null) {
      log.error("Your database connection pool configuration is incorrect! The application" + " cannot start. Please " +
        "check your Spring profile, current profiles are: {}", env.getActiveProfiles)
      throw new ApplicationContextException("Database connection pool is not configured correctly")
    }
    val hikariDataSource: HikariDataSource =
      DataSourceBuilder.create(dataSourceProperties.getClassLoader)
        .`type`(classOf[HikariDataSource])
        .driverClassName(dataSourceProperties.getDriverClassName).url(dataSourceProperties
        .getUrl).username(dataSourceProperties.getUsername).password(dataSourceProperties.getPassword).build
        .asInstanceOf[HikariDataSource]
    if (metricRegistry != null) {
      hikariDataSource.setMetricRegistry(metricRegistry)
    }
    hikariDataSource
  }

  @Bean
  def liquibase(dataSource: DataSource,
                dataSourceProperties: DataSourceProperties,
                liquibaseProperties: LiquibaseProperties
               ): SpringLiquibase = {
    // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
    val liquibase: SpringLiquibase = new AsyncSpringLiquibase
    liquibase.setDataSource(dataSource)
    liquibase.setChangeLog(liquibaseProperties.getChangeLog)
    liquibase.setContexts(liquibaseProperties.getContexts)
    liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema)
    liquibase.setDropFirst(liquibaseProperties.isDropFirst)
    if (env.acceptsProfiles(Constants.SPRING_PROFILE_NO_LIQUIBASE)) {
      liquibase.setShouldRun(false)
    }
    else {
      liquibase.setShouldRun(liquibaseProperties.isEnabled)
      log.debug("Configuring Liquibase")
    }
    liquibase
  }
}
