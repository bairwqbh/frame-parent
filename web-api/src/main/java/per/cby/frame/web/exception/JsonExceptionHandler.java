package per.cby.frame.web.exception;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import per.cby.frame.common.exception.BusinessException;
import per.cby.frame.common.result.Result;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.web.constant.ResponseResult;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * JSON异常处理器
 * 
 * @author chenboyang
 *
 */
@Slf4j
public class JsonExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error(ex.getMessage(), ex);
        Result<?> result = null;
        if (ex instanceof BusinessException) {
            result = ResponseResult.BUSINESS_ERROR.message(ex.getMessage());
        } else if (ex instanceof TokenException) {
            String message = ex.getMessage();
            if (StringUtils.isNotBlank(message)) {
                result = ResponseResult.TOKEN_ERROR.message(message);
            } else {
                result = ResponseResult.TOKEN_ERROR.result();
            }
        } else if (ex instanceof IllegalArgumentException) {
            result = ResponseResult.ILLEGAL_ARGUMENT.message(ex.getMessage());
        } else {
            result = ResponseResult.SERVER_ERROR.result();
        }
        String json = JsonUtil.toJson(result);
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = null;
        try {
            buffer = response.bufferFactory().wrap(json.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return response.writeWith(Mono.just(buffer));
    }

}
