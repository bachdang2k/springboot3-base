package vivas.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vivas.config.AppProperties;
import vivas.config.properties.database.BenvDbProperties;


import javax.sql.DataSource;

@Configuration(AppProperties.DS_CONFIG.BENV_DB.CLASS_BEAN_NAME)
@EnableJpaRepositories(basePackages = AppProperties.DS_CONFIG.BENV_DB.REPOSITORY_PACKAGE,
        entityManagerFactoryRef = AppProperties.DS_CONFIG.BENV_DB.ENTITY_MANAGER,
        transactionManagerRef = AppProperties.DS_CONFIG.BENV_DB.TRANSACTION_MANAGER)
@EnableTransactionManagement
public class AdministrationDatasource {

    private final BenvDbProperties benvDbProperties;

    @Autowired
    public AdministrationDatasource(BenvDbProperties benvDbProperties) {
        this.benvDbProperties = benvDbProperties;
    }

    @Bean(name = AppProperties.DS_CONFIG.BENV_DB.BEAN_NAME)
    @Primary
    public DataSource dataSource() {
        System.out.println("urla" + benvDbProperties.getHikariMaximumPoolSize());

        return new HikariDataSource(DataSourceUtil.initDatasource(benvDbProperties.getDriverClassName(),
                benvDbProperties.getUrl(),
                benvDbProperties.getUsername(),
                benvDbProperties.getPassword(),
                benvDbProperties.getHikariMaximumPoolSize(),
                benvDbProperties.getHikariMinimumIdle(),
                benvDbProperties.getHikariConnectionTimeout(),
                benvDbProperties.getHikariIdleTimeout(),
                benvDbProperties.getHikariPoolName()));
    }

    @Bean(name = AppProperties.DS_CONFIG.BENV_DB.ENTITY_MANAGER)
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(AppProperties.DS_CONFIG.BENV_DB.ENTITIES_PACKAGE);
        em.setPersistenceUnitName(AppProperties.DS_CONFIG.BENV_DB.JPA_UNIT_NAME);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        System.out.println("benvDbProperties.getHibernateDialect()" + benvDbProperties.getHibernateDialect());
        em.setJpaPropertyMap(DataSourceUtil.initEntityProperties(benvDbProperties.getHibernateDialect(),
                benvDbProperties.getHibernateDdlAuto(),
                benvDbProperties.isHibernateShowSql(),
                benvDbProperties.isHibernateFormatSql(),
                benvDbProperties.getHibernateBatchSize()));
       // em.afterPropertiesSet();
        return em;
    }

    @Bean(name = AppProperties.DS_CONFIG.BENV_DB.TRANSACTION_MANAGER)
    @Primary
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

}