package per.cby.frame.gis.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateFilter;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.precision.CommonBitsOp;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * JTS几何分析帮助类
 * 
 * @author chenboyang
 *
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class JTSUtil {

    /**
     * 获取几何处理器
     * 
     * @return 几何处理器
     */
    public CommonBitsOp getCommonBitsOp() {
        return new CommonBitsOp();
    }

    /**
     * 解析WKT数据
     * 
     * @param wkt WKT数据
     * @return 几何要素
     */
    public <T extends Geometry> T wktParser(String wkt) {
        return wktParser(wkt, false);
    }

    /**
     * 解析WKT数据
     * 
     * @param wkt           WKT数据
     * @param convertMetric 是否转换为米制
     * @return 几何要素
     */
    public <T extends Geometry> T wktParser(String wkt, boolean convertMetric) {
        try {
            T t = (T) (new WKTReader()).read(wkt);
            if (convertMetric) {
                convertMetric(t);
            }
            return t;
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将度制要素转换为米制要素
     * 
     * @param t 度制要素
     * @return 米制要素
     */
    public <T extends Geometry> T convertMetric(T t) {
        t.apply(new CoordinateFilter() {
            @Override
            public void filter(Coordinate coord) {
                double[] c = TransformUtil.wgs84ToMercator(coord.x, coord.y);
                coord.x = c[0];
                coord.y = c[1];
            }
        });
        return t;
    }

    /**
     * 几何相交分析并返回相交区域几何
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 相交区域几何
     */
    public String intersection(String geom0, String geom1) {
        return getCommonBitsOp().intersection(wktParser(geom0), wktParser(geom1)).toText();
    }

    /**
     * 几何合并分析并返回合并后的几何
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 合并后的几何
     */
    public String union(String geom0, String geom1) {
        return getCommonBitsOp().union(wktParser(geom0), wktParser(geom1)).toText();
    }

    /**
     * 几何差异分析并返回要素1的差异区域几何
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 要素1的差异区域几何
     */
    public String difference(String geom0, String geom1) {
        return getCommonBitsOp().difference(wktParser(geom0), wktParser(geom1)).toText();
    }

    /**
     * 几何对称差异分析并返回要素1和要素2的差异区域几何
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 对称差异区域几何
     */
    public String symDifference(String geom0, String geom1) {
        return getCommonBitsOp().symDifference(wktParser(geom0), wktParser(geom1)).toText();
    }

    /**
     * 几何缓冲区分析并返回分析后的几何,几何数据为米制
     * 
     * @param geom0    要素1(米制)
     * @param distance 距离(单位:米)
     * @return 缓冲区
     */
    public String buffer(String geom0, double distance) {
        return getCommonBitsOp().buffer(wktParser(geom0), distance).toText();
    }

    /**
     * 几何缓冲区分析并返回分析后的几何,几何数据为度制
     * 
     * @param geom0    要素1(度制)
     * @param distance 距离(单位:米)
     * @return 缓冲区
     */
    public String bufferOfDegree(String geom0, double distance) {
        return buffer(geom0, distance / (2 * Math.PI * 6371004) * 360);
    }

    /**
     * 要素1是否覆盖要素2
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 是否覆盖
     */
    public boolean covers(String geom0, String geom1) {
        return wktParser(geom0).covers(wktParser(geom1));
    }

    /**
     * 要素1与要素2是否有相交
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 是否有相交
     */
    public boolean intersects(String geom0, String geom1) {
        return wktParser(geom0).intersects(wktParser(geom1));
    }

    /**
     * 要素1是否包含要素2
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 是否包含
     */
    public boolean contains(String geom0, String geom1) {
        return wktParser(geom0).contains(wktParser(geom1));
    }

    /**
     * 计算两个要素之间的距离
     * 
     * @param geom0 要素1
     * @param geom1 要素2
     * @return 距离
     */
    public double distance(String geom0, String geom1) {
        return wktParser(geom0).distance(wktParser(geom1));
    }

    /**
     * 米制要素简化
     * 
     * @param geom0             几何要素
     * @param distanceTolerance 距离公差
     * @return 要素
     */
    public String simplify(String geom0, double distanceTolerance) {
        return TopologyPreservingSimplifier.simplify(wktParser(geom0), distanceTolerance).toText();
    }

    /**
     * 度制要素简化
     * 
     * @param geom0             几何要素
     * @param distanceTolerance 距离公差
     * @return 要素
     */
    public String simplifyOfDegree(String geom0, double distanceTolerance) {
        return TopologyPreservingSimplifier.simplify(wktParser(geom0, true), distanceTolerance).toText();
    }

    /**
     * 米制要素长度
     * 
     * @param geom0 要素
     * @return 长度
     */
    public double length(String geom0) {
        return wktParser(geom0).getLength();
    }

    /**
     * 度制要素长度
     * 
     * @param geom0 要素
     * @return 长度
     */
    public double lengthOfDegree(String geom0) {
        return wktParser(geom0, true).getLength();
    }

}
