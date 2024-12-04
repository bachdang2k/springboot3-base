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

import javax.sql.DataSource;

//@Configuration(AppProperties.DS_CONFIG.REPORT_DB.CLASS_BEAN_NAME)
//@EnableJpaRepositories(basePackages = AppProperties.DS_CONFIG.REPORT_DB.REPOSITORY_PACKAGE,
//        entityManagerFactoryRef = AppProperties.DS_CONFIG.REPORT_DB.ENTITY_MANAGER,
//        transactionManagerRef = AppProperties.DS_CONFIG.REPORT_DB.TRANSACTION_MANAGER)
//@EnableTransactionManagement
//@AllArgsConstructor
public class ReportDatasource {
//    private final ReportDbProperties reportDbProperties;
//    @Bean(name = AppProperties.DS_CONFIG.REPORT_DB.BEAN_NAME)
//    public DataSource dataSource() {
//
//        return new HikariDataSource(DataSourceUtil.initDatasource(reportDbProperties.getDriverClassName(),
//                reportDbProperties.getUrl(),
//                reportDbProperties.getUsername(),
//                reportDbProperties.getPassword(),
//                reportDbProperties.getHikariMaximumPoolSize(),
//                reportDbProperties.getHikariMinimumIdle(),
//                reportDbProperties.getHikariConnectionTimeout(),
//                reportDbProperties.getHikariIdleTimeout(),
//                reportDbProperties.getHikariPoolName()));
//    }
//    @Bean(name = AppProperties.DS_CONFIG.REPORT_DB.ENTITY_MANAGER)
//    public LocalContainerEntityManagerFactoryBean entityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(AppProperties.DS_CONFIG.REPORT_DB.ENTITIES_PACKAGE);
//        em.setPersistenceUnitName(AppProperties.DS_CONFIG.REPORT_DB.JPA_UNIT_NAME);
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaPropertyMap(DataSourceUtil.initEntityProperties(reportDbProperties.getHibernateDialect(),
//                reportDbProperties.getHibernateDdlAuto(),
//                reportDbProperties.isHibernateShowSql(),
//                reportDbProperties.isHibernateFormatSql(),
//                reportDbProperties.getHibernateBatchSize()));
//        em.afterPropertiesSet();
//        return em;
//    }
//
//    @Bean(name = AppProperties.DS_CONFIG.REPORT_DB.TRANSACTION_MANAGER)
//    public PlatformTransactionManager transactionManager() {
//
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManager().getObject());
//        return transactionManager;
//    }
}
