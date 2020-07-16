package per.cby.frame.common.config.properties;

import java.util.Map;

import per.cby.frame.common.util.DateUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统配置属性类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysProperties对象", description = "系统配置信息")
public class SysProperties {

    @ApiModelProperty("服务器IP地址")
    private String address;

    @ApiModelProperty("服务器端口")
    private int port = 8080;

    @ApiModelProperty("服务器名称")
    private String name;

    @ApiModelProperty("服务器访问地址")
    private String url;

    @ApiModelProperty("会话过期时间")
    private long timeout = 30L;

    @ApiModelProperty("文件上传临时目录")
    private String uploadDir;

    @ApiModelProperty("系统统一字符集")
    private String charset = "UTF-8";

    @ApiModelProperty("日期数据默认格式")
    private String dateFormat = DateUtil.DATETIME_FORMAT;

    @ApiModelProperty("时区")
    private String timeZone = DateUtil.GMT_E_8;

    @ApiModelProperty("系统业务配置数据")
    private Map<String, Object> business;

}
