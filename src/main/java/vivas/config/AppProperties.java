package vivas.config;


import vivas.util.PackageUtil;

public interface AppProperties {
    String APPLICATION_NAME = "administration";
    String APPLICATION_SYSTEM_CODE = "BENV";
    interface DS_CONFIG {
        interface BENV_DB {
            String ENTITY_MANAGER = "benvEntityManager";
            String TRANSACTION_MANAGER = "benvTransactionManager";
            String BEAN_NAME = "benvDataSource";
            String JPA_UNIT_NAME = "BENV_DB" + "_" + "JPA";

            String ENTITIES_PACKAGE = PackageUtil.ENTITY_BASE_PACKAGE;
            String REPOSITORY_PACKAGE = PackageUtil.REPOSITORY_BASE_PACKAGE;
            String CLASS_BEAN_NAME = PackageUtil.DATASOURCE_BASE_PACKAGE + "." + BEAN_NAME;
        }

        interface YUM_DB {
            String ENTITY_MANAGER = "yumEntityManager";
            String TRANSACTION_MANAGER = "yumTransactionManager";
            String BEAN_NAME = "yumDataSource";
            String JPA_UNIT_NAME = "YUM_DB" + "_" + "JPA";

            String ENTITIES_PACKAGE = PackageUtil.ENTITY_BASE_PACKAGE_YUM;
            String REPOSITORY_PACKAGE = PackageUtil.REPOSITORY_BASE_PACKAGE_YUM;
            String CLASS_BEAN_NAME = PackageUtil.DATASOURCE_BASE_PACKAGE + "." + BEAN_NAME;
        }
    }
}
