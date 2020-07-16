package per.cby.frame.gis.transform;

/**
 * WGS84转WEB墨卡托坐标转换器
 * 
 * @author chenboyang
 *
 */
public class Wgs84ToMercatorTransform extends AbstractTransform {

    @Override
    public double[] transform(double x, double y) {
        double[] coordinate = new double[2];
        double tx = x * METER_LIMIT / 180;
        double ty = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
        ty = ty * METER_LIMIT / 180;
        coordinate[0] = tx;
        coordinate[1] = ty;
        return coordinate;
    }

}
