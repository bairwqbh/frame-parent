package per.cby.frame.web.filter;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;

import per.cby.frame.common.result.Result;
import per.cby.frame.common.util.JsonUtil;

import reactor.core.publisher.Mono;

/**
 * 抽象的网关过滤器
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractGatewayFilter implements GlobalFilter {

    /** 包含路径模式集 */
    private String[] includes = null;

    /** 排除路径模式集 */
    private String[] excludes = null;

    /** 路径匹配器 */
    private PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 设置包含请求地址
     * 
     * @param includes 包含请求地址
     * @return 控制器返回统一处理切面
     */
    public AbstractGatewayFilter includes(String... includes) {
        this.includes = includes;
        return this;
    }

    /**
     * 设置排除请求地址
     * 
     * @param excludes 排除请求地址
     * @return 控制器返回统一处理切面
     */
    public AbstractGatewayFilter excludes(String... excludes) {
        this.excludes = excludes;
        return this;
    }

    /**
     * 设置路径匹配器
     * 
     * @param pathMatcher 路径匹配器
     * @return 控制器返回统一处理切面
     */
    public AbstractGatewayFilter pathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
        return this;
    }

    /**
     * 验证路径是否匹配
     * 
     * @param exchange 网络交换信息
     * @return 是否匹配
     */
    protected boolean match(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (ArrayUtils.isNotEmpty(excludes)) {
            for (String pattern : excludes) {
                if (pathMatcher.match(pattern, path)) {
                    return false;
                }
            }
        }
        if (ArrayUtils.isEmpty(includes)) {
            return true;
        }
        for (String pattern : includes) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将结果写入数据到响应信息中
     * 
     * @param exchange 网络交换信息
     * @param result   结果
     * @return 结果
     */
    protected <T> Mono<Void> resultWrite(ServerWebExchange exchange, Result<T> result) {
        return writeResponse(exchange, JsonUtil.toJson(result).getBytes(StandardCharsets.UTF_16));
    }

    /**
     * 写入数据到响应信息中
     * 
     * @param exchange 网络交换信息
     * @param data     数据
     * @return 结果
     */
    protected Mono<Void> writeResponse(ServerWebExchange exchange, byte[] data) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = response.bufferFactory().wrap(data);
        return response.writeWith(Mono.just(buffer));
    }

}
