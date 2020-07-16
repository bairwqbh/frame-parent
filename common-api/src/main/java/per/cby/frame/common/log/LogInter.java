package per.cby.frame.common.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JAVA程序方法调用日志信息注解
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInter {

    /**
     * 操作信息
     * 
     * @return 操作信息
     */
    String value();

}
