package per.cby.frame.task.mongo;

import per.cby.frame.mongo.annotation.MongoStorage;
import per.cby.frame.mongo.storage.MongoDBStorage;
import per.cby.frame.task.model.Task;

/**
 * 任务调度信息Mongo存储
 * 
 * @author chenboyang
 * @since 2019年11月4日
 *
 */
@MongoStorage(name = "task.schedule.info")
public class TaskMongo implements MongoDBStorage<Task> {

}
