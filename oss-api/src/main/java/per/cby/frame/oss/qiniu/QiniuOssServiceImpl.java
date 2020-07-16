package per.cby.frame.oss.qiniu;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import per.cby.frame.common.model.FileInfo;
import per.cby.frame.common.util.HttpUtil;
import per.cby.frame.common.util.StringUtil;
import per.cby.frame.oss.qiniu.config.properties.QiniuOssProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UpCompletionHandler;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 七牛云OSS存储服务接口实现类
 * 
 * @author chenboyang
 * 
 */
@Slf4j
public class QiniuOssServiceImpl implements QiniuOssService {

    /** 默认存储板块 */
    private final String DEFAULT = "default";

    /** 字符集编码 */
    private final String CHARSET = "UTF-8";

    /** 七牛云OSS对象存储配置属性 */
    private QiniuOssProperties properties;

    /** 服务配置 */
    private Configuration config;

    /** 权限认证 */
    private Auth auth;

    /** 存储管理器 */
    private UploadManager uploadManager;

    @Autowired(required = false)
    private QiniuOssBucketService bucketService;

    /**
     * 实例化七牛云OSS存储服务接口实现类
     * 
     * @param properties 七牛云OSS对象存储配置属性
     */
    public QiniuOssServiceImpl(QiniuOssProperties properties) {
        this.properties = properties;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        QiniuOssProperties.validate(properties);
        config = new Configuration(properties.getZone());
        auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
        uploadManager = new UploadManager(config);
        if (bucketService == null) {
            bucketService = new QiniuOssBucketManager(properties);
        }
    }

    @Override
    public Response put(byte[] data, String key, String token, StringMap params, String mime, boolean checkCrc) {
        try {
            return uploadManager.put(data, key, token, params, mime, checkCrc);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response put(File file, String key, String token, StringMap params, String mime, boolean checkCrc) {
        try {
            return uploadManager.put(file, key, token, params, mime, checkCrc);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void asyncPut(byte[] data, String key, String token, StringMap params, String mime, boolean checkCrc,
            UpCompletionHandler handler) {
        try {
            uploadManager.asyncPut(data, key, token, params, mime, checkCrc, handler);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Response put(InputStream stream, String key, String token, StringMap params, String mime) {
        try {
            return uploadManager.put(stream, key, token, params, mime);
        } catch (QiniuException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取存储板块上传token
     * 
     * @param bucket 存储板块名称
     * @return token
     */
    private String uploadToken(String bucket) {
        return auth.uploadToken(bucket);
    }

    /**
     * 获取预签名地址
     * 
     * @param bucket 存储板块名称
     * @param name   文件名
     * @return 预签名地址
     */
    private String privateDownloadUrl(String bucket, String name) {
        List<String> list = bucketService.domainList(bucket);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        try {
            name = URLEncoder.encode(name, CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return auth.privateDownloadUrl(String.format("%s/%s", list.get(0), name), properties.getTimeLength());
    }

    /**
     * 检查或创建存储板块
     * 
     * @param bucket 存储板块名称
     */
    private void checkOrCreateBucket(String bucket) {
        if (bucketService.exists(bucket)) {
            return;
        }
        bucketService.create(bucket);
    }

    @Override
    public String save(String bucket, String name, String path) {
        checkOrCreateBucket(bucket);
        if (put(path, name, uploadToken(bucket)).isOK()) {
            return privateDownloadUrl(bucket, name);
        }
        return null;
    }

    @Override
    public String save(String name, String path) {
        return save(DEFAULT, name, path);
    }

    @Override
    public String save(String bucket, String name, File file) {
        checkOrCreateBucket(bucket);
        if (put(file, name, uploadToken(bucket)).isOK()) {
            return privateDownloadUrl(bucket, name);
        }
        return null;
    }

    @Override
    public String save(String name, File file) {
        return save(DEFAULT, name, file);
    }

    @Override
    public String save(String bucket, String name, InputStream input) {
        checkOrCreateBucket(bucket);
        if (put(input, name, uploadToken(bucket), null, null).isOK()) {
            return privateDownloadUrl(bucket, name);
        }
        return null;
    }

    @Override
    public String save(String name, InputStream input) {
        return save(DEFAULT, name, input);
    }

    @Override
    public boolean exist(String bucket, String name) {
        return bucketService.stat(bucket, name) != null;
    }

    @Override
    public boolean exist(String name) {
        return exist(DEFAULT, name);
    }

    @Override
    public void remove(String bucket, String name) {
        bucketService.delete(bucket, name);
    }

    @Override
    public void remove(String name) {
        remove(DEFAULT, name);
    }

    @Override
    public void copy(String bucket, String sourceName, String targetName) {
        bucketService.copy(bucket, sourceName, bucket, targetName);
    }

    @Override
    public void copy(String sourceName, String targetName) {
        copy(DEFAULT, sourceName, targetName);
    }

    @Override
    public InputStream find(String bucket, String name) {
        ResponseEntity<byte[]> response = HttpUtil.restTemplate().getForEntity(privateDownloadUrl(bucket, name),
                byte[].class);
        if (!response.hasBody()) {
            return null;
        }
        return new ByteArrayInputStream(response.getBody());
    }

    @Override
    public InputStream find(String name) {
        return find(DEFAULT, name);
    }

    @Override
    public String findText(String bucket, String name, Charset charset) {
        try (InputStream input = find(bucket, name)) {
            if (input == null) {
                return null;
            }
            List<String> textList = IOUtils.readLines(input, charset);
            if (CollectionUtils.isEmpty(textList)) {
                return "";
            }
            return StringUtils.join(textList, "");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
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
        try (InputStream input = find(bucket, name)) {
            if (input == null) {
                return;
            }
            if (consumer != null) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(name);
                fileInfo.setPath(privateDownloadUrl(bucket, name));
                fileInfo.setSuffix(StringUtil.getFileNameSuffix(name));
                com.qiniu.storage.model.FileInfo sourceFileInfo = bucketService.stat(bucket, name);
                if (sourceFileInfo != null) {
                    fileInfo.setSize(sourceFileInfo.fsize);
                }
                consumer.accept(fileInfo);
            }
            IOUtils.copy(input, output);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
