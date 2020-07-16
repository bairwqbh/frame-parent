package per.cby.frame.common.db.mybatis.plus.injector;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.MappedStatement;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.db.mybatis.plus.metadata.TableInfoExt;
import per.cby.frame.common.util.ReflectUtil;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 自动注入方法包装器
 * </p>
 *
 * @author chenboyang
 * @since 2018-11-14
 */
@RequiredArgsConstructor
public class MethodWrapper extends AbstractMethod {

    /** 依赖方法 */
    protected final AbstractMethod method;

    @Override
    public void inject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        this.configuration = builderAssistant.getConfiguration();
        this.builderAssistant = builderAssistant;
        this.languageDriver = configuration.getDefaultScriptingLanguageInstance();
        if (modelClass != null) {
            TableInfoExt tableInfoExt = new TableInfoExt(tableInfo);
            injectMappedStatement(mapperClass, modelClass, tableInfoExt);
        }
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        BusinessAssert.notNull(method, "依赖方法对象为空！");
        ReflectUtil.copy(this, method);
        return method.injectMappedStatement(mapperClass, modelClass, tableInfo);
    }

}
