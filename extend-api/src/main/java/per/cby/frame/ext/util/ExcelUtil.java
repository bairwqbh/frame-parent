package per.cby.frame.ext.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import per.cby.frame.ext.excel.ExcelBuildService;
import per.cby.frame.ext.excel.ExcelExportService;
import per.cby.frame.ext.excel.ExcelReadService;
import per.cby.frame.ext.excel.ExcelServiceBuilder;

import lombok.experimental.UtilityClass;

/**
 * Excel帮助类
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
public class ExcelUtil {

    /**
     * 获取Excel构建服务
     * 
     * @return Excel构建服务
     */
    public ExcelBuildService excelBuildService() {
        return ExcelServiceBuilder.excelBuildService();
    }

    /**
     * 获取Excel导出服务
     * 
     * @return Excel导出服务
     */
    public ExcelExportService excelExportService() {
        return ExcelServiceBuilder.excelExportService();
    }

    /**
     * 获取Excel读取服务
     * 
     * @return Excel读取服务
     */
    public ExcelReadService excelReadService() {
        return ExcelServiceBuilder.excelReadService();
    }

    /**
     * 构建EXCEL
     * 
     * @param head 表头信息
     * @param body 主体内容
     * @return EXCEL文本信息
     */
    public HSSFWorkbook build(List<?> head, List<? extends List<?>> body) {
        return excelBuildService().build(head, body);
    }

    /**
     * 构建EXCEL
     * 
     * @param head     表头信息
     * @param body     主体内容
     * @param consumer Sheet消息者
     * @return EXCEL文本信息
     */
    public HSSFWorkbook build(List<?> head, List<? extends List<?>> body, Consumer<HSSFSheet> consumer) {
        return excelBuildService().build(head, body, consumer);
    }

    /**
     * 构建EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param book      文本信息
     * @param headStyle 表头样式
     * @param bodyStyle 主体样式
     * @return EXCEL文本信息
     */
    public HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, HSSFCellStyle headStyle,
            HSSFCellStyle bodyStyle) {
        return excelBuildService().build(head, body, book, headStyle, bodyStyle);
    }

    /**
     * 构建EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param book      文本信息
     * @param headStyle 表头样式
     * @param bodyStyle 主体样式
     * @param consumer  Sheet消息者
     * @return EXCEL文本信息
     */
    public HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, HSSFCellStyle headStyle,
            HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer) {
        return excelBuildService().build(head, body, book, headStyle, bodyStyle, consumer);
    }

    /**
     * 构建EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param book      文本信息
     * @param sheetName 片名称
     * @return EXCEL文本信息
     */
    public HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, String sheetName) {
        return excelBuildService().build(head, body, book, sheetName);
    }

    /**
     * 构建EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param book      文本信息
     * @param sheetName 片名称
     * @param headStyle 表头样式
     * @param bodyStyle 主体样式
     * @param consumer  Sheet消息者
     * @return EXCEL文本信息
     */
    public HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, String sheetName,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer) {
        return excelBuildService().build(head, body, book, sheetName, headStyle, bodyStyle, consumer);
    }

    /**
     * 导出EXCEL
     * 
     * @param head   表头信息
     * @param body   主体内容
     * @param output 输出流
     */
    public void export(List<?> head, List<? extends List<?>> body, OutputStream output) {
        excelExportService().export(head, body, output);
    }

    /**
     * 导出EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param output    输出流
     * @param book      文本信息
     * @param headStyle 表头样式
     * @param bodyStyle 主体样式
     */
    public void export(List<?> head, List<? extends List<?>> body, OutputStream output, HSSFWorkbook book,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle) {
        excelExportService().export(head, body, output, book, headStyle, bodyStyle);
    }

    /**
     * 导出EXCEL
     * 
     * @param head     表头信息
     * @param body     主体内容
     * @param output   输出流
     * @param consumer Sheet消息者
     */
    public void export(List<?> head, List<? extends List<?>> body, OutputStream output, Consumer<HSSFSheet> consumer) {
        excelExportService().export(head, body, output, consumer);
    }

    /**
     * 导出EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param output    输出流
     * @param book      文本信息
     * @param headStyle 表头样式
     * @param bodyStyle 主体样式
     * @param consumer  Sheet消息者
     */
    public void export(List<?> head, List<? extends List<?>> body, OutputStream output, HSSFWorkbook book,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer) {
        excelExportService().export(head, body, output, book, headStyle, bodyStyle, consumer);
    }

    /**
     * 读取EXCEL文件信息
     * 
     * @param <T>   业务类型
     * @param file  文件信息
     * @param clazz 业务类型信息
     * @param props 业务属性集
     * @return 信息列表
     */
    public <T> List<T> read(File file, Class<T> clazz, String... props) {
        return excelReadService().read(file, clazz, props);
    }

    /**
     * 读取EXCEL文件信息
     * 
     * @param <T>   业务类型
     * @param file  文件信息
     * @param clazz 业务类型信息
     * @param props 业务属性集
     * @return 信息列表
     */
    public <T> List<T> read(MultipartFile file, Class<T> clazz, String... props) {
        return excelReadService().read(file, clazz, props);
    }

    /**
     * 读取EXCEL文件信息
     * 
     * @param <T>      业务类型
     * @param workbook 工作簿
     * @param clazz    业务类型信息
     * @param props    业务属性集
     * @return 信息列表
     */
    public <T> List<T> read(Workbook workbook, Class<T> clazz, String... props) {
        return excelReadService().read(workbook, clazz, props);
    }

    /**
     * 创建工作簿
     * 
     * @param name  文件名
     * @param input 文件输入流
     * @return 工作簿
     */
    public Workbook createWorkbook(String name, InputStream input) {
        return excelReadService().createWorkbook(name, input);
    }

    /**
     * 获取列的字符串值
     * 
     * @param cell 列信息
     * @return 值
     */
    public String getCellValue(Cell cell) {
        return excelReadService().getCellValue(cell);
    }

}
