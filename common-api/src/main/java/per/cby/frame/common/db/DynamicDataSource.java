package per.cby.frame.common.db;

import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <h1>数据源动态代理</h1>
 * <p>
 * 动态代理数据源主要用于动态创建、设置、调用、管理数据源
 * </p>
 * 
 * @author chenboyang
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /** 默认数据源 */
    public static final String DEFAULT = "default";

    /** 数据源池 */
    private Map<Object, Object> targetDataSourcesMap;

    @Override
    protected Object determineCurrentLookupKey() {
        return Optional.ofNullable(DataSourceContextHolder.get()).orElse(DEFAULT);
    }

    /**
     * 设置数据源池
     * 
     * @param targetDataSources 数据源池
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        targetDataSourcesMap = targetDataSources;
        super.setTargetDataSources(targetDataSources);
        afterPropertiesSet();
    }

    /**
     * 新增数据源
     * 
     * @param key        数据源名称
     * @param dataSource 数据源
     */
    public void addTargetDataSource(Object key, DataSource dataSource) {
        targetDataSourcesMap.put(key, dataSource);
        setTargetDataSources(targetDataSourcesMap);
    }

    /**
     * 创建数据源
     * 
     * @param driver   驱动类
     * @param url      服务地址
     * @param username 用户名
     * @param password 密码
     * @return 数据源
     */
    public BasicDataSource createDataSource(DriverClass driver, String url, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver.getClassPath());
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * 创建数据源
     * 
     * @param dataSourceInfo 数据源连接信息
     * @return 数据源
     */
    public BasicDataSource createDataSource(DataSourceInfo dataSourceInfo) {
        return createDataSource(dataSourceInfo.getDriver(), dataSourceInfo.getUrl(), dataSourceInfo.getUsername(),
                dataSourceInfo.getPassword());
    }

    /**
     * 数据源是否存在
     * 
     * @param key 数据源名称
     * @return 是否存在
     */
    public boolean isExist(Object key) {
        return targetDataSourcesMap.containsKey(key) && targetDataSourcesMap.get(key) != null;
    }

}
