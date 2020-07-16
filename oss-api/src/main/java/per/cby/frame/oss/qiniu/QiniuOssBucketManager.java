package per.cby.frame.oss.qiniu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.oss.qiniu.config.properties.QiniuOssProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.BucketManager.BatchOperations;
import com.qiniu.storage.BucketManager.FileListIterator;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.AclType;
import com.qiniu.storage.model.BucketInfo;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.storage.model.IndexPageType;
import com.qiniu.storage.model.StorageType;
import com.qiniu.util.Auth;

import lombok.extern.slf4j.Slf4j;

/**
 * 七牛云OSS对象存储板块管理器
 * 
 * @author chenboyang
 *
 */
@Slf4j
public class QiniuOssBucketManager implements QiniuOssBucketService {

    /** 七牛云OSS对象存储配置属性 */
    private QiniuOssProperties properties;

    /** 服务配置 */
    private Configuration config;

    /** 权限认证 */
    private Auth auth;

    /** 存储板块管理器 */
    private BucketManager bucketManager;

    /**
     * 实例化七牛云OSS对象存储板块管理器
     * 
     * @param properties 七牛云OSS对象存储配置属性
     */
    public QiniuOssBucketManager(QiniuOssProperties properties) {
        this.properties = properties;
        init();
    }

    /**
     * 获取存储板块管理器
     * 
     * @return 板块管理器
     */
    public BucketManager manager() {
        return bucketManager;
    }

    /**
     * 初始化
     */
    private void init() {
        QiniuOssProperties.validate(properties);
        config = new Configuration(properties.getZone());
        auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
        bucketManager = new BucketManager(auth, config);
    }

    @Override
    public String encodedEntry(String bucket, String key) {
        return BucketManager.encodedEntry(bucket, key);
    }

    @Override
    public List<String> bucketList() {
        try {
            return BaseUtil.newArrayList(bucketManager.buckets());
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void createBucket(String bucketName, String region) {
        try {
            bucketManager.createBucket(bucketName, region);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteBucket(String bucketname) {
        try {
            bucketManager.deleteBucket(bucketname);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> domainList(String bucket) {
        try {
            return BaseUtil.newArrayList(bucketManager.domainList(bucket));
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public FileListIterator createFileListIterator(String bucket, String prefix) {
        return bucketManager.createFileListIterator(bucket, prefix);
    }

    @Override
    public FileListIterator createFileListIterator(String bucket, String prefix, int limit, String delimiter) {
        return bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
    }

    @Override
    public FileListing listFiles(String bucket, String prefix, String marker, int limit, String delimiter) {
        try {
            return bucketManager.listFiles(bucket, prefix, marker, limit, delimiter);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public FileInfo stat(String bucket, String fileKey) {
        try {
            return bucketManager.stat(bucket, fileKey);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response delete(String bucket, String key) {
        try {
            return bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response changeMime(String bucket, String key, String mime) {
        try {
            return bucketManager.changeMime(bucket, key, mime);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response changeHeaders(String bucket, String key, Map<String, String> headers) {
        try {
            return bucketManager.changeHeaders(bucket, key, headers);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response changeType(String bucket, String key, StorageType type) {
        try {
            return bucketManager.changeType(bucket, key, type);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response rename(String bucket, String oldFileKey, String newFileKey, boolean force) {
        try {
            return bucketManager.rename(bucket, oldFileKey, newFileKey, force);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response copy(String fromBucket, String fromFileKey, String toBucket, String toFileKey, boolean force) {
        try {
            return bucketManager.copy(fromBucket, fromFileKey, toBucket, toFileKey, force);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response move(String fromBucket, String fromFileKey, String toBucket, String toFileKey, boolean force) {
        try {
            return bucketManager.move(fromBucket, fromFileKey, toBucket, toFileKey, force);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public FetchRet fetch(String url, String bucket, String key) {
        try {
            return bucketManager.fetch(url, bucket, key);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response asynFetch(String url, String bucket, String key) {
        try {
            return bucketManager.asynFetch(url, bucket, key);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response asynFetch(String url, String bucket, String key, String md5, String etag, String callbackurl,
            String callbackbody, String callbackbodytype, String callbackhost, String fileType) {
        try {
            return bucketManager.asynFetch(url, bucket, key, md5, etag, callbackurl, callbackbody, callbackbodytype,
                    callbackhost, fileType);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response checkAsynFetchid(String region, String fetchWorkId) {
        try {
            return bucketManager.checkAsynFetchid(region, fetchWorkId);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void prefetch(String bucket, String key) {
        try {
            bucketManager.prefetch(bucket, key);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Response setImage(String bucket, String srcSiteUrl, String host) {
        try {
            return bucketManager.setImage(bucket, srcSiteUrl, host);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response unsetImage(String bucket) {
        try {
            return bucketManager.unsetImage(bucket);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response deleteAfterDays(String bucket, String key, int days) {
        try {
            return bucketManager.deleteAfterDays(bucket, key, days);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void setBucketAcl(String bucket, AclType acl) {
        try {
            bucketManager.setBucketAcl(bucket, acl);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public BucketInfo getBucketInfo(String bucket) {
        try {
            return bucketManager.getBucketInfo(bucket);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void setIndexPage(String bucket, IndexPageType type) {
        try {
            bucketManager.setIndexPage(bucket, type);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Response batch(BatchOperations operations) {
        try {
            return bucketManager.batch(operations);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void create(String bucket) {
        try {
            bucketManager.createBucket(bucket, properties.getZone().getRegion());
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> list() {
        return bucketList();
    }

    @Override
    public boolean exists(String bucket) {
        return Optional.ofNullable(list()).map(list -> list.contains(bucket)).orElse(false);
    }

    @Override
    public void delete(String bucket) {
        deleteBucket(bucket);
    }

    @Override
    public long count(String bucket) {
        return Optional.ofNullable(listFiles(bucket, null, null, 0, null)).map(t -> t.items).map(t -> t.length)
                .orElse(0);
    }

    @Override
    public List<String> fileNameList(String bucket) {
        List<String> list = BaseUtil.newArrayList();
        try {
            FileListing fileList = bucketManager.listFiles(bucket, null, null, 1000, null);
            if (fileList != null && ArrayUtils.isNotEmpty(fileList.items)) {
                for (FileInfo file : fileList.items) {
                    list.add(file.key);
                }
            }
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

}
