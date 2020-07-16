package per.cby.frame.dubbo;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import per.cby.frame.dubbo.annotation.EnableDubboConfiguration;
import per.cby.frame.dubbo.health.DubboHealthIndicator;
import per.cby.frame.dubbo.server.DubboServer;

/**
 * Dubbo自动配置
 * 
 * @author chenboyang
 *
 */
@Configuration("__DUBBO_AUTO_CONFIGURATION__")
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
public class DubboAutoConfiguration {

    /**
     * 获取Dubbo配置属性
     * 
     * @return Dubbo配置属性
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.dubbo")
    public DubboProperties dubboProperties() {
        return new DubboProperties();
    }

    /**
     * Dubbo服务器
     * 
     * @return Dubbo服务器
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.dubbo", name = { "server", "alive" }, havingValue = "true")
    public DubboServer dubboServer() {
        final DubboServer dubboServer = new DubboServer();
        final CountDownLatch latch = new CountDownLatch(1);
        Thread awaitThread = new Thread("dubboServer") {
            @Override
            public void run() {
                latch.countDown();
                dubboServer.await();
            }
        };
        awaitThread.setContextClassLoader(getClass().getClassLoader());
        awaitThread.setDaemon(false);
        awaitThread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return dubboServer;
    }

    /**
     * Dubbo健康状况指示器
     * 
     * @return Dubbo健康状况指示器
     */
    @Bean
    @ConditionalOnMissingBean
    public DubboHealthIndicator dubboHealthIndicator() {
        return new DubboHealthIndicator();
    }

}
