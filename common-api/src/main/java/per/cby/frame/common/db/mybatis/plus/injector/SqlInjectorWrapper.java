package per.cby.frame.common.db.mybatis.plus.injector;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

/**
 * <p>
 * 自定义SQL注入器
 * </p>
 *
 * @author chenboyang
 * @since 2018-11-14
 */
public class SqlInjectorWrapper extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList() {
        return super.getMethodList().stream().map(MethodWrapper::new).collect(Collectors.toList());
    }

}
