package per.cby.frame.task.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import per.cby.frame.common.constant.DefaultValue;
import per.cby.frame.mongo.storage.MongoDBStorage;
import per.cby.frame.task.constant.Constant;
import per.cby.frame.task.executor.TimeTaskExecutor;
import per.cby.frame.task.model.Task;
import per.cby.frame.task.service.TaskService;

/**
 * 任务信息服务接口实现
 * 
 * @author chenboyang
 * @since 2019年11月7日
 *
 */
public class TaskServiceImpl implements TaskService, Constant {

    @Qualifier(TASK_MONGO_BEAN)
    @Autowired(required = false)
    private MongoDBStorage<Task> taskMongo;

    @Override
    public Task queryByName(String name) {
        return taskMongo.findOne(Query.query(Criteria.where("name").is(name)));
    }

    @Override
    public void save(Task task) {
        taskMongo.save(task);
    }

    @Override
    public Task getDefault(String name) {
        return setDefault(name, queryByName(name));
    }

    @Override
    public Task setDefault(String name, Task task) {
        if (task == null) {
            task = new Task();
            task.setName(name);
        }
        if (task.getStartTime() == null) {
            task.setStartTime(DefaultValue.FIRST_DATE_TIME);
        }
        return task;
    }

    @Override
    public void setDeadline(Task task) {
        task.setStartTime(task.getEndTime());
        task.setEndTime(null);
        save(task);
    }

    @Override
    public void execute(String name, TimeTaskExecutor executor) {
        Task task = getDefault(name);
        if (task.getEndTime() == null) {
            task.setEndTime(LocalDateTime.now());
        }
        executor.execute(task.getStartTime(), task.getEndTime());
        setDeadline(task);
    }

}
