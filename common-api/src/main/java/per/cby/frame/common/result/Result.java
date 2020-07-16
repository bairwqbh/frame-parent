package per.cby.frame.common.result;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回结果封装对象
 * 
 * @author chenboyang
 *
 * @param <T> 结果类型
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "Result对象", description = "返回信息")
public class Result<T> implements IResult, Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("数据")
    private T data;

}
