package per.cby.frame.common.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.interfaces.Join;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.model.Page;

/**
 * 基础服务接口抽象类
 * 
 * @author chenboyang
 *
 * @param <M> 业务Mapper类型
 * @param <T> 业务对象类型
 */
public abstract class AbstractService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public List<T> list(Map<String, Object> param) {
        return valueQueryResult(list(queryWrapper(param)));
    }

    @Override
    public Page<T> page(Page<T> page, Map<String, Object> param) {
        page = page(page, queryWrapper(param));
        page.setList(valueQueryResult(page.getList()));
        return page;
    }

    @Override
    public T find(Map<String, Object> param) {
        return getOne(queryWrapper(param));
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        if (queryWrapper instanceof Join) {
            Join<?> join = (Join<?>) queryWrapper;
            switch (baseMapper.getDBType()) {
                case MYSQL:
                case OTHER:
                default:
                    join.last("LIMIT 1");
                    break;
            }
        }
        return super.getOne(queryWrapper);
    }

    @Override
    public boolean checkExist(SFunction<T, ?> column, Object value) {
        return checkExist(new LambdaQueryWrapper<T>().eq(column, value));
    }

    @Override
    public boolean checkExist(Wrapper<T> wrapper) {
        return SqlHelper.retBool(count(wrapper));
    }

    @Override
    public void valiExist(SFunction<T, ?> column, Object value, String... message) {
        valiExist(new LambdaQueryWrapper<T>().eq(column, value), message);
    }

    @Override
    public void valiExist(Wrapper<T> wrapper, String... message) {
        String text = ArrayUtils.isNotEmpty(message) ? String.join(",", message) : "对象已存在!";
        BusinessAssert.isTrue(!checkExist(wrapper), text);
    }

    /**
     * list和page查询方法通用参数封装方法
     * 
     * @param param 参数
     * @return 参数封装
     */
    protected Wrapper<T> queryWrapper(Map<String, Object> param) {
        return Wrappers.lambdaQuery();
    }

    /**
     * list和page查询方法通用结果处理方法
     * 
     * @param list 结果
     * @return 处理后的结果
     */
    protected List<T> valueQueryResult(List<T> list) {
        return list;
    }

}
