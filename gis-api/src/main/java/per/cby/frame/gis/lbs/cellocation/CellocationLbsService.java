package per.cby.frame.gis.lbs.cellocation;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import per.cby.frame.gis.lbs.CoordType;
import per.cby.frame.gis.lbs.LbsInfo;
import per.cby.frame.gis.lbs.LbsService;
import per.cby.frame.gis.lbs.LbsUtil;

/**
 * 垃圾LBS服务接口
 * 
 * @author chenboyang
 *
 */
@Lazy
@Service("__CELLOCATION_LBS_SERVICE__")
public class CellocationLbsService implements LbsService {

    /** 返回格式 */
    private final String OUTPUT = "json";

    @Autowired(required = false)
    private CellocationClient cellocationClient;

    @Override
    public LbsInfo locate(LbsInfo lbsInfo, CoordType coordType) {
        if (lbsInfo == null) {
            return null;
        }
        CellResult result = cellocationClient.cell(lbsInfo.getMcc(), lbsInfo.getMnc(), lbsInfo.getLac(),
                lbsInfo.getCi(), coordType.toString().toLowerCase(), OUTPUT);
        if (result.getErrcode() == 0) {
            lbsInfo.setX(result.getLon());
            lbsInfo.setY(result.getLat());
            lbsInfo.setAddress(result.getAddress());
        }
        return lbsInfo;
    }

    @Override
    public LbsInfo locate(List<LbsInfo> lbsInfoList, CoordType coordType) {
        if (CollectionUtils.isEmpty(lbsInfoList)) {
            return null;
        }
        if (lbsInfoList.size() == 1) {
            return locate(lbsInfoList.get(0), coordType);
        }
        lbsInfoList = lbsInfoList.stream().map(lbsInfo -> locate(lbsInfo, coordType)).collect(Collectors.toList());
        double[] coord = LbsUtil.lbsLocate(lbsInfoList);
        LbsInfo last = lbsInfoList.get(lbsInfoList.size() - 1);
        LbsInfo lbsInfo = new LbsInfo();
        lbsInfo.setX(coord[0]);
        lbsInfo.setY(coord[1]);
        lbsInfo.setAddress(last.getAddress());
        return lbsInfo;
    }

}
