package per.cby.frame.gis.geometry;

import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 空间类型，线
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Polyline extends Geometry {

    /** 路径集 */
    private double[][][] paths;

    /**
     * 构造函数
     */
    public Polyline() {
        this(new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param wkid 坐标系编号
     */
    public Polyline(int wkid) {
        this(new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param spatialReference 空间参考
     */
    public Polyline(SpatialReference spatialReference) {
        this(new double[][][] {}, spatialReference);
    }

    /**
     * 构造函数
     * 
     * @param path 线
     */
    public Polyline(double[][] path) {
        this(new double[][][] { path }, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param path 线
     * @param wkid 坐标系编号
     */
    public Polyline(double[][] path, int wkid) {
        this(new double[][][] { path }, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param path             线
     * @param spatialReference 空间参考
     */
    public Polyline(double[][] path, SpatialReference spatialReference) {
        this(new double[][][] { path }, spatialReference);
    }

    /**
     * 构造函数
     * 
     * @param paths 线集
     */
    public Polyline(double[][][] paths) {
        this(paths, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param paths 线集
     * @param wkid  坐标系编号
     */
    public Polyline(double[][][] paths, int wkid) {
        this(paths, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param paths            线集
     * @param spatialReference 空间参考
     */
    public Polyline(double[][][] paths, SpatialReference spatialReference) {
        this.paths = paths;
        this.spatialReference = spatialReference;
        this.type = Geometry.POLYLINE;
    }

    /**
     * 新增线
     * 
     * @param points 点集
     * @return 当前线
     */
    public Polyline addPath(Point[] points) {
        if (ArrayUtils.isNotEmpty(points)) {
            double[][] path = new double[2][points.length];
            for (int i = 0; i < points.length; i++) {
                path[i] = points[i].getCoords();
            }
            return addPath(path);
        }
        return this;
    }

    /**
     * 新增线
     * 
     * @param path 线
     * @return 当前线
     */
    public Polyline addPath(double[][] path) {
        if (ArrayUtils.isNotEmpty(path)) {
            this.setPaths((double[][][]) ArrayUtils.add(this.paths, path));
        }
        return this;
    }

    /**
     * 获取点
     * 
     * @param pathIndex  线下标
     * @param pointIndex 点下标
     * @return 点
     */
    public Point getPoint(int pathIndex, int pointIndex) {
        if (ArrayUtils.isNotEmpty(this.paths[pathIndex]) && ArrayUtils.isNotEmpty(this.paths[pathIndex][pointIndex])) {
            return new Point(this.paths[pathIndex][pointIndex]);
        }
        return null;
    }

    /**
     * 插入点
     * 
     * @param pathIndex  线下标
     * @param pointIndex 点下标
     * @param point      点
     * @return 当前线
     */
    public Polyline insertPoint(int pathIndex, int pointIndex, Point point) {
        if (point != null && ArrayUtils.isNotEmpty(point.getCoords())) {
            this.paths[pathIndex][pointIndex] = point.getCoords();
        }
        return this;
    }

    /**
     * 移除线
     * 
     * @param pathIndex 线下标
     * @return 点集
     */
    public Point[] removePath(int pathIndex) {
        if (this.paths != null && this.paths[pathIndex] != null) {
            double[][] path = (double[][]) ArrayUtils.clone(this.paths[pathIndex]);
            ArrayUtils.remove(this.paths, pathIndex);
            if (ArrayUtils.isNotEmpty(path)) {
                Point[] points = new Point[path.length];
                for (int i = 0; i < path.length; i++) {
                    points[i] = new Point(path[i]);
                }
                return points;
            }
        }
        return null;
    }

    /**
     * 移除点
     * 
     * @param pathIndex  线下标
     * @param pointIndex 点下标
     * @return 点
     */
    public Point removePoint(int pathIndex, int pointIndex) {
        return Optional.ofNullable(paths).map(p -> p[pathIndex]).map(pp -> pp[pointIndex]).map(ppp -> {
            double[] point = ArrayUtils.clone(ppp);
            ArrayUtils.remove(paths[pathIndex], pointIndex);
            return new Point(point);
        }).orElse(null);
    }

    /**
     * 设置点
     * 
     * @param pathIndex  线下标
     * @param pointIndex 点下标
     * @param point      点
     * @return 当前线
     */
    public Polyline setPoint(int pathIndex, int pointIndex, Point point) {
        if (this.paths != null && this.paths[pathIndex] != null && this.paths[pathIndex].length > pointIndex
                && point != null && point.getCoords() != null) {
            this.paths[pathIndex][pointIndex] = point.getCoords();
        }
        return this;
    }

}
