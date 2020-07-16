package per.cby.frame.web.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;

import per.cby.frame.common.result.IResult;
import per.cby.frame.web.constant.ResponseResult;

/**
 * 接口返回统一处理切面
 * 
 * @author chenboyang
 *
 */
@Aspect
@Deprecated
public class ReturnAspect {

    /** 匹配表达式 */
    private static final String EXPRESSION = "@target(org.springframework.web.bind.annotation.RestController) && (@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping) || @annotation(org.springframework.web.bind.annotation.PatchMapping))";

    /** 包含路径模式集 */
    private String[] includePatterns = null;

    /** 排除路径模式集 */
    private String[] excludePatterns = { "/proxy/**" };

    /** 路径匹配器 */
    private PathMatcher pathMatcher = new AntPathMatcher();

    /** 请求信息 */
    @Autowired(required = false)
    private HttpServletRequest request;

    /**
     * 设置包含请求地址
     * 
     * @param includePatterns 包含请求地址
     * @return 控制器返回统一处理切面
     */
    public ReturnAspect includePatterns(String... includePatterns) {
        this.includePatterns = includePatterns;
        return this;
    }

    /**
     * 设置排除请求地址
     * 
     * @param excludePatterns 排除请求地址
     * @return 控制器返回统一处理切面
     */
    public ReturnAspect excludePatterns(String... excludePatterns) {
        this.excludePatterns = excludePatterns;
        return this;
    }

    /**
     * 设置路径匹配器
     * 
     * @param pathMatcher 路径匹配器
     * @return 控制器返回统一处理切面
     */
    public ReturnAspect pathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
        return this;
    }

    /**
     * 方法执行时拦截控制
     * 
     * @param proceedingJoinPoint 执行进程点
     * @return 返回执行结果
     * @throws Throwable 抛出异常
     */
    @Around(EXPRESSION)
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        if (result instanceof IResult || result instanceof ResponseEntity
                || (request != null && !matches(request.getRequestURI()))) {
            return result;
        }
//        if (result != null && result instanceof Boolean && !((boolean) result)) {
//            return ResponseResult.FAIL.result();
//        }
        return ResponseResult.SUCCESS.data(result);
    }

    /**
     * 匹配路径模式
     * 
     * @param path 路径
     * @return 是否匹配
     */
    private boolean matches(String path) {
        if (!ObjectUtils.isEmpty(this.excludePatterns)) {
            for (String pattern : this.excludePatterns) {
                if (pathMatcher.match(pattern, path)) {
                    return false;
                }
            }
        }
        if (ObjectUtils.isEmpty(this.includePatterns)) {
            return true;
        }
        for (String pattern : this.includePatterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

}
