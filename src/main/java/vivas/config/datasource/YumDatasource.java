package vivas.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vivas.config.AppProperties;
import vivas.config.properties.database.YumDbProperties;


import javax.sql.DataSource;

@Configuration(AppProperties.DS_CONFIG.YUM_DB.CLASS_BEAN_NAME)
@EnableJpaRepositories(basePackages = AppProperties.DS_CONFIG.YUM_DB.REPOSITORY_PACKAGE,
        entityManagerFactoryRef = AppProperties.DS_CONFIG.YUM_DB.ENTITY_MANAGER,
        transactionManagerRef = AppProperties.DS_CONFIG.YUM_DB.TRANSACTION_MANAGER)
@EnableTransactionManagement
@AllArgsConstructor
public class YumDatasource {
    private final YumDbProperties yumDbProperties;
    @Bean(name = AppProperties.DS_CONFIG.YUM_DB.BEAN_NAME)
    public DataSource dataSource() {

        return new HikariDataSource(DataSourceUtil.initDatasource(yumDbProperties.getDriverClassName(),
                yumDbProperties.getUrl(),
                yumDbProperties.getUsername(),
                yumDbProperties.getPassword(),
                yumDbProperties.getHikariMaximumPoolSize(),
                yumDbProperties.getHikariMinimumIdle(),
                yumDbProperties.getHikariConnectionTimeout(),
                yumDbProperties.getHikariIdleTimeout(),
                yumDbProperties.getHikariPoolName()));
    }
    @Bean(name = AppProperties.DS_CONFIG.YUM_DB.ENTITY_MANAGER)
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(AppProperties.DS_CONFIG.YUM_DB.ENTITIES_PACKAGE);
        em.setPersistenceUnitName(AppProperties.DS_CONFIG.YUM_DB.JPA_UNIT_NAME);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(DataSourceUtil.initEntityProperties(yumDbProperties.getHibernateDialect(),
                yumDbProperties.getHibernateDdlAuto(),
                yumDbProperties.isHibernateShowSql(),
                yumDbProperties.isHibernateFormatSql(),
                yumDbProperties.getHibernateBatchSize()));
        em.afterPropertiesSet();
        return em;
    }

    @Bean(name = AppProperties.DS_CONFIG.YUM_DB.TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }
}
