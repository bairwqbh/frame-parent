package per.cby.frame.oss.aliyun;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectAcl;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import com.aliyun.oss.model.UploadFileResult;
import per.cby.frame.common.file.storage.storage.FileStorage;

/**
 * <h1>阿里云OSS存储服务接口</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 一、存储板块管理：新建存储板块，列表查询存储板块，判断存储板块是否存在，删除存储板块，设置存储板块权限
 * </p>
 * <p>
 * 二、文件管理：上传文件对象，列表查询文件对象，获取文件对象，删除文件对象，拷贝文件对象
 * </p>
 * <p>
 * 三、分块上传：分块上传文件对象，取消分块上传事件，获取存储板块内所有分块上传事件，获取所有已上传的块信息
 * </p>
 * <p>
 * 四、生成预签名URL：生成一个预签名的URL，生成其他Http方法的URL
 * </p>
 * 
 * @author chenboyang
 * 
 */
public interface AliyunOssService extends FileStorage {

    /** 实例名称 */
    String BEAN_NAME = "aliyun_oss_storage";

    /**
     * 获取云平台连接
     * 
     * @return 云平台连接
     */
    OSSClient getClient();

    /**
     * 新建存储板块
     * 
     * @param bucket 存储板块
     * @return 存储板块
     */
    Bucket createBucket(String bucket);

    /**
     * 列表查询存储板块
     * 
     * @return 存储板块列表
     */
    List<Bucket> listBuckets();

    /**
     * 判断存储板块是否存在
     * 
     * @param bucket 存储板块
     * @return 存储板块是否存在
     */
    boolean doesBucketExist(String bucket);

    /**
     * 删除存储板块
     * 
     * @param bucket 存储板块
     */
    void deleteBucket(String bucket);

    /**
     * 设置存储板块权限
     * 
     * @param bucket 存储板块
     * @param acl    权限
     */
    void setBucketAcl(String bucket, CannedAccessControlList acl);

    /**
     * 获取存储板块权限
     * 
     * @param bucket 存储板块
     * @return 存储板块权限
     */
    AccessControlList getBucketAcl(String bucket);

    /**
     * 获取存储板块的位置
     * 
     * @param bucket 存储板块
     * @return 存储板块的位置
     */
    String getBucketLocation(String bucket);

    /**
     * 获取存储板块的详细信息 存储板块
     * 
     * @param bucket
     * @return 存储板块的详细信息
     */
    BucketInfo getBucketInfo(String bucket);

    /**
     * 上传文件对象
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @param file   文件
     * @return 上传结果
     */
    PutObjectResult putObject(String bucket, String key, File file);

    /**
     * 上传文件对象
     * 
     * @param bucket   存储板块
     * @param key      标识
     * @param file     文件
     * @param metadata 元数据
     * @return 上传结果
     */
    PutObjectResult putObject(String bucket, String key, File file, ObjectMetadata metadata);

    /**
     * 上传数据流对象
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @param input  数据流
     * @return 上传结果
     */
    PutObjectResult putObject(String bucket, String key, InputStream input);

    /**
     * 上传数据流对象
     * 
     * @param bucket   存储板块
     * @param key      标识
     * @param input    数据流
     * @param metadata 元数据
     * @return 上传结果
     */
    PutObjectResult putObject(String bucket, String key, InputStream input, ObjectMetadata metadata);

    /**
     * 追加上传文件对象
     * 
     * @param bucket             存储板块
     * @param key                标识
     * @param file               文件
     * @param appendObjectResult 追加对象结果
     * @return 追加对象结果
     */
    AppendObjectResult appendObject(String bucket, String key, File file, AppendObjectResult appendObjectResult);

    /**
     * 追加上传文件对象
     * 
     * @param bucket             存储板块
     * @param key                标识
     * @param file               文件
     * @param appendObjectResult 追加对象结果
     * @param metadata           元数据
     * @return 追加对象结果
     */
    AppendObjectResult appendObject(String bucket, String key, File file, AppendObjectResult appendObjectResult,
            ObjectMetadata metadata);

    /**
     * 追加上传数据流对象
     * 
     * @param bucket             存储板块
     * @param key                标识
     * @param input              数据流
     * @param appendObjectResult 追加对象结果
     * @return 追加对象结果
     */
    AppendObjectResult appendObject(String bucket, String key, InputStream input,
            AppendObjectResult appendObjectResult);

