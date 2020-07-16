package per.cby.frame.common.file.storage.storage;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import per.cby.frame.common.model.FileInfo;

/**
 * <h1>文件存储管理基础接口</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 保存文件、判断文件是否存在、删除文件、复制文件、查询文件、写入文件、获取文件文本
 * </p>
 * 
 * @author chenboyang
 * 
 */
public interface FileStorage {

    /**
     * 保存文件
     * 
     * @param bucket 存储空间
     * @param name   名称
     * @param path   文件路径
     * @return 文件访问地址
     */
    String save(String bucket, String name, String path);

    /**
     * 默认保存文件
     * 
     * @param name 名称
     * @param path 文件路径
     * @return 文件访问地址
     */
    String save(String name, String path);

    /**
     * 保存文件
     * 
     * @param bucket 存储空间
     * @param name   名称
     * @param file   文件
     * @return 文件访问地址
     */
    String save(String bucket, String name, File file);

    /**
     * 默认保存文件
     * 
     * @param name 名称
     * @param file 文件
     * @return 文件访问地址
     */
    String save(String name, File file);

    /**
     * 保存输入流
     * 
     * @param bucket 存储空间
     * @param name   名称
     * @param input  输入流
     * @return 文件访问地址
     */
    String save(String bucket, String name, InputStream input);

    /**
     * 默认保存文件
     * 
     * @param name  名称
     * @param input 输入流
     * @return 文件访问地址
     */
    String save(String name, InputStream input);

    /**
     * 判断文件是否存在
     * 
     * @param bucket 存储空间
     * @param name   名称
     * @return 是否存在
     */
    boolean exist(String bucket, String name);

    /**
     * 默认判断文件是否存在
     * 
     * @param name 名称
     * @return 是否存在
     */
    boolean exist(String name);

    /**
     * 删除文件
     * 
     * @param bucket 存储空间
     * @param name   名称
     */
    void remove(String bucket, String name);

    /**
     * 默认删除文件
     * 
     * @param name 名称
     */
    void remove(String name);

    /**
     * 复制文件
     * 
     * @param bucket     存储空间
     * @param sourceName 源标识
     * @param targetName 目标标识
     */
    void copy(String bucket, String sourceName, String targetName);

    /**
     * 默认复制文件
     * 
     * @param sourceName 源标识
     * @param targetName 目标标识
     */
    void copy(String sourceName, String targetName);

    /**
     * 获取文件流
     * 
     * @param bucket 存储空间
     * @param name   名称
     * @return 文件流
     */
    InputStream find(String bucket, String name);

    /**
     * 默认获取文件流
     * 
     * @param name 名称
     * @return 文件流
     */
    InputStream find(String name);

    /**
     * 获取文件文本内容
     * 
     * @param bucket  存储空间
     * @param name    名称
     * @param charset 字符集编码
     * @return 文件文本内容
     */
    String findText(String bucket, String name, Charset charset);

    /**
     * 获取文件文本内容
     * 
     * @param name    名称
     * @param charset 字符集编码
     * @return 文件文本内容
     */
    String findText(String name, Charset charset);

    /**
     * 获取文件文本内容
     * 
     * @param bucket 存储空间
     * @param name   名称
     * @return 文件文本内容
     */
    String findText(String bucket, String name);

    /**
     * 默认获取文件文本内容
     * 
     * @param name 名称
     * @return 文件文本内容
     */
    String findText(String name);

    /**
     * 默认写入文件输出流
     * 
     * @param name      名称
     * @param output    文件输出流
     * @param listeners 输出文件信息监听
     */
    void write(String name, OutputStream output);

    /**
     * 写入文件输出流
     * 
     * @param bucket    存储空间
     * @param name      名称
     * @param output    文件输出流
     * @param listeners 输出文件信息监听
     */
    void write(String bucket, String name, OutputStream output);

    /**
     * 默认写入文件输出流
     * 
     * @param name     名称
     * @param output   文件输出流
     * @param consumer 文件信息消息者
     */
    void write(String name, OutputStream output, Consumer<FileInfo> consumer);

    /**
     * 写入文件输出流
     * 
     * @param bucket   存储空间
     * @param name     名称
     * @param output   文件输出流
     * @param consumer 文件信息消息者
     */
    void write(String bucket, String name, OutputStream output, Consumer<FileInfo> consumer);

}
