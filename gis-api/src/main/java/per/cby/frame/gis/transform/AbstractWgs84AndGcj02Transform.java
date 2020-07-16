package per.cby.frame.gis.transform;

/**
 * WGS84和中国测绘02坐标转换抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractWgs84AndGcj02Transform extends AbstractTransform {

    /**
     * 转换经度
     * 
     * @param x 经度
     * @param y 纬度
     * @return 经度
     */
    protected double transformX(double x, double y) {
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
    protected double transformY(double x, double y) {
        double result = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        result += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        result += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        result += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return result;
    }

}
