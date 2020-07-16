package per.cby.frame.mongo.storage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Query;

import per.cby.frame.common.file.storage.bucket.BucketManager;
import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.mongo.handler.MongoDBHandler;

import lombok.RequiredArgsConstructor;

/**
 * MongoDB存储板块管理服务
 * 
 * @author chenboyang
 *
 */
@RequiredArgsConstructor
public class GridFsBucketManager implements BucketManager {

    /** 实例名称 */
    public static final String BEAN_NAME = "mongo_gridfs_bucket";

    /** 存储板块后缀 */
    private final String BUCKET_SUFFIX = ".files";

    /** MongoDB操作处理器 */
    private final MongoDBHandler mongoDBHandler;

    @Override
    public void create(String bucket) {

    }

    @Override
    public List<String> list() {
        return mongoDBHandler.getCollectionNames().stream().filter((name) -> name.endsWith(BUCKET_SUFFIX))
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(String bucket) {
        return mongoDBHandler.collectionExists(bucket + BUCKET_SUFFIX);
    }

    @Override
    public void delete(String bucket) {
        if (exists(bucket)) {
            mongoDBHandler.dropCollection(bucket + BUCKET_SUFFIX);
        }
    }

    @Override
    public long count(String bucket) {
        if (exists(bucket)) {
            return mongoDBHandler.count(new Query(), bucket + BUCKET_SUFFIX);
        }
        return 0L;
    }

    @Override
    public List<String> fileNameList(String bucket) {
        List<String> list = BaseUtil.newArrayList();
        if (exists(bucket)) {
            mongoDBHandler.find(bucket).iterator().forEachRemaining(file -> list.add(file.getFilename()));
        }
        return list;
    }

}
