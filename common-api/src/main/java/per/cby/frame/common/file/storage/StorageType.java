package per.cby.frame.common.file.storage;

/**
 * 存储类型
 * 
 * @author chenboyang
 * @since 2019年10月9日
 *
 */
public interface StorageType {

    /** 系统文件 */
    String SYSTEM = "system_file";

    /** MongoDB文件存储 */
    String MONGO = "mongo_gridfs";

    /** 阿里云对象存储 */
    String ALIYUN = "aliyun_oss";

    /** 七牛云对象存储 */
    String QINIU = "qiniu_oss";
}
