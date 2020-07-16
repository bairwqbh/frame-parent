package per.cby.frame.gis.lbs.cellocation;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 垃圾基站定位查询结果
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class CellResult {

    /** 操作结果代码 */
    private int errcode;

    /** 纬度 */
    private double lat;

    /** 经度 */
    private double lon;

    /** 覆盖 */
    private int radius;

    /** 地址 */
    private String address;

}
