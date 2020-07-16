package per.cby.frame.gis.geometry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import per.cby.frame.gis.util.WKTUtil;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 空间几何类型基类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public abstract class Geometry {

    /** 点要素类型 */
    public static final String POINT = "point";

    /** 点簇要素类型 */
    public static final String MULTI_POINT = "multipoint";

    /** 线要素类型 */
    public static final String POLYLINE = "polyline";

    /** 面要素类型 */
    public static final String POLYGON = "polygon";

    /** 范围要素类型 */
    public static final String EXTENT = "extent";

    /** 几何要素集类型 */
    public static final String GEOMETRY_COLLECTION = "geometryCollection";

    /** 缓存数据 */
    protected Map<Object, Object> cache = new HashMap<Object, Object>();

    /** 空间参考 */
    protected SpatialReference spatialReference;

    /** 要素几何类型 */
    protected String type;

    /**
     * 清除缓存数据
     */
    public void clearCache() {
        if (cache != null && !cache.isEmpty()) {
            cache.clear();
        }
    }

    /**
     * 根据键获取缓存数据
     * 
     * @param key 键
     * @return 值
     */
    public Object getCacheValue(Object key) {
        return Optional.ofNullable(cache).map(cache -> cache.get(key)).orElse(null);
    }

    /**
     * 插入缓存数据
     * 
     * @param key   键
     * @param value 值
     */
    public void setCacheValue(Object key, Object value) {
        Optional.ofNullable(cache).ifPresent(cache -> cache.put(key, value));
    }

    /**
     * 转换为WKT字符串
     * 
     * @return WKT字符串
     */
    public String toShape() {
        return WKTUtil.geometryToWkt(this);
    }

}
