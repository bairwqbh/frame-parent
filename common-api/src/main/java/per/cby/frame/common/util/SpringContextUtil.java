package per.cby.frame.common.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <h3>Spring辅助类</h3>
 * <p>
 * 主要用于操作SpringBean容器内的各种Bean
 * </p>
 * 
 * @author chenboyang
 */
@Slf4j
@SuppressWarnings("unchecked")
@Component("__SPRING_CONTEXT_UTIL__")
public final class SpringContextUtil implements ApplicationContextAware {

    /** Spring上下文对象 */
    private static ApplicationContext applicationContext = null;

    /**
     * 获取Spring上下文对象
     * 
     * @return Spring上下文对象
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 设置Spring上下文对象
     * 
     * @param applicationContext Spring上下文对象
     */
    public static void setContext(ApplicationContext applicationContext) {
        if (applicationContext != null && SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 根据Bean的类型获取SpringBean容器内的Bean对象
     * 
     * @param clazz Bean的类型
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            if (applicationContext != null) {
                return applicationContext.getBean(clazz);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据Bean的名称获取SpringBean容器内的Bean对象
     * 
     * @param name 名称
     * @return Bean对象
     */
    public static <T> T getBean(String name) {
        try {
            if (applicationContext != null) {
                return (T) applicationContext.getBean(name);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据Bean的名称和类型获取SpringBean容器内的Bean对象
     * 
     * @param name  名称
     * @param clazz Bean的类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        try {
            if (applicationContext != null) {
                return applicationContext.getBean(name, clazz);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据Bean的类型获取SpringBean容器内的Bean对象Map
     * 
     * @param clazz Bean的类型
     * @return Bean对象Map
     */
    public static <T> Map<String, T> getBeanMap(Class<T> clazz) {
        try {
            return applicationContext.getBeansOfType(clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据Bean的注解类型获取SpringBean容器内的Bean对象Map
     * 
     * @param clazz Bean的类型
     * @return Bean对象Map
     */
    public static Map<String, Object> getBeanMapByAnnotation(Class<? extends Annotation> annotationType) {
        try {
            return applicationContext.getBeansWithAnnotation(annotationType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 初始化Spring上下文
     * 
     * @param paths Spring配置文件路径
     * @return Spring上下文
     */
    @Deprecated
    public static ClassPathXmlApplicationContext initContext(String... paths) {
        if (ArrayUtils.isEmpty(paths)) {
            paths = new String[] { "classpath*:applicationContext.xml" };
        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(paths);
        applicationContext = context;
        return context;
    }

    /**
     * 是否为初次加载上下文
     * 
     * @param event 加载上下文事件
     * @return 是否为初次
     */
    public static boolean isFirst(ApplicationContextEvent event) {
        return event.getApplicationContext().getParent() == null;
    }

    /**
     * 注册Bean到Spring上下文中
     * 
     * @param <T>   Bean类型
     * @param name  Bean名称
     * @param clazz Bean类信息
     * @param args  Bean构造参数
     * @return Bean实例
     */
    public static <T> T registerBean(String name, Class<T> clazz, Object... args) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        if (args.length > 0) {
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);
        return applicationContext.getBean(name, clazz);
    }

}
