package per.cby.frame.web.filter;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import per.cby.frame.web.constant.WebConstant;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * 跨域过滤器
 * 
 * @author chenboyang
 * @since 2019年7月23日
 *
 */
@RequiredArgsConstructor
public class CorsFilter implements WebFilter, WebConstant {

    /** 允许的头信息 */
    private final List<String> allowedHeaders;

    /**
     * 构建跨域过滤器
     */
    public CorsFilter() {
        this(ALLOWED_HEADERS);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.setAccessControlAllowMethods(ALLOWED_METHODS);
            headers.setAccessControlAllowCredentials(true);
            headers.setAccessControlAllowOrigin(request.getHeaders().getOrigin());
            headers.setAccessControlAllowHeaders(allowedHeaders);
            headers.setAccessControlMaxAge(MAX_AGE.longValue());
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }

}
