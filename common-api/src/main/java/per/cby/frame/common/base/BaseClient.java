package per.cby.frame.common.base;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import per.cby.frame.common.model.Page;
import per.cby.frame.common.result.Result;

/**
 * 服务访问基础客户端
 * 
 * @author chenboyang
 *
 */
public interface BaseClient<T> {

    /**
     * 新增记录
     * 
     * @param t 记录对象
     * @return 操作结果
     */
    @PostMapping("/add")
    Result<Boolean> add(@RequestBody T t);

    /**
     * 删除记录
     * 
     * @param id 记录编号
     * @return 操作结果
     */
    @DeleteMapping("/del/{id}")
    Result<Boolean> del(@PathVariable("id") String id);

    /**
     * 修改记录
     * 
     * @param t 记录对象
     * @return 操作结果
     */
    @PutMapping("/mod")
    Result<Boolean> mod(@RequestBody T t);

    /**
     * 查询记录列表
     * 
     * @param param 查询参数
     * @return 记录列表
     */
    @GetMapping("/list")
    Result<List<T>> list(@RequestParam Map<String, Object> param);

    /**
     * 分页查询记录
     * 
     * @param param 查询参数
     * @return 分页信息
     */
    @GetMapping("/page")
    Page<T> page(@RequestParam Map<String, Object> param);

    /**
     * 查询单条记录
     * 
     * @param param 查询参数
     * @return 单条记录
     */
    @GetMapping("/find")
    Result<T> find(@RequestParam Map<String, Object> param);

}
