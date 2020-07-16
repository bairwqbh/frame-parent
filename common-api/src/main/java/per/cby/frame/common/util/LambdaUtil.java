package per.cby.frame.common.util;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import lombok.experimental.UtilityClass;

/**
 * Lambda帮助类
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
public class LambdaUtil {

    /**
     * Get函数转属性名
     * 
     * @param <T>  业务类型
     * @param func 函数
     * @return 属性名
     */
    public <T> String funcToName(SFunction<T, ?> func) {
        return StringUtils.resolveFieldName(LambdaUtils.resolve(func).getImplMethodName());
    }

}
