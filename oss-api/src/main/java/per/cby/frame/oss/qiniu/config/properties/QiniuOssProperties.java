package per.cby.frame.oss.qiniu.config.properties;

import per.cby.frame.common.exception.BusinessAssert;
import com.qiniu.common.Zone;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 七牛云OSS对象存储配置属性
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class QiniuOssProperties {

    /** 服务区域 */
    private Zone zone;

    /** 服务区域标识 */
    private String region;

    /** 服务访问ID */
    private String accessKey;

    /** 服务访问KEY */
    private String secretKey;

    /** 过期时长 */
    private long timeLength = 1000L * 60 * 60 * 24 * 30 * 12 * 100;

    /**
     * 验证七牛云OSS对象存储配置属性
     * 
     * @param properties 七牛云OSS对象存储配置属性
     */
    public static void validate(QiniuOssProperties properties) {
        BusinessAssert.notNull(properties, "七牛云配置对象为空，无法初始化服务！");
        BusinessAssert.notNull(properties.getAccessKey(), "七牛云accessKey为空，无法初始化服务！");
        BusinessAssert.notNull(properties.getSecretKey(), "七牛云secretKey为空，无法初始化服务！");
        if (properties.getZone() == null) {
            Zone zone = null;
            switch (properties.getRegion()) {
                case "0":
                    zone = Zone.zone0();
                    break;
                case "1":
                    zone = Zone.zone1();
                    break;
                case "2":
                    zone = Zone.zone2();
                    break;
                case "na0":
                    zone = Zone.zoneNa0();
                    break;
                case "as0":
                    zone = Zone.zoneAs0();
                    break;
                default:
                    zone = Zone.autoZone();
                    break;
            }
            properties.setZone(zone);
        }
    }

}
