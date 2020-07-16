package per.cby.frame.dubbo.server;

import org.springframework.beans.factory.DisposableBean;

import lombok.extern.slf4j.Slf4j;

/**
 * Dubbo服务器
 * 
 * @author chenboyang
 *
 */
@Slf4j
public final class DubboServer implements DisposableBean {

    /** 是否已停止等待 */
    private volatile boolean stopAwait = false;

    /** 等待线程 */
    private volatile Thread awaitThread = null;

    /**
     * 等待
     */
    public void await() {
        try {
            awaitThread = Thread.currentThread();
            while (!stopAwait) {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        } finally {
            awaitThread = null;
        }
    }

    /**
     * 停止等待
     */
    public void stopAwait() {
        stopAwait = true;
    }

    @Override
    public void destroy() throws Exception {
        stopAwait();
        Thread t = awaitThread;
        if (t != null) {
            try {
                t.interrupt();
                t.join(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
