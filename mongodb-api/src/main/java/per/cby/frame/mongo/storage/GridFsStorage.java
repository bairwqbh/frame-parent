package per.cby.frame.mongo.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.file.storage.storage.FileStorage;
import per.cby.frame.common.model.FileInfo;
import per.cby.frame.common.util.StringUtil;
import per.cby.frame.mongo.handler.MongoDBHandler;
import com.mongodb.client.gridfs.model.GridFSFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>MongoDB文件存储服务接口实现类</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 保存文件、判断文件是否存在、删除文件、复制文件、查询文件、写入文件、获取文件文本
 * </p>
 * 
 * @author chenboyang
 * 
 */
@Slf4j
@RequiredArgsConstructor
public class GridFsStorage implements FileStorage {

    /** 实例名称 */
    public static final String BEAN_NAME = "mongo_gridfs_storage";

    /** MongoDB操作处理器 */
    private final MongoDBHandler mongoDBHandler;

    /**
     * 获取MongoDB操作处理器
     * 
     * @return 处理器
     */
    public MongoDBHandler handler() {
        return mongoDBHandler;
    }

    @Override
    public String save(String bucket, String name, String path) {
        return save(bucket, name, FileUtils.getFile(path));
    }

    @Override
    public String save(String name, String path) {
        return save(null, name, path);
    }

    @Override
    public String save(String bucket, String name, File file) {
        try {
            return save(bucket, name, FileUtils.openInputStream(file));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String save(String name, File file) {
        return save(null, name, file);
    }

    @Override
    public String save(String bucket, String name, InputStream input) {
        mongoDBHandler.upload(bucket, name, input);
        return null;
    }

    @Override
    public String save(String name, InputStream input) {
        return save(null, name, input);
    }

    @Override
    public boolean exist(String bucket, String name) {
        return mongoDBHandler.exist(bucket, name);
    }

    @Override
    public boolean exist(String name) {
        return exist(null, name);
    }

    @Override
    public void remove(String bucket, String name) {
        mongoDBHandler.delete(bucket, name);
    }

    @Override
    public void remove(String name) {
        remove(null, name);
    }

    @Override
    public void copy(String bucket, String sourceName, String targetName) {
        mongoDBHandler.copy(bucket, sourceName, targetName);
    }

    @Override
    public void copy(String sourceName, String targetName) {
        copy(null, sourceName, targetName);
    }

    @Override
    public InputStream find(String bucket, String name) {
        return mongoDBHandler.openDownloadStream(bucket, name);
    }

    @Override
    public InputStream find(String name) {
        return find(null, name);
    }

    @Override
    public String findText(String bucket, String name, Charset charset) {
        try {
            List<String> textList = IOUtils.readLines(find(bucket, name), charset);
            if (CollectionUtils.isNotEmpty(textList)) {
                return StringUtils.join(textList, "");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String findText(String name, Charset charset) {
        return findText(null, name, charset);
    }

    @Override
    public String findText(String bucket, String name) {
        return findText(bucket, name, Charset.defaultCharset());
    }

    @Override
    public String findText(String name) {
        return findText(null, name);
    }

    @Override
    public void write(String name, OutputStream output) {
        write(null, name, output);
    }

    @Override
    public void write(String bucket, String name, OutputStream output) {
        write(bucket, name, output, null);
    }

    @Override
    public void write(String name, OutputStream output, Consumer<FileInfo> consumer) {
        write(null, name, output, consumer);
    }

    @Override
    public void write(String bucket, String name, OutputStream output, Consumer<FileInfo> consumer) {
        BusinessAssert.isTrue(exist(bucket, name), "文件不存在！");
        GridFSFile file = mongoDBHandler.find(bucket, name);
        BusinessAssert.notNull(file, "文件不存在！");
        if (consumer != null) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getFilename());
            fileInfo.setSuffix(StringUtil.getFileNameSuffix(fileInfo.getFileName()));
            fileInfo.setSize(file.getLength());
            consumer.accept(fileInfo);
        }
        mongoDBHandler.download(bucket, name, output);
    }

}
