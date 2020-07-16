package per.cby.frame.web.filter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import per.cby.frame.common.util.DateUtil;
import per.cby.frame.common.util.JudgeUtil;
import per.cby.frame.common.util.StringUtil;
import per.cby.frame.web.constant.SignResult;
import per.cby.frame.web.constant.ValidateSignConstant;
import per.cby.frame.web.service.ValidateSignService;

import reactor.core.publisher.Mono;

/**
 * token校验过滤器
 * 
 * @author chenboyang
 * @since 2019年7月15日
 *
 */
public class ValidateSignFilter extends AbstractGatewayFilter implements ValidateSignConstant {

    @Autowired
    private ValidateSignService validateSignService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (match(exchange)) {
            ServerHttpRequest request = exchange.getRequest();
            exchange.getResponse().getHeaders().setAccessControlAllowHeaders(ALLOWED_HEADERS);
            String key = getParam(request, KEY);
            String timestamp = getParam(request, TIMESTAMP);
            String sign = getParam(request, SIGN);
            if (JudgeUtil.isOneBlank(key, timestamp, sign)) {
                return resultWrite(exchange, SignResult.MISS_PARAM.result());
            }
            if (!StringUtil.isMatche(timestamp, DateUtil.DATETIME_REGEX)) {
                return resultWrite(exchange, SignResult.TIME_FORMAT_ERROR.result());
            }
            if (Duration.between(DateUtil.parseLocalDateTime(timestamp), LocalDateTime.now())
                    .getSeconds() > EXPIRED_LIMIT) {
                return resultWrite(exchange, SignResult.EXPIRED.result());
            }
            String secret = validateSignService.getSecret(key);
            if (secret == null) {
                return resultWrite(exchange, SignResult.INVALID_KEY.result());
            }
            String genSign = validateSignService.genSign(key, secret, timestamp);
            if (!StringUtils.equals(sign, genSign)) {
                return resultWrite(exchange, SignResult.SIGN_ERROR.result());
            }
        }
        return chain.filter(exchange);
    }

    /**
     * 从请求信息中获取参数
     * 
     * @param request 请求信息
     * @param name    参数名称
     * @return 参数值
     */
    private String getParam(ServerHttpRequest request, String name) {
        List<String> list = request.getHeaders().get(name);
        if (CollectionUtils.isEmpty(list) && MapUtils.isNotEmpty(request.getQueryParams())) {
            list = request.getQueryParams().get(name);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            list.get(0);
        }
        return null;
    }

}
