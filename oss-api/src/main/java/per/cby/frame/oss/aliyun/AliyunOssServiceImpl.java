package per.cby.frame.oss.aliyun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AbortMultipartUploadRequest;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListMultipartUploadsRequest;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectAcl;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import per.cby.frame.common.model.FileInfo;
import per.cby.frame.common.util.JudgeUtil;
import per.cby.frame.common.util.StringUtil;
import per.cby.frame.oss.aliyun.config.properties.AliyunOssProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>阿里云OSS存储服务接口实现类</h1>
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
@Slf4j
@RequiredArgsConstructor
public class AliyunOssServiceImpl implements AliyunOssService {

    /** 默认存储板块 */
    private final String DEFAULT = "default";

    /** 阿里云OSS对象存储配置属性 */
    private final AliyunOssProperties properties;

    @Override
    public OSSClient getClient() {
        return new OSSClient(properties.getEndpoint(), properties.getAccessId(), properties.getAccessKey());
    }

    @Override
    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;
        if (!doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            bucket = client.createBucket(bucketName);
            client.shutdown();
        }
        return bucket;
    }

    @Override
    public List<Bucket> listBuckets() {
        OSSClient client = getClient();
        List<Bucket> list = client.listBuckets();
        client.shutdown();
        return list;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        OSSClient client = getClient();
        boolean isExist = client.doesBucketExist(bucketName);
        client.shutdown();
        return isExist;
    }

    @Override
    public void deleteBucket(String bucketName) {
        if (doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            client.deleteBucket(bucketName);
            client.shutdown();
        }
    }

    @Override
    public void setBucketAcl(String bucketName, CannedAccessControlList cannedACL) {
        if (doesBucketExist(bucketName) && cannedACL != null) {
            OSSClient client = getClient();
            client.setBucketAcl(bucketName, cannedACL);
            client.shutdown();
        }
    }

    @Override
    public AccessControlList getBucketAcl(String bucketName) {
        AccessControlList accessControlList = null;
        if (doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            accessControlList = client.getBucketAcl(bucketName);
            client.shutdown();
        }
        return accessControlList;
    }

    @Override
    public String getBucketLocation(String bucketName) {
        String location = null;
        if (doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            location = client.getBucketLocation(bucketName);
            client.shutdown();
        }
        return location;
    }

    @Override
    public BucketInfo getBucketInfo(String bucketName) {
        BucketInfo bucketInfo = null;
        if (doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            bucketInfo = client.getBucketInfo(bucketName);
            client.shutdown();
        }
        return bucketInfo;
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file) {
        return putObject(bucketName, key, file, null);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file, ObjectMetadata metadata) {
        PutObjectResult putObjectResult = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && file.exists()) {
            OSSClient client = getClient();
            putObjectResult = client.putObject(bucketName, key, file, metadata);
            client.shutdown();
        }
        return putObjectResult;
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input) {
        return putObject(bucketName, key, input, null);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        PutObjectResult putObjectResult = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && input != null) {
            OSSClient client = getClient();
            putObjectResult = client.putObject(bucketName, key, input, metadata);
            client.shutdown();
        }
        return putObjectResult;
    }

    @Override
    public AppendObjectResult appendObject(String bucketName, String key, File file,
            AppendObjectResult appendObjectResult) {
        return appendObject(bucketName, key, file, appendObjectResult, null);
    }

    @Override
    public AppendObjectResult appendObject(String bucketName, String key, File file,
            AppendObjectResult appendObjectResult, ObjectMetadata metadata) {
        AppendObjectResult result = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && file.exists()) {
            OSSClient client = getClient();
            AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key, file, metadata);
            if (appendObjectResult != null) {
                appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
            } else {
                appendObjectRequest.setPosition(0L);
            }
            result = client.appendObject(appendObjectRequest);
            client.shutdown();
        }
        return result;
    }

    @Override
    public AppendObjectResult appendObject(String bucketName, String key, InputStream input,
            AppendObjectResult appendObjectResult) {
        return appendObject(bucketName, key, input, appendObjectResult, null);
    }

    @Override
    public AppendObjectResult appendObject(String bucketName, String key, InputStream input,
            AppendObjectResult appendObjectResult, ObjectMetadata metadata) {
        AppendObjectResult result = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && input != null) {
            OSSClient client = getClient();
            AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key, input, metadata);
            if (appendObjectResult != null) {
                appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
            } else {
                appendObjectRequest.setPosition(0L);
            }
            result = client.appendObject(appendObjectRequest);
            client.shutdown();
        }
        return result;
    }

    @Override
    public UploadFileResult uploadFile(String bucketName, String key, String uploadFile) {
        UploadFileResult result = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(uploadFile)) {
            OSSClient client = getClient();
            try {
                UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
                uploadFileRequest.setUploadFile(uploadFile);
                uploadFileRequest.setTaskNum(properties.getTaskNum());
                uploadFileRequest.setPartSize(properties.getPartSize());
                uploadFileRequest.setEnableCheckpoint(true);
                result = client.uploadFile(uploadFileRequest);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
            client.shutdown();
        }
        return result;
    }

    @Override
    public ObjectListing listObjects(String bucketName) {
        return listObjects(bucketName, null);
    }

    @Override
    public ObjectListing listObjects(String bucketName, String prefix) {
        ObjectListing objectListing = null;
        if (doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            objectListing = client.listObjects(bucketName, prefix);
            client.shutdown();
        }
        return objectListing;
    }

    @Override
    public boolean doesObjectExist(String bucketName, String key) {
        boolean isExist = false;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key)) {
            OSSClient client = getClient();
            isExist = client.doesObjectExist(bucketName, key);
            client.shutdown();
        }
        return isExist;
    }

    @Override
    public void setObjectAcl(String bucketName, String key, CannedAccessControlList cannedACL) {
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && cannedACL != null) {
            OSSClient client = getClient();
            client.setObjectAcl(bucketName, key, cannedACL);
            client.shutdown();
        }
    }

    @Override
    public ObjectAcl getObjectAcl(String bucketName, String key) {
        ObjectAcl result = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key)) {
            OSSClient client = getClient();
            result = client.getObjectAcl(bucketName, key);
            client.shutdown();
        }
        return result;
    }

    @Override
    public SimplifiedObjectMeta getSimplifiedObjectMeta(String bucketName, String key) {
        SimplifiedObjectMeta result = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key)) {
            OSSClient client = getClient();
            result = client.getSimplifiedObjectMeta(bucketName, key);
            client.shutdown();
        }
        return result;
    }

    @Override
    public ObjectMetadata getObjectMetadata(String bucketName, String key) {
        ObjectMetadata result = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key)) {
            OSSClient client = getClient();
            result = client.getObjectMetadata(bucketName, key);
            client.shutdown();
        }
        return result;
    }

    @Override
    public OSSObject getObject(String bucketName, String key, OSSClient client) {
        OSSObject object = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && doesObjectExist(bucketName, key)
                && client != null) {
            object = client.getObject(bucketName, key);
        }
        return object;
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && doesObjectExist(bucketName, key)) {
            OSSClient client = getClient();
            client.deleteObject(bucketName, key);
            client.shutdown();
        }
    }

    @Override
    public DeleteObjectsResult deleteObjects(String bucketName) {
        DeleteObjectsResult deleteObjectsResult = null;
        if (doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName));
            client.shutdown();
        }
        return deleteObjectsResult;
    }

    @Override
    public CopyObjectResult copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        CopyObjectResult copyObjectResult = null;
        if (doesBucketExist(srcBucketName) && StringUtils.isNotBlank(srcKey) && doesBucketExist(destBucketName)
                && StringUtils.isNotBlank(destKey) && doesObjectExist(srcBucketName, srcKey)) {
            OSSClient client = getClient();
            copyObjectResult = client.copyObject(srcBucketName, srcKey, destBucketName, destKey);
            client.shutdown();
        }
        return copyObjectResult;
    }

    @Override
    public CompleteMultipartUploadResult multipartUpload(String bucketName, String key, File file) {
        CompleteMultipartUploadResult completeMultipartUploadResult = null;
        OSSClient client = getClient();
        try {
            if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && file.exists()) {
                InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
                        bucketName, key);
                InitiateMultipartUploadResult initiateMultipartUploadResult = client
                        .initiateMultipartUpload(initiateMultipartUploadRequest);
                List<PartETag> partETags = new ArrayList<PartETag>();
                int partCount = (int) (file.length() / properties.getPartSize());
                if (file.length() % properties.getPartSize() != 0) {
                    partCount++;
                }
                for (int i = 0; i < partCount; i++) {
                    FileInputStream input = new FileInputStream(file);
                    long skipBytes = properties.getPartSize() * i;
                    input.skip(skipBytes);
                    long size = properties.getPartSize() < file.length() - skipBytes ? properties.getPartSize()
                            : file.length() - skipBytes;
                    UploadPartRequest uploadPartRequest = new UploadPartRequest();
                    uploadPartRequest.setBucketName(bucketName);
                    uploadPartRequest.setKey(key);
                    uploadPartRequest.setUploadId(initiateMultipartUploadResult.getUploadId());
                    uploadPartRequest.setInputStream(input);
                    uploadPartRequest.setPartSize(size);
                    uploadPartRequest.setPartNumber(i + 1);
                    UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
                    partETags.add(uploadPartResult.getPartETag());
                    input.close();
                }
                CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                        bucketName, key, initiateMultipartUploadResult.getUploadId(), partETags);
                completeMultipartUploadResult = client.completeMultipartUpload(completeMultipartUploadRequest);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        client.shutdown();
        return completeMultipartUploadResult;
    }

    @Override
    public void abortMultipartUpload(String bucketName, String key, String uploadId) {
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(uploadId)) {
            OSSClient client = getClient();
            AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucketName, key,
                    uploadId);
            client.abortMultipartUpload(abortMultipartUploadRequest);
            client.shutdown();
        }
    }

    @Override
    public MultipartUploadListing listMultipartUploads(String bucketName) {
        MultipartUploadListing multipartUploadListing = null;
        if (doesBucketExist(bucketName)) {
            OSSClient client = getClient();
            multipartUploadListing = client.listMultipartUploads(new ListMultipartUploadsRequest(bucketName));
            client.shutdown();
        }
        return multipartUploadListing;
    }

    @Override
    public PartListing listParts(String bucketName, String key, String uploadId) {
        PartListing partListing = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(uploadId)) {
            OSSClient client = getClient();
            ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
            partListing = client.listParts(listPartsRequest);
            client.shutdown();
        }
        return partListing;
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key) {
        return generatePresignedUrl(bucketName, key, null, HttpMethod.GET);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration) {
        return generatePresignedUrl(bucketName, key, expiration, HttpMethod.GET);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method) {
        URL url = null;
        if (doesBucketExist(bucketName) && StringUtils.isNotBlank(key)) {
            OSSClient client = getClient();
            if (expiration == null) {
                expiration = new Date(new Date().getTime() + properties.getTimeLength());
            }
            url = client.generatePresignedUrl(bucketName, key, expiration, method);
            client.shutdown();
        }
        return url;
    }

    @Override
    public String save(String bucket, String name, String path) {
        putObject(bucket, name, new File(path));
        URL url = generatePresignedUrl(bucket, name);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    @Override
    public String save(String name, String path) {
        return save(DEFAULT, name, path);
    }

    @Override
    public String save(String bucket, String name, File file) {
        putObject(bucket, name, file);
        URL url = generatePresignedUrl(bucket, name);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    @Override
    public String save(String name, File file) {
        return save(DEFAULT, name, file);
    }

    @Override
    public String save(String bucket, String name, InputStream input) {
        putObject(bucket, name, input);
        URL url = generatePresignedUrl(bucket, name);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    @Override
    public String save(String name, InputStream input) {
        return save(DEFAULT, name, input);
    }

    @Override
    public boolean exist(String bucket, String name) {
        return doesObjectExist(bucket, name);
    }

    @Override
    public boolean exist(String name) {
        return exist(DEFAULT, name);
    }

    @Override
    public void remove(String bucket, String name) {
        deleteObject(bucket, name);
    }

    @Override
    public void remove(String name) {
        remove(DEFAULT, name);
    }

    @Override
    public void copy(String bucket, String sourceName, String targetName) {
        copyObject(bucket, sourceName, bucket, targetName);
    }

    @Override
    public void copy(String sourceName, String targetName) {
        copy(DEFAULT, sourceName, targetName);
    }

    @Override
    public InputStream find(String bucket, String name) {
        OSSClient client = getClient();
        OSSObject object = getObject(bucket, name, client);
        if (object != null) {
            File tmpFile = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                tmpFile = File.createTempFile(name, ".tmp");
                input = object.getObjectContent();
                output = FileUtils.openOutputStream(tmpFile);
                IOUtils.copy(input, output);
                return FileUtils.openInputStream(tmpFile);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        client.shutdown();
        return null;
    }

    @Override
    public InputStream find(String name) {
        return find(DEFAULT, name);
    }

    @Override
    public String findText(String bucket, String name, Charset charset) {
        OSSClient client = getClient();
        OSSObject object = getObject(bucket, name, client);
        if (object != null) {
            InputStream input = null;
            try {
                input = object.getObjectContent();
                List<String> textList = IOUtils.readLines(input, charset);
                if (CollectionUtils.isNotEmpty(textList)) {
                    return StringUtils.join(textList, "");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        client.shutdown();
        return null;
    }

    @Override
    public String findText(String name, Charset charset) {
        return findText(DEFAULT, name, charset);
    }

    @Override
    public String findText(String bucket, String name) {
        return findText(bucket, name, Charset.defaultCharset());
    }

    @Override
    public String findText(String name) {
        return findText(DEFAULT, name);
    }

    @Override
    public void write(String name, OutputStream output) {
        write(DEFAULT, name, output);
    }

    @Override
    public void write(String bucket, String name, OutputStream output) {
        write(bucket, name, output, null);
    }

    @Override
    public void write(String name, OutputStream output, Consumer<FileInfo> consumer) {
        write(DEFAULT, name, output, consumer);
    }

    @Override
    public void write(String bucket, String name, OutputStream output, Consumer<FileInfo> consumer) {
        if (JudgeUtil.isOneNull(bucket, name, output)) {
            return;
        }
        OSSClient client = getClient();
        OSSObject object = getObject(bucket, name, client);
        if (object != null) {
            if (consumer != null) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(object.getKey());
                fileInfo.setPath(
                        Optional.ofNullable(generatePresignedUrl(bucket, name)).map(URL::toString).orElse(null));
                fileInfo.setSuffix(StringUtil.getFileNameSuffix(fileInfo.getFileName()));
                fileInfo.setSize(object.getResponse().getContentLength());
                consumer.accept(fileInfo);
            }
            InputStream input = object.getObjectContent();
            try {
                IOUtils.copy(input, output);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
        client.shutdown();
    }

}
