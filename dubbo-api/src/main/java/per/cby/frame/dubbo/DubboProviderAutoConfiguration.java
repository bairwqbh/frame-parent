package per.cby.frame.dubbo;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ServiceBean;
import per.cby.frame.dubbo.annotation.EnableDubboConfiguration;

/**
 * Dubbo供应者自动配置
 * 
 * @author chenboyang
 *
 */
@AutoConfigureAfter(DubboAutoConfiguration.class)
@Configuration("__DUBBO_PROVIDER_AUTO_CONFIGURATION__")
@ConditionalOnBean(annotation = { EnableDubboConfiguration.class, Service.class })
@ConditionalOnProperty(prefix = "spring.dubbo", name = "server", havingValue = "true")
public class DubboProviderAutoConfiguration extends DubboCommonAutoConfiguration {

    @Autowired(required = false)
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private DubboProperties properties;

    /**
     * 初始化
     * 
     * @throws Exception 异常
     */
    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Service.class);
        if (beanMap != null && beanMap.size() > 0) {
            initIdConfigMap(properties);
            for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
                initProviderBean(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 初始化供应者Bean
     * 
     * @param beanName Bean名称
     * @param bean     Bean实例
     * @throws Exception 异常
     */
    private void initProviderBean(String beanName, Object bean) throws Exception {
        Service service = applicationContext.findAnnotationOnBean(beanName, Service.class);
        ServiceBean<Object> serviceConfig = new ServiceBean<Object>(service);
        if ((service.interfaceClass() == null || service.interfaceClass() == void.class)
                && (service.interfaceName() == null || "".equals(service.interfaceName()))) {
            Class<?> clazz = bean.getClass();
            if (bean instanceof Advised) {
                clazz = ((Advised) bean).getTargetClass();
            }
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                serviceConfig.setInterface(interfaces[0]);
            }
        }
        Environment environment = applicationContext.getEnvironment();
        String application = service.application();
        serviceConfig.setApplication(
                parseApplication(application, properties, environment, beanName, "application", application));
        String module = service.module();
        serviceConfig.setModule(parseModule(module, properties, environment, beanName, "module", module));
        String[] registries = service.registry();
        serviceConfig.setRegistries(parseRegistries(registries, properties, environment, beanName, "registry"));
        String[] protocols = service.protocol();
        serviceConfig.setProtocols(parseProtocols(protocols, properties, environment, beanName, "registry"));
        String monitor = service.monitor();
        serviceConfig.setMonitor(parseMonitor(monitor, properties, environment, beanName, "monitor", monitor));
        String provider = service.provider();
        serviceConfig.setProvider(parseProvider(provider, properties, environment, beanName, "provider", provider));
        serviceConfig.setApplicationContext(applicationContext);
        serviceConfig.afterPropertiesSet();
        serviceConfig.setRef(bean);
        serviceConfig.export();
    }

    @Override
    protected String buildErrorMsg(String... errors) {
        if (errors == null || errors.length != 3) {
            return super.buildErrorMsg(errors);
        }
        return new StringBuilder().append("beanName=").append(errors[0]).append(", ").append(errors[1]).append("=")
                .append(errors[2]).append(" not found in multi configs").toString();
    }

}
