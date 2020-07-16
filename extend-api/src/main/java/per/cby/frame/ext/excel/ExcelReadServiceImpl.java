package per.cby.frame.ext.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.ReflectUtil;
import per.cby.frame.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * EXCEL读取服务接口实现
 * 
 * @author chenboyang
 * @since 2019年11月22日
 *
 */
@Slf4j
public class ExcelReadServiceImpl implements ExcelReadService {

    @Override
    public <T> List<T> read(File file, Class<T> clazz, String... props) {
        List<T> list = null;
        String name = file.getName();
        try (InputStream input = FileUtils.openInputStream(file)) {
            Workbook workbook = createWorkbook(name, input);
            list = read(workbook, clazz, props);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public <T> List<T> read(MultipartFile file, Class<T> clazz, String... props) {
        List<T> list = null;
        String name = file.getOriginalFilename();
        try (InputStream input = file.getInputStream()) {
            Workbook workbook = createWorkbook(name, input);
            list = read(workbook, clazz, props);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public <T> List<T> read(Workbook workbook, Class<T> clazz, String... props) {
        List<T> list = BaseUtil.newArrayList();
        if (ArrayUtils.isEmpty(props)) {
            return list;
        }
        try {
            for (Sheet sheet : workbook) {
                int rows = sheet.getPhysicalNumberOfRows();
                if (rows > 1) {
                    for (int i = 1; i < rows; i++) {
                        Row row = sheet.getRow(i);
                        T t = clazz.newInstance();
                        int cellLength = row.getPhysicalNumberOfCells();
                        for (int j = 0; j < props.length && j < cellLength; j++) {
                            String prop = props[j];
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                String value = getCellValue(cell);
                                if (StringUtils.isNotBlank(value)) {
                                    ReflectUtil.setPropertyValue(t, prop, value);
                                }
                            }
                        }
                        list.add(t);
                    }
                }
            }
            workbook.close();
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Workbook createWorkbook(String name, InputStream input) {
        Workbook workbook = null;
        try {
            String suffix = StringUtil.getFileNameSuffix(name.toLowerCase());
            BusinessAssert.isTrue(suffix.equals("xls") || suffix.equals("xlsx"), "文件格式错误,请检查文件格式！");
            if (suffix.equals("xls")) {
                workbook = new HSSFWorkbook(input);
            } else if (suffix.equals("xlsx")) {
                workbook = new XSSFWorkbook(input);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return workbook;
    }

    @Override
    public String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                String str = String.valueOf(cell.getNumericCellValue());
                if (str.endsWith(".0")) {
                    str = str.replace(".0", "");
                }
                return str;
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return String.valueOf(cell.getCellFormula());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
            case ERROR:
            default:
                return null;
        }
    }

}
