package per.cby.frame.gis.transform;

/**
 * 中国测绘02转WGS84坐标转换器
 * 
 * @author chenboyang
 *
 */
public class Gcj02ToWgs84Transform extends AbstractWgs84AndGcj02Transform {

    @Override
    public double[] transform(double x, double y) {
        double tx = transformX(x - 105.0, y - 35.0);
        double ty = transformY(x - 105.0, y - 35.0);
        double ry = y / 180.0 * Math.PI;
        double magic = Math.sin(ry);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        tx = (tx * 180.0) / (A / sqrtMagic * Math.cos(ry) * Math.PI);
        ty = (ty * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * Math.PI);
        tx = (x * 2) - (tx + x);
        ty = (y * 2) - (ty + y);
        return new double[] { tx, ty };
    }

}
