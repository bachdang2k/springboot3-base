package vivas.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;

public class DataSourceUtil {
    public static HikariDataSource initDatasource(String driverClassName,
                                                  String url,
                                                  String username,
                                                  String password,
                                                  int maxPolSize,
                                                  int minIdle,
                                                  int connectionTimeout,
                                                  int idleTimeout,
                                                  String poolName) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
       // config.setMaximumPoolSize(maxPolSize);
        config.setMinimumIdle(minIdle);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setPoolName(poolName);
        return new HikariDataSource(config);
    }

    public static HashMap<String, Object> initEntityProperties(String hibernateDialect,
                                                               String hibernateDdlAuto,
                                                               boolean hibernateShowSql,
                                                               boolean hibernateFormatSql,
                                                               int batchSize) {
        HashMap<String, Object> properties = new HashMap<>();

        // JPA & Hibernate
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.ddl-auto", hibernateDdlAuto);
        properties.put("hibernate.show-sql", hibernateShowSql);
        properties.put("spring.jpa.properties.hibernate.format_sql", hibernateFormatSql);
        properties.put("hibernate.jdbc.batch_size", batchSize);
        properties.put("spring.jpa.open-in-view", false);
        return properties;
    }
}
