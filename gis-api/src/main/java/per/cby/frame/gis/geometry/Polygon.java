package per.cby.frame.gis.geometry;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 空间类型，面
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Polygon extends Geometry {

    /** 环集 */
    private double[][][] rings;

    /**
     * 构造函数
     */
    public Polygon() {
        this(new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param wkid 坐标系编号
     */
    public Polygon(int wkid) {
        this(new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param spatialReference 空间参考
     */
    public Polygon(SpatialReference spatialReference) {
        this(new double[][][] {}, spatialReference);
    }

    /**
     * 构造函数
     * 
     * @param ring 环
     */
    public Polygon(double[][] ring) {
        this(new double[][][] { ring }, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param ring 环
     * @param wkid 坐标系编号
     */
    public Polygon(double[][] ring, int wkid) {
        this(new double[][][] { ring }, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param ring             环
     * @param spatialReference 空间参考
     */
    public Polygon(double[][] ring, SpatialReference spatialReference) {
        this(new double[][][] { ring }, spatialReference);
    }

    /**
     * 构造函数
     * 
     * @param rings 环集
     */
    public Polygon(double[][][] rings) {
        this(rings, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param rings 环集
     * @param wkid  坐标系编号
     */
    public Polygon(double[][][] rings, int wkid) {
        this(rings, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param rings            环集
     * @param spatialReference 空间参考
     */
    public Polygon(double[][][] rings, SpatialReference spatialReference) {
        this.rings = rings;
        this.spatialReference = spatialReference;
        this.type = Geometry.POLYGON;
    }

    /**
     * 新增环
     * 
     * @param points 点集
     * @return 当前面
     */
    public Polygon addRing(Point[] points) {
        if (ArrayUtils.isNotEmpty(points)) {
            double[][] path = new double[2][points.length];
            for (int i = 0; i < points.length; i++) {
                path[i] = points[i].getCoords();
            }
            return addRing(path);
        }
        return this;
    }

    /**
     * 新增环
     * 
     * @param ring 环
     * @return 当前面
     */
    public Polygon addRing(double[][] ring) {
        if (ArrayUtils.isNotEmpty(ring)) {
            this.setRings((double[][][]) ArrayUtils.add(this.rings, ring));
        }
        return this;
    }

}
