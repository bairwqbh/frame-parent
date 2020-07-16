package per.cby.frame.common.base;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import per.cby.frame.common.model.Page;
import per.cby.frame.common.db.mybatis.plus.service.MpPageAdapter;

/**
 * 基础服务接口
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
public interface BaseService<T> extends IService<T>, MpPageAdapter<T> {

    /**
     * 查询记录
     * 
     * @param param 条件
     * @return 记录列表
     */
    List<T> list(Map<String, Object> param);

    /**
     * 分页查询记录
     * 
     * @param page  分页信息
     * @param param 条件
     * @return 分页记录
     */
    Page<T> page(Page<T> page, Map<String, Object> param);

    /**
     * 查询单个记录
     * 
     * @param param 条件
     * @return 记录
     */
    T find(Map<String, Object> param);

    /**
     * 检查是否存在
     * 
     * @param column 字段
     * @param value  值
     * @return 是否存在
     */
    boolean checkExist(SFunction<T, ?> column, Object value);

    /**
     * 校验是否存在
     * 
     * @param column  字段
     * @param value   值
     * @param message 异常消息
     */
    void valiExist(SFunction<T, ?> column, Object value, String... message);

    /**
     * 检查是否存在
     * 
     * @param wrapper 条件包装器
     * @return 是否存在
     */
    boolean checkExist(Wrapper<T> wrapper);

    /**
     * 校验是否存在
     * 
     * @param wrapper 条件包装器
     * @param message 异常消息
     */
    void valiExist(Wrapper<T> wrapper, String... message);

}
