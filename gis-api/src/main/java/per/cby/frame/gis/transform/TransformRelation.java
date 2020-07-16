package per.cby.frame.gis.transform;

/**
 * 坐标转换关系枚举
 * 
 * @author chenboyang
 *
 */
public enum TransformRelation {

    /** WGS84转WEB墨卡托 */
    WGS84_TO_MERCATOR,

    /** WEB墨卡托转WGS84 */
    MERCATOR_TO_WGS84,

    /** WGS84转中国测绘02 */
    WGS84_TO_GCJ02,

    /** 中国测绘02转WGS84 */
    GCJ02_TO_WGS84,

    /** 中国测绘02转百度09 */
    GCJ02_TO_BD09,

    /** 百度09转中国测绘02 */
    BD09_TO_GCJ02

}
