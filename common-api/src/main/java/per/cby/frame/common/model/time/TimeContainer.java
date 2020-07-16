package per.cby.frame.common.model.time;

import java.util.concurrent.TimeUnit;

/**
 * 时间容器抽象类
 * 
 * @author chenboyang
 */
public abstract class TimeContainer {

    /** 监听器延迟执行时间 */
    protected long delay;

    /** 监听器执行间隔时间 */
    protected long interval;

    /** 时间元素默认过期时间 */
    protected long timeout;

    /** 监听器执行时间单位和时间元素默认时间单位 */
    protected TimeUnit timeUnit;

    /**
     * 获取监听器延迟执行时间
     * 
     * @return 延迟时间
     */
    public long delay() {
        return delay;
    }

    /**
     * 获取监听器执行间隔时间
     * 
     * @return 间隔时间
     */
    public long interval() {
        return interval;
    }

    /**
     * 获取时间元素默认过期时间
     * 
     * @return 过期时间
     */
    public long timeout() {
        return timeout;
    }

    /**
     * 获取监听器执行时间单位和时间元素默认时间单位
     * 
     * @return 时间单位
     */
    public TimeUnit timeUnit() {
        return timeUnit;
    }

    /**
     * 启动监听器
     */
    protected abstract void startListener();

}
