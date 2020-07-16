package per.cby.frame.dubbo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import per.cby.frame.common.result.IResult;
import per.cby.frame.common.result.ResultEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * Dubbo统一处理切面
 * 
 * @author chenboyang
 *
 */
@Slf4j
@Aspect
public class DubboAspect {

    /** 匹配表达式 */
    private static final String EXPRESSION = "@target(com.alibaba.dubbo.config.annotation.Service)";

    /**
     * 请求时的异常处理
     * 
     * @param proceedingJoinPoint 处理连接点
     * @return 返回对象
     */
    @Around(EXPRESSION)
    public Object handle(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
            if (result instanceof IResult) {
                return result;
            }
            if (result != null && result instanceof Boolean && !((boolean) result)) {
                return ResultEnum.FAIL.result();
            }
        } catch (Throwable e) {
            result = ResultEnum.FAIL.message(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return ResultEnum.SUCCESS.data(result);
    }

}
