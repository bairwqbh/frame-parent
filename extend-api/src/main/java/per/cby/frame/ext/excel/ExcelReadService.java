package per.cby.frame.ext.excel;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * EXCEL读取服务接口
 * 
 * @author chenboyang
 * @since 2019年11月22日
 *
 */
public interface ExcelReadService {

    /**
     * 读取EXCEL文件信息
     * 
     * @param <T>   业务类型
     * @param file  文件信息
     * @param clazz 业务类型信息
     * @param props 业务属性集
     * @return 信息列表
     */
    <T> List<T> read(File file, Class<T> clazz, String... props);

    /**
     * 读取EXCEL文件信息
     * 
     * @param <T>   业务类型
     * @param file  文件信息
     * @param clazz 业务类型信息
     * @param props 业务属性集
     * @return 信息列表
     */
    <T> List<T> read(MultipartFile file, Class<T> clazz, String... props);

    /**
     * 读取EXCEL文件信息
     * 
     * @param <T>      业务类型
     * @param workbook 工作簿
     * @param clazz    业务类型信息
     * @param props    业务属性集
     * @return 信息列表
     */
    <T> List<T> read(Workbook workbook, Class<T> clazz, String... props);

    /**
     * 创建工作簿
     * 
     * @param name  文件名
     * @param input 文件输入流
     * @return 工作簿
     */
    Workbook createWorkbook(String name, InputStream input);

    /**
     * 获取列的字符串值
     * 
     * @param cell 列信息
     * @return 值
     */
    String getCellValue(Cell cell);

}
