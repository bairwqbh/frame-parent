package per.cby.frame.dubbo.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import com.alibaba.dubbo.rpc.service.EchoService;
import per.cby.frame.dubbo.DubboConsumerAutoConfiguration;
import per.cby.frame.dubbo.bean.ClassIdBean;
import per.cby.frame.dubbo.listener.ConsumerSubscribeListener;

/**
 * Dubbo健康状况指示器
 * 
 * @author chenboyang
 *
 */
public class DubboHealthIndicator extends AbstractHealthIndicator {

    @Override
    public void doHealthCheck(Health.Builder builder) throws Exception {
        boolean up = true;
        for (ClassIdBean classIdBean : ConsumerSubscribeListener.SUBSCRIBEDINTERFACES_SET) {
            Object service = DubboConsumerAutoConfiguration.getDubboReference(classIdBean);
            EchoService echoService = (EchoService) service;
            if (echoService != null) {
                try {
                    echoService.$echo("Hello");
                    builder.withDetail(classIdBean.toString(), Status.UP.getCode());
                } catch (Throwable t) {
                    up = false;
                    builder.withDetail(classIdBean.toString(), Status.DOWN.getCode() + ", message: " + t.getMessage());
                }
            }
        }
        if (up) {
            builder.up();
        } else {
            builder.down();
        }
    }

}
