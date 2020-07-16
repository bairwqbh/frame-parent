package per.cby.frame.gis.transform;

/**
 * WGS84转中国测绘02坐标转换器
 * 
 * @author chenboyang
 *
 */
public class Wgs84ToGcj02Transform extends AbstractWgs84AndGcj02Transform {

    @Override
    public double[] transform(double x, double y) {
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

}
