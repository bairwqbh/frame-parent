package per.cby.frame.gis.transform;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import per.cby.frame.common.util.JudgeUtil;
import per.cby.frame.gis.geometry.Extent;
import per.cby.frame.gis.geometry.Geometry;
import per.cby.frame.gis.geometry.GeometryCollection;
import per.cby.frame.gis.geometry.MultiPoint;
import per.cby.frame.gis.geometry.Point;
import per.cby.frame.gis.geometry.Polygon;
import per.cby.frame.gis.geometry.Polyline;
import per.cby.frame.gis.util.WKTUtil;

/**
 * 坐标转换服务接口实现类
 * 
 * @author chenboyang
 *
 */
@Lazy
@Service("__TRANSFORM_SERVICE_IMPL__")
public class TransformServiceImpl extends AbstractTransform implements TransformService {

    @Override
    @Deprecated
    public double[] transform(double x, double y) {
        return null;
    }

    @Override
    public double[] transform(double x, double y, Transform transform) {
        return transform.transform(x, y);
    }

    @Override
    public double[] transform(double x, double y, TransformRelation transformRelation) {
        return transform(x, y, getTransform(transformRelation));
    }

    @Override
    public double[] wgs84ToMercator(double x, double y) {
        return transform(x, y, TransformRelation.WGS84_TO_MERCATOR);
    }

    @Override
    public double[] mercatorToWgs84(double x, double y) {
        return transform(x, y, TransformRelation.MERCATOR_TO_WGS84);
    }

    @Override
    public double[] wgs84ToGcj02(double x, double y) {
        return transform(x, y, TransformRelation.WGS84_TO_GCJ02);
    }

    @Override
    public double[] gcj02ToWgs84(double x, double y) {
        return transform(x, y, TransformRelation.GCJ02_TO_WGS84);
    }

    @Override
    public double[] gcj02ToBd09(double x, double y) {
        return transform(x, y, TransformRelation.GCJ02_TO_BD09);
    }

    @Override
    public double[] bd09ToGcj02(double x, double y) {
        return transform(x, y, TransformRelation.BD09_TO_GCJ02);
    }

    @Override
    public <G extends Geometry> G transform(G geometry, Transform transform) {
        if (JudgeUtil.isAllNotNull(geometry, transform)) {
            if (geometry instanceof Point) {
                Point point = (Point) geometry;
                point.setCoords(transform.transform(point.getX(), point.getY()));
            } else if (geometry instanceof MultiPoint) {
                MultiPoint multiPoint = (MultiPoint) geometry;
                double[][] points = multiPoint.getPoints();
                if (ArrayUtils.isNotEmpty(points)) {
                    for (int i = 0; i < points.length; i++) {
                        points[i] = transform.transform(points[i][0], points[i][1]);
                    }
                }
            } else if (geometry instanceof Polyline) {
                Polyline polyline = (Polyline) geometry;
                double[][][] paths = polyline.getPaths();
                if (ArrayUtils.isNotEmpty(paths)) {
                    for (int i = 0; i < paths.length; i++) {
                        double[][] path = paths[i];
                        if (ArrayUtils.isNotEmpty(path)) {
                            for (int j = 0; j < path.length; j++) {
                                path[j] = transform.transform(path[j][0], path[j][1]);
                            }
                        }
                    }
                }
            } else if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                double[][][] rings = polygon.getRings();
                if (ArrayUtils.isNotEmpty(rings)) {
                    for (int i = 0; i < rings.length; i++) {
                        double[][] ring = rings[i];
                        if (ArrayUtils.isNotEmpty(ring)) {
                            for (int j = 0; j < ring.length; j++) {
                                ring[j] = transform.transform(ring[j][0], ring[j][1]);
                            }
                        }
                    }
                }
            } else if (geometry instanceof Extent) {
                Extent extent = (Extent) geometry;
                double[] minCoords = transform.transform(extent.getXmin(), extent.getYmin());
                extent.setXmin(minCoords[0]);
                extent.setYmin(minCoords[1]);
                double[] maxCoords = transform.transform(extent.getXmax(), extent.getYmax());
                extent.setXmax(maxCoords[0]);
                extent.setYmax(maxCoords[1]);
            } else if (geometry instanceof GeometryCollection) {
                GeometryCollection geometryCollection = (GeometryCollection) geometry;
                if (CollectionUtils.isNotEmpty(geometryCollection)) {
                    for (int i = 0; i < geometryCollection.size(); i++) {
                        geometryCollection.set(i, transform(geometryCollection.get(i), transform));
                    }
                }
            }
        }
        return geometry;
    }

    @Override
    public <G extends Geometry> G transform(G geometry, TransformRelation transformRelation) {
        return transform(geometry, getTransform(transformRelation));
    }

    @Override
    public <G extends Geometry> G wgs84ToMercator(G geometry) {
        return transform(geometry, TransformRelation.WGS84_TO_MERCATOR);
    }

    @Override
    public <G extends Geometry> G mercatorToWgs84(G geometry) {
        return transform(geometry, TransformRelation.MERCATOR_TO_WGS84);
    }

    @Override
    public <G extends Geometry> G wgs84ToGcj02(G geometry) {
        return transform(geometry, TransformRelation.WGS84_TO_GCJ02);
    }

    @Override
    public <G extends Geometry> G gcj02ToWgs84(G geometry) {
        return transform(geometry, TransformRelation.GCJ02_TO_WGS84);
    }

    @Override
    public <G extends Geometry> G gcj02ToBd09(G geometry) {
        return transform(geometry, TransformRelation.GCJ02_TO_BD09);
    }

    @Override
    public <G extends Geometry> G bd09ToGcj02(G geometry) {
        return transform(geometry, TransformRelation.BD09_TO_GCJ02);
    }

    @Override
    public String transform(String shape, Transform transform) {
        if (StringUtils.isNotBlank(shape) && transform != null) {
            shape = transform(WKTUtil.wktToGeometry(shape), transform).toShape();
        }
        return shape;
    }

    @Override
    public String transform(String shape, TransformRelation transformRelation) {
        return transform(shape, getTransform(transformRelation));
    }

    @Override
    public String wgs84ToMercator(String shape) {
        return transform(shape, TransformRelation.WGS84_TO_MERCATOR);
    }

    @Override
    public String mercatorToWgs84(String shape) {
        return transform(shape, TransformRelation.MERCATOR_TO_WGS84);
    }

    @Override
    public String wgs84ToGcj02(String shape) {
        return transform(shape, TransformRelation.WGS84_TO_GCJ02);
    }

    @Override
    public String gcj02ToWgs84(String shape) {
        return transform(shape, TransformRelation.GCJ02_TO_WGS84);
    }

    @Override
    public String gcj02ToBd09(String shape) {
        return transform(shape, TransformRelation.GCJ02_TO_BD09);
    }

    @Override
    public String bd09ToGcj02(String shape) {
        return transform(shape, TransformRelation.BD09_TO_GCJ02);
    }

}
