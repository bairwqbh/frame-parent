package per.cby.frame.oss.aliyun;

import java.util.List;
import java.util.stream.Collectors;

import com.aliyun.oss.model.OSSObjectSummary;
import per.cby.frame.common.file.storage.bucket.BucketManager;

import lombok.RequiredArgsConstructor;

/**
 * 阿里云OSS对象存储板块管理服务
 * 
 * @author chenboyang
 *
 */
@RequiredArgsConstructor
public class AliyunOssBucketManager implements BucketManager {

    /** 实例名称 */
    public static final String BEAN_NAME = "aliyun_oss_bucket";

    /** OSS对象存储服务接口 */
    private final AliyunOssService oss;

    @Override
    public void create(String bucket) {
        oss.createBucket(bucket);
    }

    @Override
    public List<String> list() {
        return oss.listBuckets().stream().map((b) -> b.getName()).collect(Collectors.toList());
    }

    @Override
    public boolean exists(String bucket) {
        return oss.doesBucketExist(bucket);
    }

    @Override
    public void delete(String bucket) {
        oss.deleteBucket(bucket);
    }

    @Override
    public long count(String bucket) {
        return oss.getClient().getBucketStat(bucket).getObjectCount().longValue();
    }

    @Override
    public List<String> fileNameList(String bucket) {
        return oss.listObjects(bucket).getObjectSummaries().stream().map(OSSObjectSummary::getKey)
                .collect(Collectors.toList());
    }

}
