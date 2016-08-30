package io.koju.autopos.config;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariDataSource;
import io.koju.autopos.accounting.domain.AccountingDomainPackage;
import io.koju.autopos.accounting.service.AccountingServicePackage;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.service.CatalogServicePackage;
import io.koju.autopos.config.liquibase.AsyncSpringLiquibase;
import io.koju.autopos.domain.DomainPackage;
import io.koju.autopos.kernel.domain.AbstractEntity;
import io.koju.autopos.party.domain.PartyDomainPackage;
import io.koju.autopos.party.service.PartyServicePackage;
import io.koju.autopos.repository.RepositoryPackage;
import io.koju.autopos.shared.domain.SharedDomainPackage;
import io.koju.autopos.shared.service.SharedServicePackage;
import io.koju.autopos.trade.domain.TradeDomainPackage;
import io.koju.autopos.trade.sale.domain.SaleDomainPackage;
import io.koju.autopos.trade.sale.repo.SaleRepoPackage;
import io.koju.autopos.trade.service.TradeServicePackage;
import io.koju.autopos.user.domain.User;
import io.koju.autopos.user.service.UserServicePackage;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        RepositoryPackage.class,
        SharedServicePackage.class,
        CatalogServicePackage.class,
        UserServicePackage.class,
        AccountingServicePackage.class,
        PartyServicePackage.class,
        TradeServicePackage.class,
        SaleRepoPackage.class})
@EnableTransactionManagement
@EntityScan(basePackageClasses = {
        AbstractEntity.class,
        DomainPackage.class,
        SharedDomainPackage.class,
        User.class,
        Item.class,
        AccountingDomainPackage.class,
        PartyDomainPackage.class,
        TradeDomainPackage.class,
        SaleDomainPackage.class})
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Inject
    private Environment env;

    @Autowired(required = false)
    private MetricRegistry metricRegistry;

    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        log.debug("Configuring Datasource");
        if (dataSourceProperties.getUrl() == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                            " cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariDataSource hikariDataSource = (HikariDataSource) DataSourceBuilder
                .create(dataSourceProperties.getClassLoader())
                .type(HikariDataSource.class)
                .driverClassName(dataSourceProperties.getDriverClassName())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .build();

        if (metricRegistry != null) {
            hikariDataSource.setMetricRegistry(metricRegistry);
        }
        return hikariDataSource;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, DataSourceProperties dataSourceProperties,
                                     LiquibaseProperties liquibaseProperties) {

        // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
        SpringLiquibase liquibase = new AsyncSpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_NO_LIQUIBASE)) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            log.debug("Configuring Liquibase");
        }

        return liquibase;
    }

}
