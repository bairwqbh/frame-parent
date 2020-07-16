package per.cby.frame.dubbo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Dubbo配置属性
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class DubboProperties {

    /** 是否启用Dubbo服务器 */
    private boolean server;

    /** 是否启用Dubbo服务器 */
    private boolean client;

    /** 是否独立存活 */
    private boolean alive;

    /** 应用程序配置 */
    private ApplicationConfig application;

    /** 模块配置 */
    private ModuleConfig module;

    /** 注册配置 */
    private RegistryConfig registry;

    /** 协议配置 */
    private ProtocolConfig protocol;

    /** 监控器配置 */
    private MonitorConfig monitor;

    /** 供应者配置 */
    private ProviderConfig provider;

    /** 消费者配置 */
    private ConsumerConfig consumer;

    /** 应用程序配置集合 */
    private Map<String, ApplicationConfig> applications = new LinkedHashMap<String, ApplicationConfig>();

    /** 模块配置集合 */
    private Map<String, ModuleConfig> modules = new LinkedHashMap<String, ModuleConfig>();

    /** 注册配置集合 */
    private Map<String, RegistryConfig> registries = new LinkedHashMap<String, RegistryConfig>();

    /** 协议配置集合 */
    private Map<String, ProtocolConfig> protocols = new LinkedHashMap<String, ProtocolConfig>();

    /** 监控配置集合 */
    private Map<String, MonitorConfig> monitors = new LinkedHashMap<String, MonitorConfig>();

    /** 供应者配置集合 */
    private Map<String, ProviderConfig> providers = new LinkedHashMap<String, ProviderConfig>();

    /** 消费配置集合 */
    private Map<String, ConsumerConfig> consumers = new LinkedHashMap<String, ConsumerConfig>();

}
