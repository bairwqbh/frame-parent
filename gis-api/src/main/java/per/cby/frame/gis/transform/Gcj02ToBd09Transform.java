package per.cby.frame.gis.transform;

/**
 * 中国测绘02转百度09坐标转换器
 * 
 * @author chenboyang
 *
 */
public class Gcj02ToBd09Transform extends AbstractTransform {

    @Override
    public double[] transform(double x, double y) {
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double bx = z * Math.cos(theta) + 0.0065;
        double by = z * Math.sin(theta) + 0.006;
        return new double[] { bx, by };
    }

}
