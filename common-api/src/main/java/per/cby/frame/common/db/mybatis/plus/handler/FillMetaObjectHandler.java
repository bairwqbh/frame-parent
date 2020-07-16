package per.cby.frame.common.db.mybatis.plus.handler;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import per.cby.frame.common.db.mybatis.util.FillDefaultHelper;

/**
 * 元对象字段自动填充处理器
 * 
 * @author chenboyang
 *
 */
public class FillMetaObjectHandler implements MetaObjectHandler, FillDefaultHelper {

    @Override
    public void insertFill(MetaObject metaObject) {
        fill(metaObject.getOriginalObject());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fill(metaObject.getOriginalObject());
    }

}
