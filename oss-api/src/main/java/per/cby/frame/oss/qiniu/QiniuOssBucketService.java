package per.cby.frame.oss.qiniu;

import java.util.List;
import java.util.Map;

import per.cby.frame.common.file.storage.bucket.BucketManager;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager.BatchOperations;
import com.qiniu.storage.BucketManager.FileListIterator;
import com.qiniu.storage.model.AclType;
import com.qiniu.storage.model.BucketInfo;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.storage.model.IndexPageType;
import com.qiniu.storage.model.StorageType;

/**
 * 七牛云OSS对象存储板块服务接口
 * 
 * @author chenboyang
 *
 */
public interface QiniuOssBucketService extends BucketManager {

    /** 实例名称 */
    String BEAN_NAME = "qiniu_oss_bucket";

    /**
     * EncodedEntryURI格式，其中 bucket+":"+key 称之为 entry
     *
     * @param bucket 空间名称
     * @param key 文件标识
     * @return UrlSafeBase64.encodeToString(entry)
     * @link http://developer.qiniu.com/kodo/api/data-format
     */
    String encodedEntry(String bucket, String key);

    /**
     * EncodedEntryURI格式，用在不指定key值的情况下
     *
     * @param bucket 空间名称
     * @return UrlSafeBase64.encodeToString(bucket)
     * @link http://developer.qiniu.com/kodo/api/data-format
     */
    default String encodedEntry(String bucket) {
        return encodedEntry(bucket, null);
    }

    /**
     * 获取账号下所有空间名称列表
     *
     * @return 空间名称列表
     */
    List<String> bucketList();

    /**
     * 创建模块
     * 
     * @param bucketName 板块名称
     * @param region     存储地区
     */
    void createBucket(String bucketName, String region);

    /**
     * 删除板块
     * 
     * @param bucketname 板块名称
     */
    void deleteBucket(String bucketname);

    /**
     * 获取该空间下所有的domain
     *
     * @param bucket 板块名称
     * @return 该空间名下的domain
     */
    List<String> domainList(String bucket);

    /**
     * 根据前缀获取文件列表的迭代器
     *
     * @param bucket 空间名
     * @param prefix 文件名前缀
     * @return 文件属性列迭代器
     */
    FileListIterator createFileListIterator(String bucket, String prefix);

    /**
     * 根据前缀获取文件列表的迭代器
     *
     * @param bucket    空间名
     * @param prefix    文件名前缀
     * @param limit     每次迭代的长度限制，最大1000，推荐值 100
     * @param delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
     * @return 文件属性列迭代器
     */
    FileListIterator createFileListIterator(String bucket, String prefix, int limit, String delimiter);

    /**
     * 根据前缀获取文件列表
     *
     * @param bucket    空间名
     * @param prefix    文件名前缀
     * @param marker    上一次获取文件列表时返回的 marker
     * @param limit     每次迭代的长度限制，最大1000，推荐值 100
     * @param delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
     * @return 文件列属性
     */
    FileListing listFiles(String bucket, String prefix, String marker, int limit, String delimiter);

    /**
     * 获取空间中文件的属性
     *
     * @param bucket  空间名称
     * @param fileKey 文件名称
     * @return 文件属性
     * @link http://developer.qiniu.com/kodo/api/stat
     */
    FileInfo stat(String bucket, String fileKey);

    /**
     * 删除指定空间、文件名的文件
     *
     * @param bucket 空间名称
     * @param key    文件名称
     * @return 响应结果
     * @link http://developer.qiniu.com/kodo/api/delete
     */
    Response delete(String bucket, String key);

    /**
     * 修改文件的MimeType
     *
     * @param bucket 空间名称
     * @param key    文件名称
     * @param mime   文件的新MimeType
     * @return 响应结果
     * @link http://developer.qiniu.com/kodo/api/chgm
     */
    Response changeMime(String bucket, String key, String mime);

    /**
     * 修改文件的元数据
     *
     * @param bucket  空间名称
     * @param key     文件名称
     * @param headers 需要修改的文件元数据
     * @return 响应结果
     * @link https://developer.qiniu.com/kodo/api/1252/chgm
     */
    Response changeHeaders(String bucket, String key, Map<String, String> headers);

