package per.cby.frame.gis.lbs;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * LBS基站定位信息
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class LbsInfo {

    /** 国家代码(460=中国) */
    private int mcc = 460;

    /** 网络类型：0=移动，1=联通(11=电信4G) */
    private int mnc;

    /** 位置区码 */
    private int lac;

    /** 小区标识 */
    private int ci;

    /** 信号强度 */
    private int rssi;

    /** 经度 */
    private double x;

    /** 纬度 */
    private double y;

    /** 地址 */
    private String address;

}
