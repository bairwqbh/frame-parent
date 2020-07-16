package per.cby.frame.common.db;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据源连接信息类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class DataSourceInfo {

    /** 驱动类 */
    private DriverClass driver;

    /** 连接地址 */
    private String url;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

}
