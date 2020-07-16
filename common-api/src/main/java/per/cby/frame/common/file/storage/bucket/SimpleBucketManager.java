package per.cby.frame.common.file.storage.bucket;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import per.cby.frame.common.file.storage.storage.SystemFileStorage;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 简单存储板块管理服务
 * 
 * @author chenboyang
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBucketManager implements BucketManager {

    /** 实例名称 */
    public static final String BEAN_NAME = "system_file_bucket";

    /** 文件存储根目录 */
    private String directory;

    /**
     * 文件存储根目录
     * 
     * @return 目录
     */
    public String directory() {
        return Optional.ofNullable(directory).orElse(SystemFileStorage.DEF_DIR);
    }

    @Override
    public void create(String bucket) {
        File file = new File(directory() + "/" + bucket);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public List<String> list() {
        File file = new File(directory());
        if (file.exists()) {
            Arrays.asList(file.list());
        }
        return null;
    }

    @Override
    public boolean exists(String bucket) {
        return new File(directory() + "/" + bucket).exists();
    }

    @Override
    public void delete(String bucket) {
        File file = new File(directory() + "/" + bucket);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public long count(String bucket) {
        File file = new File(directory() + "/" + bucket);
        if (file.exists()) {
            return file.list().length;
        }
        return 0;
    }

    @Override
    public List<String> fileNameList(String bucket) {
        File file = new File(directory() + "/" + bucket);
        if (file.exists()) {
            Arrays.asList(file.list());
        }
        return null;
    }

}
