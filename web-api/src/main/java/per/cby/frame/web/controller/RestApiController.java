package per.cby.frame.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import per.cby.frame.common.base.BaseModel;
import per.cby.frame.common.base.BaseService;

import io.swagger.annotations.ApiOperation;

/**
 * RESTful接口控制器
 * 
 * @author chenboyang
 *
 * @param <T> 业务类型
 */
public interface RestApiController<S extends BaseService<T>, T extends BaseModel<T>> {

    /**
     * 获取基础服务接口
     * 
     * @return 基础服务接口
     */
    S baseService();

    @GetMapping
    @ApiOperation("获取记录")
    default List<T> get(@RequestParam Map<String, Object> map) {
        return baseService().list(map);
    }

    @PostMapping
    @ApiOperation("提交记录")
    default boolean post(@RequestBody @Valid T record) {
        return baseService().save(record);
    }

    @PutMapping
    @ApiOperation("更新记录")
    default boolean put(@RequestBody @Valid T record) {
        return baseService().updateById(record);
    }

    @ApiOperation("删除记录")
    @DeleteMapping("/{id}")
    default boolean delete(@PathVariable("id") String id) {
        return baseService().removeById(id);
    }

}
