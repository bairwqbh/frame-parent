package per.cby.frame.oss.aliyun.config.properties;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 阿里云OSS对象存储配置属性
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class AliyunOssProperties {

    /** OSS服务地址 */
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

    /** 服务访问ID */
    private String accessId;

    /** 服务访问KEY */
    private String accessKey;

    /** 分块Size */
    private long partSize = 1024L * 1024 * 10;

    /** 并发线程数 */
    private int taskNum = 10;

    /** 过期时长 */
    private long timeLength = 1000L * 60 * 60 * 24 * 30 * 12 * 100;

}
