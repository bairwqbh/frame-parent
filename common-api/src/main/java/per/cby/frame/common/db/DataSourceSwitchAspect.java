package per.cby.frame.common.db;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 数据源开关切面
 * 
 * @author chenboyang
 *
 */
@Aspect
@Deprecated
public class DataSourceSwitchAspect {

    /**
     * 基础数据源切点
     */
    @Pointcut("execution(* *..business..service..*.*(..))")
    public void base() {
    }

    /**
     * 基础数据源进入之前
     */
    @Before("base()")
    public void wukongBefore() {
        DataSourceContextHolder.set(DynamicDataSource.DEFAULT);
    }

    /**
     * 数据源退出之后
     */
    @After("base()")
    public void after() {
        DataSourceContextHolder.remove();
    }

}
