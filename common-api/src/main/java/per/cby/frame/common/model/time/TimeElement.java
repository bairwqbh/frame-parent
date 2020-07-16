package per.cby.frame.common.model.time;

import java.util.concurrent.TimeUnit;

import per.cby.frame.common.util.DateUtil;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 时间元素对象
 * 
 * @author chenboyang
 *
 * @param <E> 元素类型
 */
@Data
@Accessors(chain = true)
public class TimeElement<E> {

    /** 元素 */
    private E element;

    /** 存储开始时间 */
    private long startTime;

    /** 存储过期时间 */
    private long timeout;

    /** 时间单位 */
    private TimeUnit timeUnit;

    /**
     * 时间元素构造方法
     * 
     * @param element  业务对象
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    TimeElement(E element, long timeout, TimeUnit timeUnit) {
        this.element = element;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.startTime = DateUtil.currentTime(timeUnit);
    }

}
