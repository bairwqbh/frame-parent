package per.cby.frame.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.file.storage.StorageType;
import per.cby.frame.common.file.storage.storage.FileStorage;
import per.cby.frame.common.file.storage.storage.SystemFileStorage;
import per.cby.frame.common.model.FileInfo;
import per.cby.frame.common.sys.service.SysService;
import per.cby.frame.common.useable.Useable;
import per.cby.frame.common.util.HttpUtil;
import per.cby.frame.common.util.IDUtil;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.mongo.storage.GridFsStorage;
import per.cby.frame.oss.aliyun.AliyunOssService;
import per.cby.frame.oss.qiniu.QiniuOssService;
import per.cby.frame.web.constant.WebConstant;
import per.cby.frame.web.service.ApplyService;
import per.cby.frame.web.session.Session;
import per.cby.frame.web.session.SessionManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统应用服务接口实现类
 * 
 * @author chenboyang
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public class ApplyServiceImpl implements ApplyService, WebConstant {

    @Autowired(required = false)
    private SysService sysService;

    @Autowired(required = false)
    private SessionManager sessionManager;

    /**
     * 获取请求信息属性
     * 
     * @return 请求信息属性
     */
    private ServletRequestAttributes servletRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return (ServletRequestAttributes) requestAttributes;
        }
        return null;
    }

    @Override
    public HttpServletRequest request() {
        return Optional.ofNullable(servletRequestAttributes()).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    @Override
    public HttpServletResponse response() {
        return Optional.ofNullable(servletRequestAttributes()).map(ServletRequestAttributes::getResponse).orElse(null);
    }

    @Override
    public Session session() {
        return Optional.ofNullable(sessionManager).map(SessionManager::getSession).orElse(null);
    }

    @Override
    public void setSessionAttribute(String name, Object value) {
        Optional.ofNullable(sessionManager).ifPresent(t -> t.setAttribute(name, value));
    }

    @Override
    public <T> T getSessionAttribute(String name) {
        return (T) Optional.ofNullable(session()).map(t -> t.getAttribute(name)).orElse(null);
    }

    @Override
    public <T> T getSessionAttribute(String name, Class<T> clazz) {
        return Optional.ofNullable(session()).map(t -> t.getAttribute(name)).map(t -> JsonUtil.convert(t, clazz))
                .orElse(null);
    }

    @Override
    public File uploadDir() {
        File dir = null;
        HttpSession session = Optional.ofNullable(request()).map(request -> request.getSession(false)).orElse(null);
        if (session != null) {
            String serverPath = session.getServletContext().getRealPath("/");
            String dirPath = sysService.sysProperties().getUploadDir();
            dir = FileUtils.getFile(serverPath + dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return dir;
    }

    @Override
    public File getUploadFile(String fileName) {
        return new File(uploadDir(), fileName);
    }

    @Override
    public FileInfo upload(File file, String storage, String bucket) {
        FileInfo fileInfo = null;
        try {
            if (file != null) {
                fileInfo = new FileInfo();
                fileInfo.setOriginName(file.getName());
                StringBuilder fileName = new StringBuilder(IDUtil.createUUID32());
                if (fileInfo.getOriginName().contains(".")) {
                    String suffix = fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf("."));
                    fileName.append(suffix);
                    fileInfo.setSuffix(suffix);
                }
                fileInfo.setFileName(fileName.toString());
                fileInfo.setSize(file.length());
                FileStorage fileStorage = fileStorage(storage);
                if (StringUtils.isNotBlank(bucket) && fileStorage != null) {
                    fileInfo.setPath(fileStorage.save(bucket, fileInfo.getFileName(), FileUtils.openInputStream(file)));
                } else {
                    fileInfo.setPath(fileStorage.save(fileInfo.getFileName(), FileUtils.openInputStream(file)));
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return fileInfo;
    }

    @Override
    public FileInfo upload(MultipartFile file, String storage) {
        return upload(file, null);
    }

    @Override
    public FileInfo upload(MultipartFile file, String storage, String bucket) {
        FileInfo fileInfo = null;
        try {
            if (file != null) {
                fileInfo = new FileInfo();
                fileInfo.setOriginName(file.getOriginalFilename());
                StringBuilder fileName = new StringBuilder(IDUtil.createUUID32());
                if (fileInfo.getOriginName().contains(".")) {
                    String suffix = fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf("."));
                    fileName.append(suffix);
                    fileInfo.setSuffix(suffix);
                }
                fileInfo.setFileName(fileName.toString());
                fileInfo.setSize(file.getSize());
                FileStorage fileStorage = fileStorage(storage);
                if (StringUtils.isNotBlank(bucket) && fileStorage != null) {
                    fileInfo.setPath(fileStorage.save(bucket, fileInfo.getFileName(), file.getInputStream()));
                } else {
                    fileInfo.setPath(fileStorage.save(fileInfo.getFileName(), file.getInputStream()));
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return fileInfo;
    }

    @Override
    public List<FileInfo> upload(List<MultipartFile> fileList, String storage) {
        return upload(fileList, null);
    }

    @Override
    public List<FileInfo> upload(List<MultipartFile> fileList, String storage, String bucket) {
        List<FileInfo> list = null;
        if (CollectionUtils.isNotEmpty(fileList)) {
            list = new ArrayList<FileInfo>();
            for (MultipartFile file : fileList) {
                FileInfo fileInfo = upload(file, bucket);
                if (fileInfo != null) {
                    list.add(fileInfo);
                }
            }
        }
        return list;
    }

    @Override
    public void download(String name, String storage, String bucket, HttpServletResponse response) {
        BusinessAssert.hasText(name, "文件名称为空，无法下载！");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        try {
            FileStorage fileStorage = fileStorage(storage);
            if (StringUtils.isNotBlank(bucket) && fileStorage != null) {
                fileStorage.write(bucket, name, response.getOutputStream(), fileInfoConsumer(response));
            } else {
                fileStorage.write(name, response.getOutputStream(), fileInfoConsumer(response));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 读写文件信息消息费
     * 
     * @param response 响应信息
     * @return 读写文件信息消息费
     */
    private Consumer<FileInfo> fileInfoConsumer(HttpServletResponse response) {
        return fileInfo -> {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileInfo.getFileName());
            response.setContentLengthLong(fileInfo.getSize());
        };
    }

    @Override
    public Object proxy(String url, HttpServletRequest request) {
        BusinessAssert.hasText(url, "请求地址不能为空！");
        BusinessAssert.isTrue(url.startsWith("http://") || url.startsWith("https://"), "请求地址格式错误！");
        BusinessAssert.notNull(request, "请求数据为空无法执行！");
        ServletServerHttpRequest sshr = new ServletServerHttpRequest(request);
        if (StringUtils.isNotBlank(request.getQueryString())) {
            url += "?" + request.getQueryString();
        }
        URI uri = URI.create(url);
        HttpHeaders headers = new HttpHeaders();
        sshr.getHeaders().forEach(headers::put);
        headers.remove(HttpHeaders.ACCEPT_ENCODING);
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        HttpMethod method = sshr.getMethod();
        int contentLength = (int) sshr.getHeaders().getContentLength();
        RequestEntity<?> requestEntity = null;
        if (MapUtils.isNotEmpty(request.getParameterMap())) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            Enumeration<String> names = request.getParameterNames();
            String name = null;
            while (names.hasMoreElements()) {
                name = names.nextElement();
                map.add(name, request.getParameter(name));
            }
            requestEntity = new RequestEntity<MultiValueMap<String, String>>(map, headers, method, uri);
        } else if (contentLength > 0) {
            try {
                requestEntity = new RequestEntity<byte[]>(IOUtils.readFully(sshr.getBody(), contentLength), headers,
                        method, uri);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        if (requestEntity == null) {
            requestEntity = new RequestEntity<>(headers, method, uri);
        }
        return HttpUtil.restTemplate().exchange(requestEntity, byte[].class).getBody();
    }

    @Override
    public String currToken() {
        return getToken(request());
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String token = null;
        if (request != null) {
            token = request.getHeader(TOKEN);
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter(TOKEN);
            }
        }
        return token;
    }

    @Override
    public boolean isOnline() {
        return Optional.ofNullable(sessionManager).map(SessionManager::hasSession).orElse(false);
    }

    @Override
    public void setUseable(Useable user) {
        Optional.ofNullable(sessionManager).ifPresent(t -> t.setAttribute(USER, user));
    }

    @Override
    public <T extends Useable> T getUseable() {
        return (T) Optional.ofNullable(sessionManager).map(SessionManager::getSession).map(t -> t.getAttribute(USER))
                .orElse(null);
    }

    /**
     * 获取文件存储服务
     * 
     * @param storage 存储类型
     * @return 文件存储服务
     */
    private FileStorage fileStorage(String storage) {
        switch (storage) {
            case StorageType.SYSTEM:
                return SpringContextUtil.getBean(SystemFileStorage.class);
            case StorageType.MONGO:
                return SpringContextUtil.getBean(GridFsStorage.class);
            case StorageType.ALIYUN:
                return SpringContextUtil.getBean(AliyunOssService.class);
            case StorageType.QINIU:
                return SpringContextUtil.getBean(QiniuOssService.class);
            default:
                return null;
        }
    }

}
