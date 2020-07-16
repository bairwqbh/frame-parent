package per.cby.frame.gis.lbs;

import java.util.List;

/**
 * LBS服务接口
 * 
 * @author chenboyang
 *
 */
public interface LbsService {

    /**
     * 基站定位
     * 
     * @param lbsInfo   基站信息
     * @param coordType 坐标类型
     * @return 定位信息
     */
    default LbsInfo locate(LbsInfo lbsInfo) {
        return locate(lbsInfo, CoordType.WGS84);
    }

    /**
     * 批量基站定位
     * 
     * @param lbsInfoList 基站信息列表
     * @param coordType   坐标类型
     * @return 定位信息
     */
    default LbsInfo locate(List<LbsInfo> lbsInfoList) {
        return locate(lbsInfoList, CoordType.WGS84);
    }

    /**
     * 基站定位
     * 
     * @param lbsInfo   基站信息
     * @param coordType 坐标类型
     * @return 定位信息
     */
    LbsInfo locate(LbsInfo lbsInfo, CoordType coordType);

    /**
     * 批量基站定位
     * 
     * @param lbsInfoList 基站信息列表
     * @param coordType   坐标类型
     * @return 定位信息
     */
    LbsInfo locate(List<LbsInfo> lbsInfoList, CoordType coordType);

}
