package per.cby.frame.gis.geometry;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 空间类型，点簇
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MultiPoint extends Geometry {

    /** 点集 */
    private double[][] points;

    /**
     * 构造函数
     */
    public MultiPoint() {
        this(new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param wkid 坐标系编号
     */
    public MultiPoint(int wkid) {
        this(new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param spatialReference 空间参考
     */
    public MultiPoint(SpatialReference spatialReference) {
        this(new double[][] {}, spatialReference);
    }

    /**
     * 构造函数
     * 
     * @param points 点集
     */
    public MultiPoint(double[][] points) {
        this(points, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param points 点集
     * @param wkid   坐标系编号
     */
    public MultiPoint(double[][] points, int wkid) {
        this(points, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param points           点集
     * @param spatialReference 空间参考
     */
    public MultiPoint(double[][] points, SpatialReference spatialReference) {
        this.points = points;
        this.spatialReference = spatialReference;
        this.type = Geometry.MULTI_POINT;
    }

    /**
     * 新增点
     * 
     * @param point 点
     * @return 当前点簇
     */
    public MultiPoint addPoint(Point point) {
        return point != null ? addPoint(point.getCoords()) : null;
    }

    /**
     * 新增点
     * 
     * @param coords 坐标组
     * @return 当前点簇
     */
    public MultiPoint addPoint(double[] coords) {
        if (ArrayUtils.isNotEmpty(coords)) {
            this.setPoints((double[][]) ArrayUtils.add(this.points, coords));
        }
        return this;
    }

    /**
     * 新增点集
     * 
     * @param points 点集
     * @return 当前点簇
     */
    public MultiPoint addPoints(Point[] points) {
        if (ArrayUtils.isNotEmpty(points)) {
            double[][] coordsArr = new double[2][points.length];
            for (int i = 0; i < points.length; i++) {
                coordsArr[i] = points[i].getCoords();
            }
            this.setPoints(coordsArr);
        }
        return this;
    }

    /**
     * 新增点集
     * 
     * @param coordsArr 坐标组集
     * @return 当前点簇
     */
    public MultiPoint addPoints(double[][] coordsArr) {
        if (coordsArr != null) {
            this.setPoints((double[][]) ArrayUtils.addAll(this.points, coordsArr));
        }
        return this;
    }

    /**
     * 获取点
     * 
     * @param index 下标
     * @return 点
     */
    public Point getPoint(int index) {
        if (ArrayUtils.isNotEmpty(this.points) && index >= 0 && this.points.length > index) {
            return new Point(this.points[index]);
        }
        return null;
    }

    /**
     * 移除点
     * 
     * @param index 下标
     * @return 点
     */
    public Point removePoint(int index) {
        Point point = null;
        if (ArrayUtils.isNotEmpty(this.points) && index >= 0 && this.points.length > index) {
            point = new Point(this.points[index]);
            ArrayUtils.remove(this.points, index);
        }
        return point;
    }

    /**
     * 插入点
     * 
     * @param index 下标
     * @param point 点
     * @return 当前点簇
     */
    public MultiPoint setPoint(int index, Point point) {
        return point != null ? setPoint(index, point.getCoords()) : this;
    }

    /**
     * 插入点
     * 
     * @param index  下标
     * @param coords 坐标组
     * @return 当前点簇
     */
    public MultiPoint setPoint(int index, double[] coords) {
        if (ArrayUtils.isNotEmpty(this.points) && index >= 0 && this.points.length > index
                && ArrayUtils.isNotEmpty(coords)) {
            this.points[index] = coords;
        }
        return this;
    }

}
