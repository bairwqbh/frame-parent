package per.cby.frame.gis.geometry;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 空间类型，矩形范围
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Extent extends Geometry {

    /** 最大X坐标 */
    private double xmax;

    /** 最小X坐标 */
    private double xmin;

    /** 最大Y坐标 */
    private double ymax;

    /** 最小Y坐标 */
    private double ymin;

    /**
     * 构造函数
     */
    public Extent() {
        this(0, 0, 0, 0, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param wkid 坐标系编号
     */
    public Extent(int wkid) {
        this(0, 0, 0, 0, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param spatialReference 空间参考
     */
    public Extent(SpatialReference spatialReference) {
        this(0, 0, 0, 0, spatialReference);
    }

    /**
     * 构造函数
     * 
     * @param xmax 最大X坐标
     * @param xmin 最小X坐标
     * @param ymax 最大Y坐标
     * @param ymin 最小Y坐标
     */
    public Extent(double xmax, double xmin, double ymax, double ymin) {
        this(xmax, xmin, ymax, ymin, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param xmax 最大X坐标
     * @param xmin 最小X坐标
     * @param ymax 最大Y坐标
     * @param ymin 最小Y坐标
     * @param wkid 坐标系编号
     */
    public Extent(double xmax, double xmin, double ymax, double ymin, int wkid) {
        this(xmax, xmin, ymax, ymin, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param xmax             最大X坐标
     * @param xmin             最小X坐标
     * @param ymax             最大Y坐标
     * @param ymin             最小Y坐标
     * @param spatialReference 空间参考
     */
    public Extent(double xmax, double xmin, double ymax, double ymin, SpatialReference spatialReference) {
        this.xmax = xmax;
        this.xmin = xmin;
        this.ymax = ymax;
        this.ymin = ymin;
        this.spatialReference = spatialReference != null ? spatialReference : new SpatialReference();
        this.type = Geometry.EXTENT;
    }

    /**
     * 设置中心点
     * 
     * @param point 中心点
     * @return 中心点设置后的新范围
     */
    public Extent centerAt(Point point) {
        if (point != null) {
            double width = (xmax - xmin) / 2;
            double height = (ymax - ymin) / 2;
            this.setXmax(point.getX() + width);
            this.setXmin(point.getX() - width);
            this.setYmax(point.getY() + height);
            this.setYmin(point.getY() - height);
        }
        return this;
    }

    /**
     * 范围是否包含几何要素
     * 
     * @param geometry 几何要素
     * @return 是否包含
     */
    public boolean contains(Geometry geometry) {
        if (geometry != null) {
            double[][] points = null;
            if (geometry instanceof Point) {
                Point point = (Point) geometry;
                points = new double[][] { point.getCoords() };
            } else if (geometry instanceof MultiPoint) {
                MultiPoint multiPoint = (MultiPoint) geometry;
                points = multiPoint.getPoints();
            } else if (geometry instanceof Polyline) {
                Polyline polyline = (Polyline) geometry;
                double[][][] paths = polyline.getPaths();
                if (ArrayUtils.isNotEmpty(paths)) {
                    points = new double[][] {};
                    for (double[][] path : paths) {
                        points = (double[][]) ArrayUtils.addAll(points, path);
                    }
                }
            } else if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                double[][][] rings = polygon.getRings();
                if (ArrayUtils.isNotEmpty(rings)) {
                    points = new double[][] {};
                    for (double[][] ring : rings) {
                        points = (double[][]) ArrayUtils.addAll(points, ring);
                    }
                }
            }
            return isExist(points);
        }
        return false;
    }

    /**
     * 点簇是否存在范围中
     * 
     * @param points 点簇
     * @return 是否存在
     */
    private boolean isExist(double[][] points) {
        if (ArrayUtils.isNotEmpty(points)) {
            for (double[] point : points) {
                double x = point[0];
                double y = point[1];
                if (x <= xmax && x >= xmin && y <= ymax && y >= ymin) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取范围的面状类型对象
     * 
     * @return 面状类型对象
     */
    public Polygon getPolygon() {
        double[][][] ring = { { { xmax, ymax }, { xmax, ymin }, { xmin, ymin }, { xmin, ymax }, { xmax, ymax } } };
        return new Polygon(ring, spatialReference);
    }

}
