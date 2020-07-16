package per.cby.frame.ext.excel;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * EXCEL构建服务接口实现类
 * 
 * @author chenboyang
 *
 */
public class ExcelBuildServiceImpl implements ExcelBuildService {

    @Override
    public HSSFWorkbook build(List<?> head, List<? extends List<?>> body, HSSFWorkbook book, String sheetName,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer) {
        // 创建EXCEL文本信息
        HSSFSheet sheet = StringUtils.isNotBlank(sheetName) ? book.createSheet(sheetName) : book.createSheet();
        if (consumer != null) {
            consumer.accept(sheet);
        }
        // 创建EXCEL表头信息
        if (CollectionUtils.isNotEmpty(head)) {
            HSSFRow headRow = sheet.createRow(0);
            for (int i = 0; i < head.size(); i++) {
                HSSFCell cell = headRow.createCell(i);
                cell.setCellStyle(headStyle);
                Optional.ofNullable(head.get(i)).ifPresent(item -> cell.setCellValue(item.toString()));
            }
        }
        // 创建EXCEL主体内容信息
        if (CollectionUtils.isNotEmpty(body)) {
            for (int i = 0; i < body.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                row.setRowStyle(bodyStyle);
                if (CollectionUtils.isNotEmpty(body.get(i))) {
                    for (int j = 0; j < body.get(i).size(); j++) {
                        HSSFCell cell = row.createCell(j);
                        cell.setCellStyle(bodyStyle);
                        Optional.ofNullable(body.get(i).get(j)).ifPresent(item -> cell.setCellValue(item.toString()));
                    }
                }
            }
        }
        // 返回EXCEL文本信息
        return book;
    }

}
