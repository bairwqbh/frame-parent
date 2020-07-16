package per.cby.frame.gis.transform;

/**
 * WEB墨卡托转WGS84坐标转换器
 * 
 * @author chenboyang
 *
 */
public class MercatorToWgs84Transform extends AbstractTransform {

    @Override
    public double[] transform(double x, double y) {
        double[] coordinate = new double[2];
        double tx = x / METER_LIMIT * 180;
        double ty = y / METER_LIMIT * 180;
        ty = 180 / Math.PI * (2 * Math.atan(Math.exp(ty * Math.PI / 180)) - Math.PI / 2);
        coordinate[0] = tx;
        coordinate[1] = ty;
        return coordinate;
    }

}
