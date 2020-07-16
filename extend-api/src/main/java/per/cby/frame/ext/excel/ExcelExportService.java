package per.cby.frame.ext.excel;

import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * EXCEL导出服务接口
 * 
 * @author chenboyang
 *
 */
public interface ExcelExportService {

    /**
     * 导出EXCEL
     * 
     * @param head   表头信息
     * @param body   主体内容
     * @param output 输出流
     */
    default void export(List<?> head, List<? extends List<?>> body, OutputStream output) {
        export(head, body, output, null);
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
    default void export(List<?> head, List<? extends List<?>> body, OutputStream output, HSSFWorkbook book,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle) {
        export(head, body, output, book, headStyle, bodyStyle, null);
    }

    /**
     * 导出EXCEL
     * 
     * @param head     表头信息
     * @param body     主体内容
     * @param output   输出流
     * @param consumer Sheet消费者
     */
    void export(List<?> head, List<? extends List<?>> body, OutputStream output, Consumer<HSSFSheet> consumer);

    /**
     * 导出EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param output    输出流
     * @param book      文本信息
     * @param headStyle 表头样式
     * @param bodyStyle 主体样式
     * @param listener  Sheet监听器
     */
    void export(List<?> head, List<? extends List<?>> body, OutputStream output, HSSFWorkbook book,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer);

}
