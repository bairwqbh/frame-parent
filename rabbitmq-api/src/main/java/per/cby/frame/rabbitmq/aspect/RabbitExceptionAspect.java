package per.cby.frame.rabbitmq.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

/**
 * RabbitMQ统一异常捕获处理切面
 * 
 * @author chenboyang
 *
 */
@Slf4j
@Aspect
public class RabbitExceptionAspect {

    /** 匹配表达式 */
    private static final String EXPRESSION = "@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)";

    /**
     * 请求时的异常处理
     */
    @Around(EXPRESSION)
    public Object handle(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

}
