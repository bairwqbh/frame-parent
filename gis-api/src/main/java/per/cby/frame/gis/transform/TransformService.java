package per.cby.frame.gis.transform;

import per.cby.frame.gis.geometry.Geometry;

/**
 * 坐标转换服务接口
 * 
 * @author chenboyang
 *
 */
public interface TransformService {

    /**
     * 坐标转换
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @param transform
     *            坐标转换器
     * @return 坐标
     */
    double[] transform(double x, double y, Transform transform);

    /**
     * 坐标转换
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @param transformRelation
     *            坐标转换关系枚举
     * @return 坐标
     */
    double[] transform(double x, double y, TransformRelation transformRelation);

    /**
     * WGS84地理坐标转WEB墨卡托
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @return 坐标
     */
    double[] wgs84ToMercator(double x, double y);

    /**
     * WEB墨卡托转WGS84地理坐标
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @return 坐标
     */
    double[] mercatorToWgs84(double x, double y);

    /**
     * GPS84转中国测绘02
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @return 坐标
     */
    double[] wgs84ToGcj02(double x, double y);

    /**
     * 中国测绘02转GPS84
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @return 坐标
     */
    double[] gcj02ToWgs84(double x, double y);

    /**
     * 中国测绘02转百度09
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @return 坐标
     */
    double[] gcj02ToBd09(double x, double y);

    /**
     * 百度09转中国测绘02
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @return 坐标
     */
    double[] bd09ToGcj02(double x, double y);

    /**
     * 几何要素坐标转换
     * 
     * @param geometry
     *            几何要素
     * @param transform
     *            坐标转换器
     * @return 几何要素
     */
    <G extends Geometry> G transform(G geometry, Transform transform);

    /**
     * 几何要素坐标转换
     * 
     * @param geometry
     *            几何要素
     * @param transformRelation
     *            坐标转换关系枚举
     * @return 几何要素
     */
    <G extends Geometry> G transform(G geometry, TransformRelation transformRelation);

    /**
     * WGS84地理坐标转WEB墨卡托
     * 
     * @param geometry
     *            几何要素
     * @return 几何要素
     */
    <G extends Geometry> G wgs84ToMercator(G geometry);

    /**
     * WEB墨卡托转WGS84地理坐标
     * 
     * @param geometry
     *            几何要素
     * @return 几何要素
     */
    <G extends Geometry> G mercatorToWgs84(G geometry);

    /**
     * GPS84转中国测绘02
     * 
     * @param geometry
     *            几何要素
     * @return 几何要素
     */
    <G extends Geometry> G wgs84ToGcj02(G geometry);

    /**
     * 中国测绘02转GPS84
     * 
     * @param geometry
     *            几何要素
     * @return 几何要素
     */
    <G extends Geometry> G gcj02ToWgs84(G geometry);

    /**
     * 中国测绘02转百度09
     * 
     * @param geometry
     *            几何要素
     * @return 几何要素
     */
    <G extends Geometry> G gcj02ToBd09(G geometry);

    /**
     * 百度09转中国测绘02
     * 
     * @param geometry
     *            几何要素
     * @return 几何要素
     */
    <G extends Geometry> G bd09ToGcj02(G geometry);

    /**
     * WKT字符串坐标转换
     * 
     * @param shape
     *            WKT字符串
     * @param transform
     *            坐标转换器
     * @return WKT字符串
     */
    String transform(String shape, Transform transform);

    /**
     * WKT字符串坐标转换
     * 
     * @param shape
     *            WKT字符串
     * @param transformRelation
     *            坐标转换关系枚举
     * @return WKT字符串
     */
    String transform(String shape, TransformRelation transformRelation);

    /**
     * WGS84地理坐标转WEB墨卡托
     * 
     * @param shape
     *            WKT字符串
     * @return WKT字符串
     */
    String wgs84ToMercator(String shape);

    /**
     * WEB墨卡托转WGS84地理坐标
     * 
     * @param shape
     *            WKT字符串
     * @return WKT字符串
     */
    String mercatorToWgs84(String shape);

    /**
     * GPS84转中国测绘02
     * 
     * @param shape
     *            WKT字符串
     * @return WKT字符串
     */
    String wgs84ToGcj02(String shape);

    /**
     * 中国测绘02转GPS84
     * 
     * @param shape
     *            WKT字符串
     * @return WKT字符串
     */
    String gcj02ToWgs84(String shape);

    /**
     * 中国测绘02转百度09
     * 
     * @param shape
     *            WKT字符串
     * @return WKT字符串
     */
    String gcj02ToBd09(String shape);

    /**
     * 百度09转中国测绘02
     * 
     * @param shape
     *            WKT字符串
     * @return WKT字符串
     */
    String bd09ToGcj02(String shape);

}
