package per.cby.frame.gis.transform;

/**
 * 坐标转换接口
 * 
 * @author chenboyang
 *
 */
public interface Transform {

    /** 长半轴 */
    double A = 6378137.0;

    /** 偏移率 */
    double EE = 0.00669342162296594323;

    /** 米制范围 */
    double METER_LIMIT = 20037508.3427892;

    /**
     * 坐标转换
     * 
     * @param x
     *            经度
     * @param y
     *            纬度
     * @return 坐标
     */
    double[] transform(double x, double y);

}
