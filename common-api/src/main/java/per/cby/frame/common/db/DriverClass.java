package per.cby.frame.common.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据库驱动类
 * 
 * @author chenboyang
 * @since 2020年7月13日
 *
 */
@Getter
@RequiredArgsConstructor
public enum DriverClass {

    MYSQL("com.mysql.jdbc.Driver"),
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    POSTGRESQL("org.postgresql.Driver"),
    SQLSERVER("com.microsoft.jdbc.sqlserver.SQLServerDriver");

    private final String classPath;

}
