package vivas.config.properties.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

    @EqualsAndHashCode(callSuper = true)
    @Configuration
    @EnableConfigurationProperties
    @ConfigurationProperties(prefix = "spring.datasource.benv")
    @Data
    //@RefreshScope
    public class BenvDbProperties extends OracleDbConfigProperties {

    }