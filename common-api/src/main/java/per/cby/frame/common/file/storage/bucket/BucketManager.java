package per.cby.frame.common.file.storage.bucket;

import java.util.List;

/**
 * 存储板块管理接口
 * 
 * @author chenboyang
 *
 */
public interface BucketManager {

    /**
     * 创建存储板块
     * 
     * @param bucket 存储板块名称
     */
    void create(String bucket);

    /**
     * 获取存储板块列表
     * 
     * @return 存储板块列表
     */
    List<String> list();

    /**
     * 判断存储板块是否存在
     * 
     * @param bucket 存储板块名称
     * @return 是否存在
     */
    boolean exists(String bucket);

    /**
     * 删除存储板块
     * 
     * @param bucket 存储板块名称
     */
    void delete(String bucket);

    /**
     * 获取存储板块文件数量
     * 
     * @param bucket 存储板块名称
     * @return 文件数量
     */
    long count(String bucket);

    /**
     * 获取存储板块的文件名列表
     * 
     * @param bucket 存储板块名称
     * @return 文件名列表
     */
    List<String> fileNameList(String bucket);

}