    /**
     * 修改文件的类型（普通存储或低频存储）
     *
     * @param bucket 空间名称
     * @param key    文件名称
     * @param type   type=0 表示普通存储，type=1 表示低频存存储
     * @return 响应结果
     */
    Response changeType(String bucket, String key, StorageType type);

    /**
     * 重命名空间中的文件，可以设置force参数为true强行覆盖空间已有同名文件
     *
     * @param bucket     空间名称
     * @param oldFileKey 文件名称
     * @param newFileKey 新文件名
     * @param force      强制覆盖空间中已有同名（和 newFileKey 相同）的文件
     * @return 响应结果
     */
    Response rename(String bucket, String oldFileKey, String newFileKey, boolean force);

    /**
     * 重命名空间中的文件
     *
     * @param bucket     空间名称
     * @param oldFileKey 文件名称
     * @param newFileKey 新文件名
     * @return 响应结果
     * @link http://developer.qiniu.com/kodo/api/move
     */
    default Response rename(String bucket, String oldFileKey, String newFileKey) {
        return move(bucket, oldFileKey, bucket, newFileKey);
    }

    /**
     * 复制文件，要求空间在同一账号下，可以设置force参数为true强行覆盖空间已有同名文件
     *
     * @param fromBucket  源空间名称
     * @param fromFileKey 源文件名称
     * @param toBucket    目的空间名称
     * @param toFileKey   目的文件名称
     * @param force       强制覆盖空间中已有同名（和 toFileKey 相同）的文件
     * @return 响应结果
     */
    Response copy(String fromBucket, String fromFileKey, String toBucket, String toFileKey, boolean force);

    /**
     * 复制文件，要求空间在同一账号下
     *
     * @param fromBucket  源空间名称
     * @param fromFileKey 源文件名称
     * @param toBucket    目的空间名称
     * @param toFileKey   目的文件名称
     * @return 响应结果
     */
    default void copy(String fromBucket, String fromFileKey, String toBucket, String toFileKey) {
        copy(fromBucket, fromFileKey, toBucket, toFileKey, false);
    }

    /**
     * 移动文件，要求空间在同一账号下
     *
     * @param fromBucket  源空间名称
     * @param fromFileKey 源文件名称
     * @param toBucket    目的空间名称
     * @param toFileKey   目的文件名称
     * @param force       强制覆盖空间中已有同名（和 toFileKey 相同）的文件
     * @return 响应结果
     */
    Response move(String fromBucket, String fromFileKey, String toBucket, String toFileKey, boolean force);

    /**
     * 移动文件。要求空间在同一账号下, 可以添加force参数为true强行移动文件。
     *
     * @param fromBucket  源空间名称
     * @param fromFileKey 源文件名称
     * @param toBucket    目的空间名称
     * @param toFileKey   目的文件名称
     * @return 响应结果
     */
    default Response move(String fromBucket, String fromFileKey, String toBucket, String toFileKey) {
        return move(fromBucket, fromFileKey, toBucket, toFileKey, false);
    }

    /**
     * 抓取指定地址的文件，以指定名称保存在指定空间 要求指定url可访问，大文件不建议使用此接口抓取。可先下载再上传 如果不指定保存的文件名，那么以文件内容的
     * etag 作为文件名
     *
     * @param url    待抓取的文件链接
     * @param bucket 文件抓取后保存的空间
     * @return 接口的回复对象
     */
    default FetchRet fetch(String url, String bucket) {
        return fetch(url, bucket, null);
    }

    /**
     * 抓取指定地址的文件，以指定名称保存在指定空间 要求指定url可访问，大文件不建议使用此接口抓取。可先下载再上传
     *
     * @param url    待抓取的文件链接
     * @param bucket 文件抓取后保存的空间
     * @param key    文件抓取后保存的文件名
     * @return 接口的回复对象
     */
    FetchRet fetch(String url, String bucket, String key);

