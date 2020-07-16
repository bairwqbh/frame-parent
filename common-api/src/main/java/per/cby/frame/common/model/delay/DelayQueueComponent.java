package per.cby.frame.common.model.delay;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.annotation.PreDestroy;

import per.cby.frame.common.util.ThreadPoolUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 延时队列组件
 * 
 * @author chenboyang
 *
 * @param <T> 业务数据类型
 */
@Slf4j
public class DelayQueueComponent<T> {

    /** 延时队列 */
    private DelayQueue<DelayItem<T>> delayQueue = new DelayQueue<DelayItem<T>>();

    /** 数据消费者 */
    @Setter
    private volatile Consumer<T> consumer;

    /** 是否运行 */
    private volatile boolean isRun = true;

    /**
     * 构建延时队列组件
     */
    public DelayQueueComponent() {
        listener();
    }

    /**
     * 构建延时队列组件
     * 
     * @param consumer 数据消费者
     */
    public DelayQueueComponent(Consumer<T> consumer) {
        this.consumer = consumer;
        listener();
    }

    /**
     * 推送入队列
     * 
     * @param data 数据
     * @return 推送结果
     */
    public boolean offer(T data) {
        return delayQueue.offer(new DelayItem<T>(data));
    }

    /**
     * 推送入队列
     * 
     * @param data   数据
     * @param expire 到期时间
     * @return 推送结果
     */
    public boolean offer(T data, long expire) {
        return delayQueue.offer(new DelayItem<T>(data, expire));
    }

    /**
     * 推送入队列
     * 
     * @param data     数据
     * @param expire   到期时间
     * @param timeUnit 时间单位
     * @return 推送结果
     */
    public boolean offer(T data, long expire, TimeUnit timeUnit) {
        return delayQueue.offer(new DelayItem<T>(data, expire, timeUnit));
    }

    /**
     * 执行监听
     */
    private void listener() {
        ThreadPoolUtil.execute(() -> {
            try {
                while (isRun) {
                    DelayItem<T> delayItem = delayQueue.take();
                    if (consumer != null) {
                        ThreadPoolUtil.execute(() -> consumer.accept(delayItem.getData()));
                    }
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        delayQueue = null;
        consumer = null;
        isRun = false;
    }

    /**
     * 延时项目
     * 
     * @author chenboyang
     *
     * @param <T> 数据类型
     */
    static class DelayItem<T> implements Delayed {

        /** 数据 */
        @Getter
        private T data;

        /** 到期时间 */
        @Getter
        private long expire;

        /**
         * 构建延时项目
         * 
         * @param data 数据
         */
        public DelayItem(T data) {
            this(data, 0);
        }

        /**
         * 构建延时项目
         * 
         * @param data   数据
         * @param expire 到期时间
         */
        public DelayItem(T data, long expire) {
            this(data, expire, TimeUnit.MILLISECONDS);
        }

        /**
         * 构建延时项目
         * 
         * @param data     数据
         * @param expire   到期时间
         * @param timeUnit 时间单位
         */
        public DelayItem(T data, long expire, TimeUnit timeUnit) {
            this.data = data;
            this.expire = TimeUnit.MILLISECONDS.convert(expire, timeUnit) + System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

    }

}
