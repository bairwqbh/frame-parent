package per.cby.frame.mongo.constant;

/**
 * Mongo配置常量
 * 
 * @author chenboyang
 *
 */
public interface MongoConstant {

    /** MongoDB数据库文件存储操作模板容器 */
    String GRID_FS_TEMP_MAP = "gridFsTempMap";

    /** MongoDB数据库文件存储板块容器 */
    String GRID_FS_BUCKET_MAP = "gridFsBucketMap";

    /** 文件名称字段名 */
    public String FILENAME_KEY = "filename";

    /** 板块操作模板过期时间 */
    long GRIDFS_OPERATE_TIMEOUT = 1000L * 60 * 10;

}
