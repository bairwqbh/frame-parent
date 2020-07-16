package per.cby.frame.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import per.cby.frame.common.config.properties.SysProperties;
import per.cby.frame.common.model.FileInfo;
import per.cby.frame.common.sys.service.SysService;
import per.cby.frame.web.annotation.NotResultWrap;
import per.cby.frame.web.service.ApplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统应用控制器
 * 
 * @author chenboyang
 *
 */
@Api(value = "应用程序Controller", tags = "应用程序前端控制器接口")
public class ApplyController {

    @Autowired(required = false)
    private SysService sysService;

    @Autowired(required = false)
    private ApplyService applyService;

    /**
     * 获取系统默认信息配置属性
     * 
     * @return 配置属性
     */
    @GetMapping("/systemConfig")
    @ApiOperation("获取系统默认信息配置属性")
    public SysProperties systemConfig() {
        return sysService.sysProperties();
    }

    /**
     * 系统统一文件上传
     * 
     * @param file   文件
     * @param bucket 存储板块名称
     * @return 文件保存信息
     */
    @PostMapping("/upload")
    @ApiOperation("系统统一文件上传")
    public FileInfo upload(MultipartFile file, @ApiParam("存储类型") @RequestParam(required = false) String storage,
            @ApiParam("存储板块名称") @RequestParam(required = false) String bucket) {
        return applyService.upload(file, storage, bucket);
    }

    /**
     * 系统统一文件批量上传
     * 
     * @param request 请求信息
     * @param bucket  存储板块名称
     * @return 文件保存信息
     */
    @PostMapping("/batchUpload")
    @ApiOperation("系统统一文件批量上传")
    public List<FileInfo> batchUpload(HttpServletRequest request,
            @ApiParam("存储类型") @RequestParam(required = false) String storage,
            @ApiParam("存储板块名称") @RequestParam(required = false) String bucket) {
        List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
        return applyService.upload(fileList, storage, bucket);
    }

    /**
     * 系统统一文件下载
     * 
     * @param name     文件名称
     * @param bucket   存储模块名称
     * @param response 响应信息
     */
    @GetMapping("/download")
    @ApiOperation("系统统一文件下载")
    public void download(@ApiParam(value = "文件名称", required = true) @RequestParam String name,
            @ApiParam("存储类型") @RequestParam(required = false) String storage,
            @ApiParam("存储板块名称") @RequestParam(required = false) String bucket, HttpServletResponse response) {
        applyService.download(name, storage, bucket, response);
    }

    /**
     * HTTP反向代理服务
     * 
     * @param url     请求地址
     * @param request 请求信息
     * @return 响应结果
     */
    @NotResultWrap
    @RequestMapping("/proxy/**")
    @ApiOperation("HTTP反向代理服务")
    public Object proxy(HttpServletRequest request) {
        String url = request.getRequestURI().replaceFirst("/proxy/", "");
        return applyService.proxy(url, request);
    }

}
