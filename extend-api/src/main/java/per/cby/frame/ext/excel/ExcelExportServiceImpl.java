package per.cby.frame.ext.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * EXCEL导出服务接口
 * 
 * @author chenboyang
 *
 */
@Slf4j
public class ExcelExportServiceImpl implements ExcelExportService {

    /** EXCEL构建服务接口 */
    private ExcelBuildService excelBuildService = ExcelServiceBuilder.excelBuildService();

    @Override
    public void export(List<?> head, List<? extends List<?>> body, OutputStream output, Consumer<HSSFSheet> consumer) {
        try {
            HSSFWorkbook book = excelBuildService.build(head, body, consumer);
            book.write(output);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void export(List<?> head, List<? extends List<?>> body, OutputStream output, HSSFWorkbook book,
            HSSFCellStyle headStyle, HSSFCellStyle bodyStyle, Consumer<HSSFSheet> consumer) {
        try {
            book = excelBuildService.build(head, body, book, headStyle, bodyStyle, consumer);
            book.write(output);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
