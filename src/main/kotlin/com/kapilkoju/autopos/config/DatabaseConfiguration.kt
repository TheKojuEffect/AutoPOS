package com.kapilkoju.autopos.config

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.kapilkoju.autopos.accounting.domain.AccountingDomainPackage
import com.kapilkoju.autopos.accounting.service.AccountingServicePackage
import com.kapilkoju.autopos.catalog.domain.CatalogDomainPackage
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
import com.kapilkoju.autopos.trade.sale.service.SaleServicePackage
import com.kapilkoju.autopos.trade.service.TradeServicePackage
import com.kapilkoju.autopos.transaction.domain.TransactionDomainPackage
import com.kapilkoju.autopos.transaction.service.TransactionServicePackage
import com.kapilkoju.autopos.user.domain.User
import com.kapilkoju.autopos.user.service.UserServicePackage
import io.github.jhipster.config.JHipsterConstants
import io.github.jhipster.config.liquibase.AsyncSpringLiquibase
import liquibase.integration.spring.SpringLiquibase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.task.TaskExecutor
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = [
            RepositoryPackage::class,
            SharedServicePackage::class,
            CatalogServicePackage::class,
            UserServicePackage::class,
            AccountingServicePackage::class,
            PartyServicePackage::class,
            TradeServicePackage::class,
            SaleServicePackage::class,
            PurchaseServicePackage::class,
            TransactionServicePackage::class,
            CatalogServicePackage::class,
            StockBookServicePackage::class
        ])
@EntityScan(
        basePackageClasses = [
            AbstractEntity::class,
            DomainPackage::class,
            SharedDomainPackage::class,
            User::class,
            CatalogDomainPackage::class,
            AccountingDomainPackage::class,
            PartyDomainPackage::class,
            TradeDomainPackage::class,
            SaleDomainPackage::class,
            PurchaseDomainPackage::class,
            TransactionDomainPackage::class,
            StockBookDomainPackage::class]
)
@EnableJpaAuditing
class DatabaseConfiguration(private val env: Environment) {

    final private val log: Logger = LoggerFactory.getLogger(DatabaseConfiguration::class.java)

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
        if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_NO_LIQUIBASE)) {
            liquibase.setShouldRun(false)
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled)
            log.debug("Configuring Liquibase")
        }
        return liquibase
    }

    @Bean
    fun hibernate5Module(): Hibernate5Module {
        return Hibernate5Module()
    }

    @Bean
    fun auditorAware(): AuditorAware<User> {
        return AuditorAware { SecurityUtils.getCurrentUser() }
    }
}
