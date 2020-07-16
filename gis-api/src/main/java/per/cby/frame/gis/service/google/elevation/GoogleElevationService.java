package per.cby.frame.gis.service.google.elevation;

import java.util.List;

import per.cby.frame.gis.geometry.Point;
import per.cby.frame.gis.service.google.elevation.GoogleElevationResult.Elevation;

/**
 * 谷歌高程服务
 * 
 * @author chenboyang
 * @since 2019年5月23日
 *
 */
public interface GoogleElevationService {

    /**
     * 根据单个坐标获取高程
     * 
     * @param point 坐标
     * @return 高程
     */
    Elevation elevation(Point point);

    /**
     * 根据坐标列表获取高程列表
     * 
     * @param pointList 坐标列表
     * @return 高程列表
     */
    List<Elevation> elevation(List<Point> pointList);

}