    /**
     * 异步第三方资源抓取 从指定 URL 抓取资源，并将该资源存储到指定空间中。每次只抓取一个文件，抓取时可以指定保存空间名和最终资源名。
     * 主要对于大文件进行抓取 https://developer.qiniu.com/kodo/api/4097/asynch-fetch
     *
     * @param url    待抓取的文件链接，支持设置多个,以';'分隔
     * @param bucket 文件抓取后保存的空间
     * @param key    文件抓取后保存的文件名
     * @return 响应结果
     */
    Response asynFetch(String url, String bucket, String key);

    /**
     * 异步第三方资源抓取 从指定 URL 抓取资源，并将该资源存储到指定空间中。每次只抓取一个文件，抓取时可以指定保存空间名和最终资源名。
     * https://developer.qiniu.com/kodo/api/4097/asynch-fetch 提供更多参数的抓取 可以对抓取文件进行校验
     * 和自定义抓取回调地址等
     *
     * @param url              待抓取的文件链接，支持设置多个,以';'分隔
     * @param bucket           文件抓取后保存的空间
     * @param key              文件抓取后保存的文件名
     * @param md5              文件md5,传入以后会在存入存储时对文件做校验，校验失败则不存入指定空间
     * @param etag             文件etag,传入以后会在存入存储时对文件做校验，校验失败则不存入指定空间
     * @param callbackurl      回调URL，详细解释请参考上传策略中的callbackUrl
     * @param callbackbody     回调Body，如果callbackurl不为空则必须指定。与普通上传一致支持魔法变量，
     * @param callbackbodytype 回调Body内容类型,默认为"application/x-www-form-urlencoded"，
     * @param callbackhost     回调时使用的Host
     * @param fileType         存储文件类型 0:正常存储(默认),1:低频存储
     * @return 响应结果
     */
    Response asynFetch(String url, String bucket, String key, String md5, String etag, String callbackurl,
            String callbackbody, String callbackbodytype, String callbackhost, String fileType);

    /**
     * 查询异步抓取任务
     *
     * @param region      抓取任务所在bucket区域 华东 z0 华北 z1 华南 z2 北美 na0 东南亚 as0
     * @param fetchWorkId 抓取任务id
     * @return 响应结果
     */
    Response checkAsynFetchid(String region, String fetchWorkId);

    /**
     * 对于设置了镜像存储的空间，从镜像源站抓取指定名称的资源并存储到该空间中 如果该空间中已存在该名称的资源，则会将镜像源站的资源覆盖空间中相同名称的资源
     *
     * @param bucket 空间名称
     * @param key    文件名称
     */
    void prefetch(String bucket, String key);

    /**
     * 设置空间的镜像源站
     *
     * @param bucket     空间名称
     * @param srcSiteUrl 镜像回源地址
     * @return 响应结果
     */
    default Response setImage(String bucket, String srcSiteUrl) {
        return setImage(bucket, srcSiteUrl, null);
    }

    /**
     * 设置空间的镜像源站
     *
     * @param bucket     空间名称
     * @param srcSiteUrl 镜像回源地址
     * @param host       镜像回源Host
     * @return 响应结果
     */
    Response setImage(String bucket, String srcSiteUrl, String host);

    /**
     * 取消空间的镜像源站设置
     *
     * @param bucket 空间名称
     * @return 响应结果
     */
    Response unsetImage(String bucket);

    /**
     * 设置文件的存活时间
     *
     * @param bucket 空间名称
     * @param key    文件名称
     * @param days   存活时间，单位：天
     * @return 响应结果
     */
    Response deleteAfterDays(String bucket, String key, int days);

    /**
     * 设置板块空间权限
     * 
     * @param bucket 板块名称
     * @param acl    权限
     */
    void setBucketAcl(String bucket, AclType acl);

    /**
     * 获取板块信息
     * 
     * @param bucket 模块名称
     * @return 板块信息
     */
    BucketInfo getBucketInfo(String bucket);

    /**
     * 设置板块分页类型
     * 
     * @param bucket 板块名称
     * @param type   分页类型
     */
    void setIndexPage(String bucket, IndexPageType type);

    /**
     * 批量文件管理请求
     * 
     * @param operations 文件管理批量操作指令构建对象
     * @return 响应结果
     */
    Response batch(BatchOperations operations);

}
