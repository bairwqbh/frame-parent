package per.cby.frame.task.executor;

import java.time.LocalDateTime;

/**
 * 时间任务执行器
 * 
 * @author chenboyang
 * @since 2019年11月7日
 *
 */
@FunctionalInterface
public interface TimeTaskExecutor {

	/**
	 * 执行任务
	 * 
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 */
	void execute(LocalDateTime startTime, LocalDateTime endTime);

}
