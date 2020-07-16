package per.cby.frame.mongo.service;

import java.io.InputStream;
import java.io.OutputStream;

import org.bson.types.ObjectId;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSFile;

/**
 * MongoDB数据库文件存储系统服务接口
 * 
 * @author chenboyang
 *
 */
public interface GridFsService {

    /**
     * 上传文件
     * 
     * @param bucket   存储板块
     * @param filename 文件名称
     * @param input    文件输入流
     * @return 对象编号
     */
    ObjectId upload(String bucket, String filename, InputStream input);

    /**
     * 下载文件
     * 
     * @param bucket   存储板块
     * @param filename 文件名称
     * @param output   文件输出流
     * @return 对象编号
     */
    void download(String bucket, String filename, OutputStream output);

    /**
     * 删除文件
     * 
     * @param bucket   存储板块
     * @param filename 文件名称
     */
    void delete(String bucket, String filename);

    /**
     * 删除指定存储板块的全部文件
     * 
     * @param bucket 存储板块
     */
    void drop(String bucket);

    /**
     * 文件是否存在
     * 
     * @param bucket   存储板块
     * @param filename 文件名称
     * @return 是否存在
     */
    boolean exist(String bucket, String filename);

    /**
     * 查询指定存储模板的全部文件信息
     * 
     * @param bucket 存储板块
     */
    GridFSFindIterable find(String bucket);

    /**
     * 查询文件信息
     * 
     * @param bucket   存储板块
     * @param filename 文件名称
     * @return 文件信息
     */
    GridFSFile find(String bucket, String filename);

    /**
     * 打开文件下载流
     * 
     * @param bucket   存储板块
     * @param filename 文件名称
     * @return 文件输入流
     */
    GridFSDownloadStream openDownloadStream(String bucket, String filename);

    /**
     * 打开文件上传流
     * 
     * @param bucket   存储板块
     * @param filename 文件名称
     * @return 文件输出流
     */
    GridFSUploadStream openUploadStream(String bucket, String filename);

    /**
     * 板块内复制文件
     * 
     * @param bucket     存储板块
     * @param sourceName 源文件名称
     * @param targetName 目录文件名称
     */
    void copy(String bucket, String sourceName, String targetName);

}
