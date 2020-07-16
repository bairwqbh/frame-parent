package per.cby.frame.common.db.mybatis.plus.injector;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;

/**
 * <p>
 * 自定义SQL逻辑删除注入器
 * </p>
 *
 * @author chenboyang
 * @since 2018-11-06
 */
public class LogicSqlInjectorWrapper extends LogicSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList() {
        return super.getMethodList().stream().map(LogicMethodWrapper::new).collect(Collectors.toList());
    }

}
