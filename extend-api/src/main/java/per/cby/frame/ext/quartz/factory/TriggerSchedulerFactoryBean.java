package per.cby.frame.ext.quartz.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import per.cby.frame.ext.quartz.annotation.TriggerMethod;
import per.cby.frame.ext.quartz.annotation.TriggerTarget;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务调度工厂
 * 
 * @author chenboyang
 *
 */
@Slf4j
public class TriggerSchedulerFactoryBean extends SchedulerFactoryBean {

    /** Spring上下文 */
    private ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void registerJobsAndTriggers() throws SchedulerException {
        try {
            // 获取所有Spring上下文中的所有bean名称
            String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
            for (String beanName : beanNames) {
                Class<?> targetClass = applicationContext.getType(beanName);
                // 判断是否标记了TriggerTarget注解的Bean
                if (targetClass.isAnnotationPresent(TriggerTarget.class)) {
                    Object targetObject = applicationContext.getBean(beanName);
                    Method[] methods = targetClass.getDeclaredMethods();
                    for (Method method : methods) {
                        // 判断是否标记了TriggerMethod注解的方法
                        if (method.isAnnotationPresent(TriggerMethod.class)) {
                            String targetMethod = method.getName();
                            TriggerMethod triggerMethod = method.getAnnotation(TriggerMethod.class);
                            String cron = triggerMethod.cron();
                            // 注册定时任务的调度工作
                            registerJobs(targetObject, targetMethod, beanName, cron);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 注册定时任务的调度工作
     * 
     * @param targetObject   目标对象
     * @param targetMethod   目标方法
     * @param beanName       对象名称
     * @param cronExpression cron表达式
     * @throws Exception 异常
     */
    private void registerJobs(Object targetObject, String targetMethod, String beanName, String cronExpression)
            throws Exception {

        // 声明工作执行业务
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetObject(targetObject);
        jobDetailFactoryBean.setTargetMethod(targetMethod);
        jobDetailFactoryBean.setBeanName(beanName + "_" + targetMethod + "_Task");
        jobDetailFactoryBean.setName(beanName + "_" + targetMethod + "_Task");
        jobDetailFactoryBean.setConcurrent(false);
        jobDetailFactoryBean.afterPropertiesSet();
        JobDetail jobDetail = jobDetailFactoryBean.getObject();

        // 声明定时触发器
        CronTriggerFactoryBean cronTriggerBean = new CronTriggerFactoryBean();
        cronTriggerBean.setJobDetail(jobDetail);
        cronTriggerBean.setCronExpression(cronExpression);
        cronTriggerBean.setName(beanName + "_" + targetMethod + "_Trigger");
        cronTriggerBean.setBeanName(beanName + "_" + targetMethod + "_Trigger");
        cronTriggerBean.afterPropertiesSet();
        CronTrigger trigger = cronTriggerBean.getObject();

        // 将定时器注册到调度工厂中
        List<Trigger> triggerList = new ArrayList<Trigger>();
        triggerList.add(trigger);
        Trigger[] triggers = (Trigger[]) triggerList.toArray(new Trigger[triggerList.size()]);
        setTriggers(triggers);
        super.registerJobsAndTriggers();
    }

}
