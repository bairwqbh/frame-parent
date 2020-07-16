package per.cby.frame.gis.service.gaode;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 高德WEB服务请求参数基类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public abstract class GaodeResult {

    /** 请求成功 */
    public static final int SUCCESS = 1;

    /** 请求失败 */
    public static final int FAIL = 0;

    /** 响应状态 */
    private int status;

    /** 响应信息 */
    private String info;

    /** 状态码 */
    private int infocode;

}
