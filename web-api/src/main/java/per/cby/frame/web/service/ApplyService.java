package per.cby.frame.web.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import per.cby.frame.common.model.FileInfo;
import per.cby.frame.common.useable.Useable;
import per.cby.frame.web.session.Session;

/**
 * 系统应用服务接口
 * 
 * @author chenboyang
 *
 */
public interface ApplyService {

    /**
     * 获取当前请求信息
     * 
     * @return 请求信息
     */
    HttpServletRequest request();

    /**
     * 获取当前响应信息
     * 
     * @return 响应信息
     */
    HttpServletResponse response();

    /**
     * 获取当前会话信息
     * 
     * @return 会话信息
     */
    Session session();

    /**
     * 设置会话业务属性
     * 
     * @param name  属性名称
     * @param value 属性值
     */
    void setSessionAttribute(String name, Object value);

    /**
     * 获取会话业务属性
     * 
     * @param <T>  业务类型
     * @param name 属性名称
     * @return 属性值
     */
    <T> T getSessionAttribute(String name);

    /**
     * 获取会话业务属性
     * 
     * @param <T>   业务类型
     * @param name  属性名称
     * @param clazz 业务类型信息
     * @return 属性值
     */
    <T> T getSessionAttribute(String name, Class<T> clazz);

    /**
     * 获取系统文件上传目录
     * 
     * @return 目录
     */
    File uploadDir();

    /**
     * 根据文件名从文件上传目录获取文件
     * 
     * @param fileName 文件名
     * @return 文件
     */
    File getUploadFile(String fileName);

    /**
     * 普通文件上传
     * 
     * @param file    文件
     * @param storage 存储类型
     * @return 保存信息
     */
    default FileInfo upload(File file, String storage) {
        return upload(file, storage, null);
    }

    /**
     * 普通文件上传
     * 
     * @param file    文件
     * @param storage 存储类型
     * @param bucket  存储板块
     * @return 保存信息
     */
    FileInfo upload(File file, String storage, String bucket);

    /**
     * SpringWeb文件上传
     * 
     * @param file    文件
     * @param storage 存储类型
     * @return 保存信息
     */
    default FileInfo upload(MultipartFile file, String storage) {
        return upload(file, storage, null);
    }

    /**
     * SpringWeb文件上传
     * 
     * @param file    文件
     * @param storage 存储类型
     * @param bucket  存储板块
     * @return 保存信息
     */
    FileInfo upload(MultipartFile file, String storage, String bucket);

    /**
     * SpringWeb文件批量上传
     * 
     * @param fileList 文件列表
     * @param storage  存储类型
     * @return 保存信息
     */
    List<FileInfo> upload(List<MultipartFile> fileList, String storage);

    /**
     * SpringWeb文件批量上传
     * 
     * @param fileList 文件列表
     * @param storage  存储类型
     * @param bucket   存储板块
     * @return 保存信息
     */
    List<FileInfo> upload(List<MultipartFile> fileList, String storage, String bucket);

    /**
     * 系统统一文件下载
     * 
     * @param name     文件名称
     * @param storage  存储类型
     * @param bucket   存储模块名称
     * @param response 响应信息
     */
    void download(String name, String storage, String bucket, HttpServletResponse response);

    /**
     * HTTP反向代理服务
     * 
     * @param url     请求地址
     * @param request 请求信息
     * @return 响应结果
     */
    Object proxy(String url, HttpServletRequest request);

    /**
     * 获取当前token
     * 
     * @return token
     */
    String currToken();

    /**
     * 从请求信息中获取token值
     * 
     * @param request 请求信息
     * @return token值
     */
    String getToken(HttpServletRequest request);

    /**
     * 当前会话是否在线
     * 
     * @return 是否在线
     */
    boolean isOnline();

    /**
     * 设置当前会话用户信息
     * 
     * @param user 用户信息
     */
    void setUseable(Useable user);

    /**
     * 获取当前会话用户信息
     * 
     * @return 用户信息
     */
    @Deprecated
    <T extends Useable> T getUseable();

}
