package per.cby.frame.common.util;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;

/**
 * 线程池帮助类
 * 
 * @author chenboyang
 * @since 2019年7月9日
 *
 */
@UtilityClass
public class ThreadPoolUtil {

    /** CPU密集型系数 */
    public final double CPU_INTESIVE_COEFFICIENT = 0.3;

    /** 混合型系数 */
    public final double MIX_INTESIVE_COEFFICIENT = 0.6;

    /** IO密集型系数 */
    public final double IO_INTESIVE_COEFFICIENT = 0.9;

    /** CPU核心数 */
    public final int CORE_NUM = Runtime.getRuntime().availableProcessors();

    /** 核心线程数 */
    public static final int CORE_POOL_SIZE = cpuIntesivePoolSize();

    /** 最大线程数 */
    public static final int MAX_POOL_SIZE = ioIntesivePoolSize();

    /** 混合线程数 */
    public static final int MIX_POOL_SIZE = mixIntesivePoolSize();

    /** 线程执行器 */
    private volatile ExecutorService executorService = null;

    /** 线程调度执行器 */
    private volatile ScheduledExecutorService scheduledExecutor = null;

    /**
     * 获取线程执行器
     * 
     * @return 执行器
     */
    public ExecutorService executorService() {
        return Optional.ofNullable(executorService)
                .orElseGet(() -> executorService = new ThreadPoolExecutor(cpuIntesivePoolSize(), ioIntesivePoolSize(),
                        1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(mixIntesivePoolSize()),
                        new CallerRunsPolicy()));
    }

    /**
     * 获取执行器
     * 
     * @return 执行器
     */
    public ScheduledExecutorService scheduledExecutor() {
        return Optional.ofNullable(scheduledExecutor)
                .orElseGet(() -> scheduledExecutor = Executors.newSingleThreadScheduledExecutor());
    }

    /**
     * 每个任务在大部分的时间内都会进行计算。 也就是CPU密集池
     * 
     * @return CPU密集型线程池大小
     */
    public int cpuIntesivePoolSize() {
        return poolSize(CPU_INTESIVE_COEFFICIENT);
    }

    /**
     * 有的任务会阻塞，有的任务则完全在计算。 也就是混合池
     * 
     * @return 混合型线程池大小
     */
    public int mixIntesivePoolSize() {
        return poolSize(MIX_INTESIVE_COEFFICIENT);
    }

    /**
     * 每个任务在90％的时间内都会阻塞，并且只能在其生命周期的10％内完成。 也就是I/O密集池
     * 
     * @return IO密集型线程池大小
     */
    public int ioIntesivePoolSize() {
        return poolSize(IO_INTESIVE_COEFFICIENT);
    }

    /**
     * 线程数=阻塞系数介于0和1之间的可用内核数/(1-阻塞系数)。计算密集型任务的阻塞系数为0，而IO密集型任务的值接近1， 所以我们不必担心价值达到1。
     * 
     * @param coefficient 系数
     * @return 线程池大小
     */
    public int poolSize(double coefficient) {
        return (int) (CORE_NUM / (1 - coefficient));
    }

    /**
     * 立即执行程序
     * 
     * @param command 程序
     */
    public void execute(Runnable command) {
        executorService().execute(command);
    }

    /**
     * 提交执行任务
     * 
     * @param <T>  数据类型
     * @param task 任务
     * @return 执行结果
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executorService().submit(task);
    }

    /**
     * 执行监听
     * 
     * @param command      监听内容
     * @param initialDelay 执行延迟时间
     * @param period       执行间隔时间
     * @param unit         时间单位
     */
    public void schedule(Runnable command, long initialDelay, long period, TimeUnit unit) {
        scheduledExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    /**
     * 增加延时执行程序
     * 
     * @param command 程序内容
     * @param delay   延时时间
     * @param unit    时间单位
     */
    public void schedule(Runnable command, long delay, TimeUnit unit) {
        scheduledExecutor().schedule(command, delay, unit);
    }

}
