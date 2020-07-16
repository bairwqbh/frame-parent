package per.cby.frame.common.model.time;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.common.util.DateUtil;
import per.cby.frame.common.util.ThreadPoolUtil;

/**
 * 时间列表容器实现类
 * 
 * @author chenboyang
 *
 * @param <E> 业务元素类型
 */
public class TimeArrayList<E> extends TimeContainer implements TimeList<E>, Cloneable, Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 时间元素存储容器 */
    private List<TimeElement<E>> container;

    /**
     * 时间列表容器构建方法
     */
    public TimeArrayList() {
        this(0L);
    }

    /**
     * 时间列表容器构建方法
     * 
     * @param timeout 时间元素默认过期时间
     */
    public TimeArrayList(long timeout) {
        this(100L, timeout);
    }

    /**
     * 时间列表容器构建方法
     * 
     * @param interval 监听器执行间隔时间
     * @param timeout  时间元素默认过期时间
     */
    public TimeArrayList(long interval, long timeout) {
        this(interval, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 时间列表容器构建方法
     * 
     * @param interval 监听器执行间隔时间
     * @param timeout  时间元素默认过期时间
     * @param timeUnit 监听器执行时间单位和时间元素默认时间单位
     */
    public TimeArrayList(long interval, long timeout, TimeUnit timeUnit) {
        this(0L, interval, timeout, timeUnit);
    }

    /**
     * 时间列表容器构建方法
     * 
     * @param delay    监听器延迟执行时间
     * @param interval 监听器执行间隔时间
     * @param timeout  时间元素默认过期时间
     * @param timeUnit 监听器执行时间单位和时间元素默认时间单位
     */
    public TimeArrayList(long delay, long interval, long timeout, TimeUnit timeUnit) {
        this.delay = delay;
        this.interval = interval;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.container = new CopyOnWriteArrayList<TimeElement<E>>();
        this.startListener();
    }

    @Override
    public List<TimeElement<E>> container() {
        return container;
    }

    @Override
    protected void startListener() {
        List<TimeElement<E>> list = container();
        ThreadPoolUtil.schedule(() -> {
            if (CollectionUtils.isNotEmpty(list)) {
                list.forEach((timeElement) -> {
                    long currTime = DateUtil.currentTime(timeElement.getTimeUnit());
                    long elementTimeout = timeElement.getTimeout();
                    long realtime = currTime - timeElement.getStartTime();
                    if (elementTimeout > 0 && realtime > elementTimeout) {
                        list.remove(timeElement);
                    }
                });
            }
        }, delay(), interval(), timeUnit());
    }

}
