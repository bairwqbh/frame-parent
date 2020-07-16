package per.cby.frame.mongo.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import per.cby.frame.common.model.time.TimeContainerFactory;
import per.cby.frame.common.model.time.TimeMap;
import per.cby.frame.common.util.SystemUtil;
import per.cby.frame.mongo.constant.MongoConstant;
import per.cby.frame.mongo.service.GridFsService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;

/**
 * MongoDB处理器
 * 
 * @author chenboyang
 *
 */
public class MongoDBHandler extends MongoTemplate implements GridFsService {

    /**
     * 初始化MongoDB处理器
     * 
     * @param factory   MongoDb工厂
     * @param converter MongoDb转换器
     */
    public MongoDBHandler(MongoDbFactory factory, MongoConverter converter) {
        super(factory, converter);
    }

    /**
     * 获取存储板块
     * 
     * @param bucket 板块名称
     * @return 存储板块
     */
    private GridFSBucket getBucket(String bucket) {
        if (StringUtils.isBlank(bucket)) {
            return null;
        }
        if (!gridFsBucketMap().containsKey(bucket)) {
            gridFsBucketMap().put(bucket, GridFSBuckets.create(getDb(), bucket));
        }
        return gridFsBucketMap().get(bucket);
    }

    /**
     * 获取板块缓存
     * 
     * @return 板块缓存
     */
    private TimeMap<String, GridFSBucket> gridFsBucketMap() {
        return SystemUtil.systemStorage().getOrSet(MongoConstant.GRID_FS_BUCKET_MAP,
                () -> TimeContainerFactory.createTimeHashMap(MongoConstant.GRIDFS_OPERATE_TIMEOUT));
    }

    @Override
    public ObjectId upload(String bucket, String filename, InputStream input) {
        return getBucket(bucket).uploadFromStream(filename, input);
    }

    @Override
    public void download(String bucket, String filename, OutputStream output) {
        getBucket(bucket).downloadToStream(filename, output);
    }

    @Override
    public void delete(String bucket, String filename) {
        Optional.ofNullable(find(bucket, filename)).ifPresent(f -> getBucket(bucket).delete(f.getObjectId()));
    }

    @Override
    public void drop(String bucket) {
        getBucket(bucket).drop();
    }

    @Override
    public boolean exist(String bucket, String filename) {
        return find(bucket, filename) != null;
    }

    @Override
    public GridFSFindIterable find(String bucket) {
        return getBucket(bucket).find();
    }

    @Override
    public GridFSFile find(String bucket, String filename) {
        return getBucket(bucket).find(Filters.eq(MongoConstant.FILENAME_KEY, filename)).first();
    }

    @Override
    public GridFSDownloadStream openDownloadStream(String bucket, String filename) {
        return getBucket(bucket).openDownloadStream(filename);
    }

    @Override
    public GridFSUploadStream openUploadStream(String bucket, String filename) {
        return getBucket(bucket).openUploadStream(filename);
    }

    @Override
    public void copy(String bucket, String sourceName, String targetName) {
        GridFSBucket gridFSBucket = getBucket(bucket);
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(sourceName);
        gridFSBucket.uploadFromStream(targetName, gridFSDownloadStream);
    }

}
