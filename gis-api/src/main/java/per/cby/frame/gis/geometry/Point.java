package per.cby.frame.gis.geometry;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 空间类型，点
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Point extends Geometry {

    /** x坐标 */
    private double x;

    /** y坐标 */
    private double y;

    /**
     * 构造函数
     */
    public Point() {
        this(new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param spatialReference 空间参考
     */
    public Point(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
        this.type = Geometry.POINT;
    }

    /**
     * 构造函数
     * 
     * @param x x坐标
     * @param y y坐标
     */
    public Point(double x, double y) {
        this(x, y, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param x    x坐标
     * @param y    x坐标
     * @param wkid
     */
    public Point(double x, double y, int wkid) {
        this(x, y, new SpatialReference(wkid));
    }

    /**
     * 构造函数
     * 
     * @param x                x坐标
     * @param y                x坐标
     * @param spatialReference 空间参考
     */
    public Point(double x, double y, SpatialReference spatialReference) {
        this.x = x;
        this.y = y;
        this.spatialReference = spatialReference;
        this.type = Geometry.POINT;
    }

    /**
     * 构造函数
     * 
     * @param coords 坐标组
     */
    public Point(double[] coords) {
        this(coords, new SpatialReference());
    }

    /**
     * 构造函数
     * 
     * @param coords           坐标组
     * @param spatialReference 空间参考
     */
    public Point(double[] coords, SpatialReference spatialReference) {
        if (coords != null && coords.length >= 2) {
            this.x = coords[0];
            this.y = coords[1];
        }
        this.spatialReference = spatialReference;
        this.type = Geometry.POINT;
    }

    /**
     * 点坐标偏移
     * 
     * @param dx x坐标偏移量
     * @param dy y坐标偏移量
     * @return 当前点
     */
    public Point offset(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    /**
     * 更新点坐标
     * 
     * @param x x坐标
     * @param y y坐标
     * @return 当前点
     */
    public Point update(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * 获取坐标组
     * 
     * @return 坐标组
     */
    public double[] getCoords() {
        return new double[] { x, y };
    }

    /**
     * 设置坐标
     * 
     * @param coords 坐标
     */
    public void setCoords(double[] coords) {
        if (ArrayUtils.getLength(coords) >= 2) {
            x = coords[0];
            y = coords[1];
        }
    }

}
