package per.cby.frame.oss.qiniu;

import java.io.File;
import java.io.InputStream;

import per.cby.frame.common.file.storage.storage.FileStorage;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UpCompletionHandler;
import com.qiniu.util.StringMap;

/**
 * 七牛云OSS存储服务接口
 * 
 * @author chenboyang
 * 
 */
public interface QiniuOssService extends FileStorage {

    /** 实例名称 */
    String BEAN_NAME = "qiniu_oss_storage";

    /**
     * 上传字节数组
     *
     * @param data  上传的数据
     * @param key   上传数据保存的文件名
     * @param token 上传凭证
     * @return 响应结果
     */
    default Response put(final byte[] data, final String key, final String token) {
        return put(data, key, token, null, null, false);
    }

    /**
     * 上传字节数组
     *
     * @param data     上传的数据
     * @param key      上传数据保存的文件名
     * @param token    上传凭证
     * @param params   自定义参数，如 params.put("x:foo", "foo")
     * @param mime     指定文件mimetype
     * @param checkCrc 是否验证crc32
     * @return
     * @throws QiniuException
     * @return 响应结果
     */
    Response put(final byte[] data, final String key, final String token, StringMap params, String mime,
            boolean checkCrc);

    /**
     * 上传文件
     *
     * @param filePath 上传的文件路径
     * @param key      上传文件保存的文件名
     * @param token    上传凭证
     * @return 响应结果
     */
    default Response put(String filePath, String key, String token) {
        return put(filePath, key, token, null, null, false);
    }

    /**
     * 上传文件
     *
     * @param filePath 上传的文件路径
     * @param key      上传文件保存的文件名
     * @param token    上传凭证
     * @param params   自定义参数，如 params.put("x:foo", "foo")
     * @param mime     指定文件mimetype
     * @param checkCrc 是否验证crc32
     * @return 响应结果
     */
    default Response put(String filePath, String key, String token, StringMap params, String mime, boolean checkCrc) {
        return put(new File(filePath), key, token, params, mime, checkCrc);
    }

    /**
     * 上传文件
     *
     * @param file  上传的文件对象
     * @param key   上传文件保存的文件名
     * @param token 上传凭证
     * @return 响应结果
     */
    default Response put(File file, String key, String token) {
        return put(file, key, token, null, null, false);
    }

    /**
     * 上传文件
     *
     * @param file     上传的文件对象
     * @param key      上传文件保存的文件名
     * @param token    上传凭证
     * @param mime     指定文件mimetype
     * @param checkCrc 是否验证crc32
     * @return 响应结果
     */
    Response put(File file, String key, String token, StringMap params, String mime, boolean checkCrc);

    /**
     * 异步上传数据
     *
     * @param data     上传的数据
     * @param key      上传数据保存的文件名
     * @param token    上传凭证
     * @param params   自定义参数，如 params.put("x:foo", "foo")
     * @param mime     指定文件mimetype
     * @param checkCrc 是否验证crc32
     * @param handler  上传完成的回调函数
     */
    void asyncPut(final byte[] data, final String key, final String token, StringMap params, String mime,
            boolean checkCrc, UpCompletionHandler handler);

    /**
     * 流式上传，通常情况建议文件上传，可以使用持久化的断点记录。
     *
     * @param stream sha
     * @param key    上传文件保存的文件名
     * @param token  上传凭证
     * @param params 自定义参数，如 params.put("x:foo", "foo")
     * @param mime   指定文件mimetype
     * @return 响应结果
     */
    Response put(InputStream stream, String key, String token, StringMap params, String mime);

}
