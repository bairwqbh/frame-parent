package per.cby.frame.common.db.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * 几何类型字段
 * 
 * @author chenboyang
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeometryField {

    /**
     * 数据库类型
     * 
     * @return 数据库类型
     */
    DbType dbType() default DbType.MYSQL;

}
