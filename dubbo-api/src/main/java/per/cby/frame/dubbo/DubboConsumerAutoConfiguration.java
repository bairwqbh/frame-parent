package per.cby.frame.dubbo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import per.cby.frame.dubbo.annotation.EnableDubboConfiguration;
import per.cby.frame.dubbo.bean.ClassIdBean;

/**
 * Dubbo消费者自动配置
 * 
 * @author chenboyang
 *
 */
@AutoConfigureAfter(DubboAutoConfiguration.class)
@Configuration("__DUBBO_CONSUMER_AUTO_CONFIGURATION__")
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@ConditionalOnProperty(prefix = "spring.dubbo", name = "client", havingValue = "true")
public class DubboConsumerAutoConfiguration extends DubboCommonAutoConfiguration {

    /** Dubbo接口引用对象MAP */
    private static final Map<ClassIdBean, Object> DUBBO_REFERENCES_MAP = new ConcurrentHashMap<ClassIdBean, Object>();

    @Autowired(required = false)
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private DubboProperties properties;

    /**
     * 根据接口信息获取引用对象实例
     * 
     * @param classIdBean 接口信息
     * @return 对象实例
     */
    public static Object getDubboReference(ClassIdBean classIdBean) {
        return DUBBO_REFERENCES_MAP.get(classIdBean);
    }

    /**
     * Bean封装代理处理器
     * 
     * @return Bean处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                Class<?> objClz;
                if (AopUtils.isAopProxy(bean)) {
                    objClz = AopUtils.getTargetClass(bean);
                } else {
                    objClz = bean.getClass();
                }
                try {
                    for (Field field : objClz.getDeclaredFields()) {
                        Reference reference = field.getAnnotation(Reference.class);
                        if (reference != null) {
                            initIdConfigMap(properties);
                            ReferenceBean<?> referenceBean = getConsumerBean(beanName, field, reference);
                            Class<?> interfaceClass = referenceBean.getInterfaceClass();
                            String group = referenceBean.getGroup();
                            String version = referenceBean.getVersion();
                            ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
                            Object dubboReference = DUBBO_REFERENCES_MAP.get(classIdBean);
                            if (dubboReference == null) {
                                synchronized (this) {
                                    dubboReference = DUBBO_REFERENCES_MAP.get(classIdBean);
                                    if (dubboReference == null) {
                                        referenceBean.afterPropertiesSet();
                                        dubboReference = referenceBean.getObject();
                                        DUBBO_REFERENCES_MAP.put(classIdBean, dubboReference);
                                    }
                                }
                            }
                            field.setAccessible(true);
                            field.set(bean, dubboReference);
                        }
                    }
                } catch (Exception e) {
                    throw new BeanCreationException(beanName, e);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }

    /**
     * 获取消费者Bean
     * 
     * @param beanName  Bean名称
     * @param field     字段
     * @param reference 引用
     * @return 引用Bean
     * @throws BeansException Bean异常
     */
    private <T> ReferenceBean<T> getConsumerBean(String beanName, Field field, Reference reference)
            throws BeansException {
        ReferenceBean<T> referenceBean = new ReferenceBean<T>(reference);
        if ((reference.interfaceClass() == null || reference.interfaceClass() == void.class)
                && (reference.interfaceName() == null || "".equals(reference.interfaceName()))) {
            referenceBean.setInterface(field.getType());
        }
        Environment environment = applicationContext.getEnvironment();
        String application = reference.application();
        referenceBean.setApplication(parseApplication(application, properties, environment, beanName, field.getName(),
                "application", application));
        String module = reference.module();
        referenceBean
                .setModule(parseModule(module, properties, environment, beanName, field.getName(), "module", module));
        String[] registries = reference.registry();
        referenceBean.setRegistries(
                parseRegistries(registries, properties, environment, beanName, field.getName(), "registry"));
        String monitor = reference.monitor();
        referenceBean.setMonitor(
                parseMonitor(monitor, properties, environment, beanName, field.getName(), "monitor", monitor));
        String consumer = reference.consumer();
        referenceBean.setConsumer(
                parseConsumer(consumer, properties, environment, beanName, field.getName(), "consumer", consumer));
        referenceBean.setApplicationContext(applicationContext);
        return referenceBean;
    }

    @Override
    protected String buildErrorMsg(String... errors) {
        if (errors == null || errors.length != 4) {
            return super.buildErrorMsg(errors);
        }
        return new StringBuilder().append("beanName=").append(errors[0]).append(", field=").append(errors[1])
                .append(", ").append(errors[2]).append("=").append(errors[3]).append(" 多项配置中找不到！").toString();
    }

}
