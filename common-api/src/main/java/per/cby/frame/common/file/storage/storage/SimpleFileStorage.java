package per.cby.frame.common.file.storage.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import per.cby.frame.common.model.FileInfo;
import per.cby.frame.common.util.JudgeUtil;
import per.cby.frame.common.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>简单文件存储服务</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 保存文件、判断文件是否存在、删除文件、复制文件、查询文件、写入文件、获取文件文本
 * </p>
 * 
 * @author chenboyang
 * 
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class SimpleFileStorage implements SystemFileStorage {

    /** 文件存储根目录 */
    private String directory;

    /** 默认存储板块 */
    private String defaultBucket;

    /**
     * 构建简单文件存储服务
     * 
     * @param directory 文件存储根目录
     */
    public SimpleFileStorage(String directory) {
        this.directory = directory;
    }

    @Override
    public String directory() {
        return Optional.ofNullable(directory).orElse(DEF_DIR);
    }

    @Override
    public String defaultBucket() {
        return Optional.ofNullable(defaultBucket).orElse(DEF_BUC);
    }

    @Override
    public File getFile(String bucket, String name) {
        File dir = new File(directory() + "/" + bucket);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, name);
    }

    @Override
    public String relativePath(String bucket, String name) {
        return directory() + "/" + bucket + "/" + name;
    }

    @Override
    public String save(String bucket, String name, String path) {
        return save(bucket, name, new File(path));
    }

    @Override
    public String save(String name, String path) {
        return save(defaultBucket(), name, path);
    }

    @Override
    public String save(String bucket, String name, File file) {
        try {
            File newFile = getFile(bucket, name);
            newFile.createNewFile();
            FileUtils.copyFile(file, newFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return relativePath(bucket, name);
    }

    @Override
    public String save(String name, File file) {
        return save(defaultBucket(), name, file);
    }

    @Override
    public String save(String bucket, String name, InputStream input) {
        OutputStream output = null;
        try {
            File newFile = getFile(bucket, name);
            newFile.createNewFile();
            output = FileUtils.openOutputStream(newFile);
            IOUtils.copy(input, output);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return relativePath(bucket, name);
    }

    @Override
    public String save(String name, InputStream input) {
        return save(defaultBucket(), name, input);
    }

    @Override
    public boolean exist(String bucket, String name) {
        return getFile(bucket, name).exists();
    }

    @Override
    public boolean exist(String name) {
        return exist(defaultBucket(), name);
    }

    @Override
    public void remove(String bucket, String name) {
        File file = getFile(bucket, name);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void remove(String name) {
        remove(defaultBucket(), name);
    }

    @Override
    public void copy(String bucket, String sourceName, String targetName) {
        try {
            File source = getFile(bucket, sourceName);
            File target = getFile(bucket, targetName);
            if (source.exists()) {
                if (!target.exists()) {
                    target.createNewFile();
                }
                FileUtils.copyFile(source, target);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void copy(String sourceName, String targetName) {
        copy(defaultBucket(), sourceName, targetName);
    }

    @Override
    public InputStream find(String bucket, String name) {
        try {
            File source = getFile(bucket, name);
            if (source.exists()) {
                return FileUtils.openInputStream(source);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public InputStream find(String name) {
        return find(defaultBucket(), name);
    }

    @Override
    public String findText(String bucket, String name, Charset charset) {
        try {
            File file = getFile(bucket, name);
            if (file.exists()) {
                return FileUtils.readFileToString(file, charset);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String findText(String name, Charset charset) {
        return findText(defaultBucket(), name, charset);
    }

    @Override
    public String findText(String bucket, String name) {
        return findText(bucket, name, Charset.defaultCharset());
    }

    @Override
    public String findText(String name) {
        return findText(defaultBucket(), name);
    }

    @Override
    public void write(String name, OutputStream output) {
        write(defaultBucket(), name, output);
    }

    @Override
    public void write(String bucket, String name, OutputStream output) {
        write(bucket, name, output, null);
    }

    @Override
    public void write(String name, OutputStream output, Consumer<FileInfo> consumer) {
        write(defaultBucket(), name, output, consumer);
    }

    @Override
    public void write(String bucket, String name, OutputStream output, Consumer<FileInfo> consumer) {
        if (JudgeUtil.isOneNull(bucket, name, output)) {
            return;
        }
        File file = getFile(bucket, name);
        if (file.exists()) {
            if (consumer != null) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(file.getName());
                fileInfo.setPath(relativePath(bucket, name));
                fileInfo.setSuffix(StringUtil.getFileNameSuffix(fileInfo.getFileName()));
                fileInfo.setSize(file.length());
                consumer.accept(fileInfo);
            }
            InputStream input = null;
            try {
                input = FileUtils.openInputStream(file);
                IOUtils.copy(input, output);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
    }

}
