package per.cby.frame.common.db.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import per.cby.frame.common.db.mybatis.enums.NoType;

/**
 * 自动填充编号
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoId {

    /**
     * 编号类型
     * 
     * @return 编号类型
     */
    NoType value() default NoType.TIME;

}
