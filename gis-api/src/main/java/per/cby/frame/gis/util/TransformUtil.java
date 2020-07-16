package per.cby.frame.gis.util;

import lombok.experimental.UtilityClass;

/**
 * <h1>坐标转换帮助类</h1>
 * <p>
 * 用于转换不同坐标系的坐标信息，主要包含以下坐标系的转换：
 * </p>
 * <p>
 * WGS84(GPS84地理坐标系)
 * </p>
 * <p>
 * WEB墨卡托(GPS84投影坐标系)
 * </p>
 * <p>
 * GCJ02(中国测绘局02地理坐标系)
 * </p>
 * <p>
 * BD09(百度09地理坐标系)
 * </p>
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class TransformUtil {

    /** 长半轴 */
    public final double A = 6378137.0;

    /** 偏移率 */
    public final double EE = 0.00669342162296594323;

    /** 米制范围 */
    public final double METER_LIMIT = 20037508.3427892;

    /**
     * WGS84地理坐标转WEB墨卡托
     * 
     * @param x 经度
     * @param y 纬度
     * @return WEB墨卡托坐标
     */
    public double[] wgs84ToMercator(double x, double y) {
        double[] coordinate = new double[2];
        double tx = x * METER_LIMIT / 180;
        double ty = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
        ty = ty * METER_LIMIT / 180;
        coordinate[0] = tx;
        coordinate[1] = ty;
        return coordinate;
    }

    /**
     * WEB墨卡托转WGS84地理坐标
     * 
     * @param x 经度
     * @param y 纬度
     * @return 地理坐标
     */
    public double[] mercatorToWgs84(double x, double y) {
        double[] coordinate = new double[2];
        double tx = x / METER_LIMIT * 180;
        double ty = y / METER_LIMIT * 180;
        ty = 180 / Math.PI * (2 * Math.atan(Math.exp(ty * Math.PI / 180)) - Math.PI / 2);
        coordinate[0] = tx;
        coordinate[1] = ty;
        return coordinate;
    }

    /**
     * GPS84转中国测绘02
     * 
     * @param x 经度
     * @param y 纬度
     * @return 坐标
     */
    public double[] wgs84ToGcj02(double x, double y) {
        double tx = transformX(x - 105.0, y - 35.0);
        double ty = transformY(x - 105.0, y - 35.0);
        double rady = y / 180.0 * Math.PI;
        double magic = Math.sin(rady);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        ty = (ty * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * Math.PI);
        tx = (tx * 180.0) / (A / sqrtMagic * Math.cos(rady) * Math.PI);
        double my = y + ty;
        double mx = x + tx;
        return new double[] { mx, my };
    }

    /**
     * 中国测绘02转GPS84
     * 
     * @param x 经度
     * @param y 纬度
     * @return 坐标
     */
    public double[] gcj02ToWgs84(double x, double y) {
        double[] coordinate = transform(x, y);
        double tx = x * 2 - coordinate[0];
        double ty = y * 2 - coordinate[1];
        return new double[] { tx, ty };
    }

    /**
     * 中国测绘02转百度09
     * 
     * @param x 经度
     * @param y 纬度
     * @return 坐标
     */
    public double[] gcj02ToBd09(double x, double y) {
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double bx = z * Math.cos(theta) + 0.0065;
        double by = z * Math.sin(theta) + 0.006;
        return new double[] { bx, by };
    }

    /**
     * 百度09转中国测绘02
     * 
     * @param x 经度
     * @param y 纬度
     * @return 坐标
     */
    public double[] bd09ToGcj02(double x, double y) {
        double bx = x - 0.0065;
        double by = y - 0.006;
        double bz = Math.sqrt(bx * bx + by * by) - 0.00002 * Math.sin(by * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
        double gx = bz * Math.cos(theta);
        double gy = bz * Math.sin(theta);
        return new double[] { gx, gy };
    }

    /**
     * 百度09转GPS84
     * 
     * @param x 经度
     * @param y 纬度
     * @return 坐标
     */
    public double[] bd09ToWgs84(double x, double y) {
        double[] gcj02 = bd09ToGcj02(x, y);
        double[] wgs84 = gcj02ToWgs84(gcj02[0], gcj02[1]);
        return wgs84;
    }

    /**
     * 坐标转换
     * 
     * @param x 经度
     * @param y 纬度
     * @return 坐标
     */
    public double[] transform(double x, double y) {
        double tx = transformX(x - 105.0, y - 35.0);
        double ty = transformY(x - 105.0, y - 35.0);
        double ry = y / 180.0 * Math.PI;
        double magic = Math.sin(ry);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        ty = (ty * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * Math.PI);
        tx = (tx * 180.0) / (A / sqrtMagic * Math.cos(ry) * Math.PI);
        double my = y + ty;
        double mx = x + tx;
        return new double[] { mx, my };
    }

    /**
     * 转换经度
     * 
     * @param x 经度
     * @param y 纬度
     * @return 经度
     */
    public double transformX(double x, double y) {
        double result = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        result += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        result += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        result += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return result;
    }

    /**
     * 转换纬度
     * 
     * @param x 经度
     * @param y 纬度
     * @return 纬度
     */
    public double transformY(double x, double y) {
        double result = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        result += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        result += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        result += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return result;
    }

}
