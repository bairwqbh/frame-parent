package per.cby.frame.web.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import per.cby.frame.common.exception.BusinessException;
import per.cby.frame.common.result.IResult;
import per.cby.frame.web.constant.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * MVC接口统一异常处理
 * 
 * @author chenboyang
 *
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnWebApplication
public class ExceptionAdvice {

    /**
     * 普通异常处理
     * 
     * @param e 异常信息
     * @return 结果数据
     */
    @ExceptionHandler(Exception.class)
    public IResult handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseResult.SERVER_ERROR.result();
    }

    /**
     * 业务异常处理
     * 
     * @param e 异常信息
     * @return 结果数据
     */
    @ExceptionHandler(BusinessException.class)
    public IResult handle(BusinessException e) {
        log.error(e.getMessage());
        return ResponseResult.BUSINESS_ERROR.message(e.getMessage());
    }

    /**
     * Token异常处理
     * 
     * @param e 异常信息
     * @return 结果数据
     */
    @ExceptionHandler(TokenException.class)
    public IResult handle(TokenException e) {
        String message = e.getMessage();
        if (StringUtils.isNotBlank(message)) {
            log.error(message);
            return ResponseResult.TOKEN_ERROR.message(message);
        }
        return ResponseResult.TOKEN_ERROR.result();
    }

    /**
     * 参数异常处理
     * 
     * @param e 异常信息
     * @return 结果数据
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public IResult handle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.ILLEGAL_ARGUMENT.message(e.getMessage());
    }

    /**
     * 缺失请求参数异常处理
     * 
     * @param e 异常信息
     * @return 结果数据
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public IResult handle(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        String message = "必需的参数'" + e.getParameterName() + "'不存在!";
        return ResponseResult.MISS_REQUEST_PARAM.message(message);
    }

    /**
     * 参数校验异常处理
     * 
     * @param e 异常信息
     * @return 结果数据
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public IResult handle(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        FieldError error = e.getBindingResult().getFieldError();
        String message = "'" + error.getField() + "'" + error.getDefaultMessage() + "!";
        return ResponseResult.ARGUMENT_NOT_VALID.message(message);
    }

}
