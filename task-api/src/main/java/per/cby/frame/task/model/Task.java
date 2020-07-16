package per.cby.frame.task.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 任务调度信息
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class Task {

    /** 主键 */
    private String id;

    /** 任务名称 */
    private String name;

    /** 开始时间 */
    private LocalDateTime startTime;

    /** 结束时间 */
    private LocalDateTime endTime;

}
