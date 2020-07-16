package per.cby.frame.gis.geometry;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 空间参考
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class SpatialReference {

    /** 坐标系编号 */
    private int wkid;

    /** WKT数据 */
    private String wkt;

    /**
     * 构造函数
     */
    public SpatialReference() {
        this(4326);
    }

    /**
     * 构造函数
     * 
     * @param wkid 坐标系编号
     */
    public SpatialReference(int wkid) {
        this.wkid = wkid;
    }

}
