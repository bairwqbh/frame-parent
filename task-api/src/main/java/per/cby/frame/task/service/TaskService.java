package per.cby.frame.task.service;

import per.cby.frame.task.executor.TimeTaskExecutor;
import per.cby.frame.task.model.Task;

/**
 * 任务信息服务
 * 
 * @author chenboyang
 * @since 2019年11月7日
 *
 */
public interface TaskService {

    /**
     * 获取任务调度信息
     * 
     * @param name 任务名称
     * @return 任务调度信息
     */
    Task queryByName(String name);

    /**
     * 保存任务调度信息
     * 
     * @param task 任务调度信息
     * @return 操作记录数
     */
    void save(Task task);

    /**
     * 获取任务调度信息，如果没有就会填充默认的
     * 
     * @param name 任务名称
     * @return 任务调度信息
     */
    Task getDefault(String name);

    /**
     * 获取默认的任务调度信息
     * 
     * @param name 任务名称
     * @param task 任务调度信息
     * @return 任务调度信息
     */
    Task setDefault(String name, Task task);

    /**
     * 设置任务截止
     * 
     * @param task 任务调度信息
     */
    void setDeadline(Task task);

    /**
     * 执行时间任务，无监听器的情况下才能使用
     * 
     * @param name     任务名称
     * @param executor 时间任务执行器
     */
    void execute(String name, TimeTaskExecutor executor);

}
