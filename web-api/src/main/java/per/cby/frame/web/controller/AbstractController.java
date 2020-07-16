package per.cby.frame.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import per.cby.frame.common.base.BaseModel;
import per.cby.frame.common.base.BaseService;
import per.cby.frame.common.model.Page;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 基础前端控制器抽象类
 * 
 * @author chenboyang
 *
 * @param <T> 业务对象类型
 */
public abstract class AbstractController<S extends BaseService<T>, T extends BaseModel<T>> {

    /** 基础服务接口 */
    @Autowired(required = false)
    protected S baseService;

    /**
     * 获取基础服务接口
     * 
     * @return 基础服务接口
     */
    public S baseService() {
        return baseService;
    }

    @PostMapping("/add")
    @ApiOperation("新增记录")
    public boolean add(@RequestBody @Valid T record) {
        return baseService.save(record);
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation("删除记录")
    public boolean del(@ApiParam(value = "记录编号", required = true) @PathVariable("id") String id) {
        return baseService.removeById(id);
    }

    @ApiOperation("修改记录")
    @PutMapping("/mod")
    public boolean mod(@RequestBody @Valid T record) {
        return baseService.updateById(record);
    }

    @GetMapping("/list")
    @ApiOperation("查询记录列表")
    public List<T> list(@RequestParam Map<String, Object> map) {
        return baseService.list(map);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询记录")
    public Page<T> page(Page<T> page, @RequestParam Map<String, Object> map) {
        return baseService.page(page, map);
    }

    @GetMapping("/find")
    @ApiOperation("查询单条记录")
    public T find(@RequestParam Map<String, Object> map) {
        return baseService.find(map);
    }

}
