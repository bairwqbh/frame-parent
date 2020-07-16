package per.cby.frame.common.file.storage.storage;

import java.io.File;

/**
 * <h1>系统文件存储服务</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 保存文件、判断文件是否存在、删除文件、复制文件、查询文件、写入文件、获取文件文本
 * </p>
 * 
 * @author chenboyang
 * 
 */
public interface SystemFileStorage extends FileStorage {

    /** 缺省文件存储根目录 */
    String DEF_DIR = "upload";

    /** 缺省默认存储板块 */
    String DEF_BUC = "default";

    /** 实例名称 */
    String BEAN_NAME = "system_file_storage";

    /**
     * 文件存储根目录
     * 
     * @return 根目录
     */
    String directory();

    /**
     * 默认存储板块
     * 
     * @return 板块
     */
    String defaultBucket();

    /**
     * 根据存储板块和文件名称获取文件
     * 
     * @param bucket 存储板块
     * @param name   文件名称
     * @return 文件
     */
    File getFile(String bucket, String name);

    /**
     * 根据存储板块和文件名称获取文件访问相对路径
     * 
     * @param bucket 存储板块
     * @param name   文件名称
     * @return 路径
     */
    String relativePath(String bucket, String name);

}
