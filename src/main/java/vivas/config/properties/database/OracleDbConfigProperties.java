package vivas.config.properties.database;

import lombok.Data;

@Data
public class OracleDbConfigProperties {
    //region db config
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    //endregion
    //region hikari config
    private int hikariMaximumPoolSize;
    private int hikariMinimumIdle;
    private int hikariIdleTimeout;
    private int hikariConnectionTimeout;
    private String hikariPoolName;
    //endregion
    //region jpa config
    private String hibernateDialect;
    private String hibernateDdlAuto;
    private boolean hibernateShowSql;
    private boolean hibernateFormatSql;
    private int hibernateBatchSize;
    //endregion

}