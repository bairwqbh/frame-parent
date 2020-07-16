package per.cby.frame.gis.service.gaode.geocode;

import java.util.List;

import per.cby.frame.gis.geometry.Point;
import per.cby.frame.gis.service.gaode.GaodeParameter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 高德逆向地理编码服务请求参数类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RegeoParameter extends GaodeParameter {

    /** 经纬度坐标 */
    private List<Point> points;

    /** 返回附近POI类型 */
    private List<String> pois;

    /** 经纬度坐标 */
    @Setter(AccessLevel.MODULE)
    private String location;

    /** 返回附近POI类型 */
    @Setter(AccessLevel.MODULE)
    private String poitype;

    /** 搜索半径(radius取值范围在0~3000，默认是1000。单位：米) */
    private Integer radius = 3000;

    /** 批量查询控制 */
    private Boolean batch;

    /** 道路等级 */
    private Integer roadlevel;

    /** 是否优化POI返回顺序 */
    private Integer homeorcorp;

}
