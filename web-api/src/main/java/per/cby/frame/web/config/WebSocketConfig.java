package per.cby.frame.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import per.cby.frame.web.websocket.WebSocket;

/**
 * WebSocket配置类
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@ConditionalOnBean(WebSocket.class)
@Configuration("__WEB_SOCKET_CONFIG__")
@ConditionalOnClass(name = "org.springframework.web.socket.server.standard.ServerEndpointExporter")
public class WebSocketConfig {

    /**
     * WebSocket服务注册器
     * 
     * @return 服务注册器
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
