package per.cby.frame.gis.transform;

/**
 * 百度09转中国测绘02坐标转换器
 * 
 * @author chenboyang
 *
 */
public class Bd09ToGcj02Transform extends AbstractTransform {

    @Override
    public double[] transform(double x, double y) {
        double bx = x - 0.0065;
        double by = y - 0.006;
        double bz = Math.sqrt(bx * bx + by * by) - 0.00002 * Math.sin(by * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
        double gx = bz * Math.cos(theta);
        double gy = bz * Math.sin(theta);
        return new double[] { gx, gy };
    }

}
