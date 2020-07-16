package per.cby.frame.ext.excel;

import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * EXCEL构建服务接口
 * 
 * @author chenboyang
 *
 */
public interface ExcelBuildService {

    /**
     * 构建EXCEL
     * 
     * @param head 表头信息
     * @param body 主体内容
     * @return EXCEL文本信息
     */
    default HSSFWorkbook build(List<?> head, List<? extends List<?>> body) {
        return build(head, body, null);
    }

    /**
     * 构建EXCEL
     * 
     * @param head     表头信息
     * @param body     主体内容
     * @param consumer Sheet消费者
     * @return EXCEL文本信息
     */
    default HSSFWorkbook build(List<?> head, List<? extends List<?>> body, Consumer<HSSFSheet> consumer) {
        HSSFWorkbook book = new HSSFWorkbook();
        return build(head, body, book, defaultHeadStyle(book), defaultBodyStyle(book), consumer);
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
    default HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, HSSFCellStyle headStyle,
            HSSFCellStyle bodyStyle) {
        return build(head, body, book, headStyle, bodyStyle, null);
    }

    /**
     * 构建EXCEL
     * 
     * @param head      表头信息
     * @param body      主体内容
     * @param book      文本信息
     * @param headStyle 表头样式
     * @param bodyStyle 主体样式
     * @param consumer  Sheet消费者
     * @return EXCEL文本信息
     */
    default HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, HSSFCellStyle headStyle,
            HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer) {
        return build(head, body, book, null, headStyle, bodyStyle, consumer);
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
    default HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, String sheetName) {
        return build(head, body, book, null, defaultHeadStyle(book), defaultBodyStyle(book), null);
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
     * @param consumer  Sheet消费者
     * @return EXCEL文本信息
     */
    HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, String sheetName,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer);

    /**
     * 获取默认表头样式
     * 
     * @param book 文本信息
     * @return 表头样式
     */
    default HSSFCellStyle defaultHeadStyle(HSSFWorkbook book) {
        HSSFCellStyle headStyle = book.createCellStyle();
//        headStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont headFont = book.createFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 12);
        headStyle.setFont(headFont);
        return headStyle;
    }

    /**
     * 获取默认主体样式
     * 
     * @param book 文本信息
     * @return 主体样式
     */
    default HSSFCellStyle defaultBodyStyle(HSSFWorkbook book) {
        HSSFCellStyle bodyStyle = book.createCellStyle();
        HSSFFont bodyFont = book.createFont();
        bodyFont.setFontHeightInPoints((short) 12);
        bodyStyle.setFont(bodyFont);
        return bodyStyle;
    }

}
