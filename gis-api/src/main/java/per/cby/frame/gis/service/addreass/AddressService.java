package per.cby.frame.gis.service.addreass;

import java.util.List;

import per.cby.frame.gis.geometry.Point;

/**
 * 地址服务接口
 * 
 * @author chenboyang
 *
 */
public interface AddressService {

    /**
     * 根据地理坐标获取地理地址
     * 
     * @param point
     *            地理坐标
     * @return 地理地址
     */
    String geoAddress(Point point);

    /**
     * 根据地理坐标列表获取地理地址列表
     * 
     * @param point
     *            地理坐标列表
     * @return 地理地址列表
     */
    List<String> geoAddress(List<Point> list);

}
