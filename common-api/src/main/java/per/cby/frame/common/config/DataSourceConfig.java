package per.cby.frame.common.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import per.cby.frame.common.db.DataSourceContextHolder;
import per.cby.frame.common.db.DynamicDataSource;
import per.cby.frame.common.db.mybatis.plus.handler.FillMetaObjectHandler;
import per.cby.frame.common.db.mybatis.plus.injector.SqlInjectorWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源配置类
 * 
 * @author chenboyang
 *
 */
@Slf4j
@Configuration("__DATA_SOURCE_CONFIG__")
@ConditionalOnClass(name = { "com.alibaba.druid.pool.DruidDataSource",
        "com.baomidou.mybatisplus.core.MybatisConfiguration" })
public class DataSourceConfig {

    /**
     * 数据源
     * 
     * @return 数据源
     */
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.datasource")
    @ConditionalOnProperty("spring.datasource.url")
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 动态数据源
     * 
     * @param dataSource 数据源
     * @return 动态数据源
     */
    @Bean
    @ConditionalOnBean(DataSource.class)
    @ConditionalOnMissingBean(DynamicDataSource.class)
    public DynamicDataSource dynamicDataSource(DataSource dataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DynamicDataSource.DEFAULT, dataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(dataSource);
        DataSourceContextHolder.set(DynamicDataSource.DEFAULT);
        return dynamicDataSource;
    }

    /**
     * 数据源事务
     * 
     * @param dynamicDataSource 动态数据源
     * @return 事务
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(DynamicDataSource.class)
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    /**
     * JDBC操作模板
     * 
     * @param dynamicDataSource 动态数据源
     * @return 操作模板
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(DynamicDataSource.class)
    public JdbcTemplate jdbcTemplate(DynamicDataSource dynamicDataSource) {
        return new JdbcTemplate(dynamicDataSource);
    }

    /**
     * MyBatis基础配置
     * 
     * @return 基础配置
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisConfiguration mybatisConfiguration() {
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setLazyLoadingEnabled(true);
        mybatisConfiguration.setAggressiveLazyLoading(true);
        mybatisConfiguration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.addInterceptor(new PaginationInterceptor());
        return mybatisConfiguration;
    }

    /**
     * MyBatis全局配置
     * 
     * @return 全局配置
     */
    @Bean
    @ConditionalOnMissingBean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        globalConfig.setBanner(false);
        globalConfig.setSqlInjector(new SqlInjectorWrapper());
        globalConfig.setMetaObjectHandler(new FillMetaObjectHandler());
        return globalConfig;
    }

    /**
     * SQL会话工厂
     * 
     * @param dynamicDataSource    动态数据源
     * @param mybatisConfiguration mybatis配置
     * @param globalConfig         全局配置
     * @return 会话工厂
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({ DynamicDataSource.class, MybatisConfiguration.class, GlobalConfig.class })
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource,
            MybatisConfiguration mybatisConfiguration, GlobalConfig globalConfig) {
        try {
            MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            mybatisSqlSessionFactoryBean.setDataSource(dynamicDataSource);
            mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
            mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);
            mybatisSqlSessionFactoryBean.setMapperLocations(
                    new PathMatchingResourcePatternResolver().getResources("classpath*:**/*Mapper.xml"));
            return mybatisSqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * MyBatis会话操作模板
     * 
     * @param sqlSessionFactory sql会话构建工厂
     * @return 操作模板
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(SqlSessionFactory.class)
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * mapper扫瞄配置
     * 
     * @return mapper扫瞄配置
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(SqlSessionFactory.class)
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("**.mapper");
        mapperScannerConfigurer.setAnnotationClass(Repository.class);
        return mapperScannerConfigurer;
    }

}
