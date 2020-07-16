package per.cby.frame.ext.excel;

import java.util.Optional;

import lombok.experimental.UtilityClass;

/**
 * Excel服务构建器
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class ExcelServiceBuilder {

    /** Excel构建服务 */
    private volatile ExcelBuildService excelBuildService = null;

    /** Excel导出服务 */
    private volatile ExcelExportService excelExportService = null;

    /** Excel读取服务 */
    private volatile ExcelReadService excelReadService = null;

    /**
     * 获取Excel构建服务
     * 
     * @return Excel构建服务
     */
    public ExcelBuildService excelBuildService() {
        return Optional.ofNullable(excelBuildService).orElseGet(() -> excelBuildService = new ExcelBuildServiceImpl());
    }

    /**
     * 获取Excel导出服务
     * 
     * @return Excel导出服务
     */
    public ExcelExportService excelExportService() {
        return Optional.ofNullable(excelExportService)
                .orElseGet(() -> excelExportService = new ExcelExportServiceImpl());
    }

    /**
     * 获取Excel读取服务
     * 
     * @return Excel读取服务
     */
    public ExcelReadService excelReadService() {
        return Optional.ofNullable(excelReadService).orElseGet(() -> excelReadService = new ExcelReadServiceImpl());
    }

}
