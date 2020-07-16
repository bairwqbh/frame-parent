package per.cby.frame.gis.service.google.elevation;

import java.util.ArrayList;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 谷歌高程服务接口返回结果
 * 
 * @author chenboyang
 * @since 2019年5月23日
 *
 */
@Data
@Accessors(chain = true)
public class GoogleElevationResult {

    /** 成功 */
    public static final String OK = "OK";

    /** 返回状态 */
    private String status;

    /** 结果集 */
    private ArrayList<Elevation> results;

    /** 错误信息 */
    private String error_message;

    /**
     * 高程数据
     * 
     * @author chenboyang
     * @since 2019年5月23日
     *
     */
    @Data
    @Accessors(chain = true)
    public static class Elevation {

        /** 空间高程 */
        private double elevation;

        /** 空间坐标 */
        private Location location;

        /** 分辨率 */
        private double resolution;

        /**
         * 坐标数据
         * 
         * @author chenboyang
         * @since 2019年5月23日
         *
         */
        @Data
        @Accessors(chain = true)
        public static class Location {

            /** 纬度 */
            private double lat;

            /** 经度 */
            private double lng;

        }

    }

}