    /**
     * 追加上传数据流对象
     * 
     * @param bucket             存储板块
     * @param key                标识
     * @param input              数据流
     * @param appendObjectResult 追加对象结果
     * @param metadata           元数据
     * @return 追加对象结果
     */
    AppendObjectResult appendObject(String bucket, String key, InputStream input, AppendObjectResult appendObjectResult,
            ObjectMetadata metadata);

    /**
     * 断点续传上传文件
     * 
     * @param bucket     存储板块
     * @param key        标识
     * @param uploadFile 文件路径
     * @return 断点续传结果
     */
    UploadFileResult uploadFile(String bucket, String key, String uploadFile);

    /**
     * 列表查询文件对象
     * 
     * @param bucket 存储板块
     * @return 文件列表对象
     */
    ObjectListing listObjects(String bucket);

    /**
     * 列表查询文件对象
     * 
     * @param bucket 存储板块
     * @param prefix 文件名前缀
     * @return 文件列表对象
     */
    ObjectListing listObjects(String bucket, String prefix);

    /**
     * 判断文件是否存在
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @return 文件是否存在
     */
    boolean doesObjectExist(String bucket, String key);

    /**
     * 设置对象权限
     * 
     * @param bucket    存储板块
     * @param key       标识
     * @param cannedACL 权限
     */
    void setObjectAcl(String bucket, String key, CannedAccessControlList cannedACL);

    /**
     * 获取对象权限
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @return 权限
     */
    ObjectAcl getObjectAcl(String bucket, String key);

    /**
     * 获取对象简单元数据
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @return 简单元数据
     */
    SimplifiedObjectMeta getSimplifiedObjectMeta(String bucket, String key);

    /**
     * 获取对象详细元数据
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @return 详细元数据
     */
    ObjectMetadata getObjectMetadata(String bucket, String key);

    /**
     * 获取文件对象
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @param client 接口连接
     * @return 文件对象
     */
    OSSObject getObject(String bucket, String key, OSSClient client);

    /**
     * 删除文件对象
     * 
     * @param bucket 存储板块
     * @param key    标识
     */
    void deleteObject(String bucket, String key);

    /**
     * 批量删除存储板块下的文件对象
     * 
     * @param bucket 存储板块
     * @return 删除文件结果
     */
    DeleteObjectsResult deleteObjects(String bucket);

    /**
     * 拷贝文件对象
     * 
     * @param srcBucket  源存储板块
     * @param srcKey     源标识
     * @param destBucket 目标存储板块
     * @param destKey    目标标识
     * @return 文件拷贝结果
     */
    CopyObjectResult copyObject(String srcBucket, String srcKey, String destBucket, String destKey);

    /**
     * 分块上传文件对象
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @param file   文件
     * @return 文件分块上传结果
     */
    CompleteMultipartUploadResult multipartUpload(String bucket, String key, File file);

    /**
     * 取消分块上传事件
     * 
     * @param bucket   存储板块
     * @param key      标识
     * @param uploadId 上传标识
     */
    void abortMultipartUpload(String bucket, String key, String uploadId);

    /**
     * 获取存储板块内所有分块上传事件
     * 
     * @param bucket 存储板块
     * @return 分块上传列表
     */
    MultipartUploadListing listMultipartUploads(String bucket);

    /**
     * 获取所有已上传的块信息
     * 
     * @param bucket   存储板块
     * @param key      标识
     * @param uploadId 上传标识
     * @return 分块上传信息列表
     */
    PartListing listParts(String bucket, String key, String uploadId);

    /**
     * 生成一个预签名的URL
     * 
     * @param bucket 存储板块
     * @param key    标识
     * @return URL对象
     */
    URL generatePresignedUrl(String bucket, String key);

    /**
     * 生成一个预签名的URL
     * 
     * @param bucket     存储板块
     * @param key        标识
     * @param expiration 过期时间
     * @return URL对象
     */
    URL generatePresignedUrl(String bucket, String key, Date expiration);

    /**
     * 生成其他Http方法的URL
     * 
     * @param bucket     存储板块
     * @param key        标识
     * @param expiration 过期时间
     * @param method     请求方式
     * @return URL对象
     */
    URL generatePresignedUrl(String bucket, String key, Date expiration, HttpMethod method);

}
