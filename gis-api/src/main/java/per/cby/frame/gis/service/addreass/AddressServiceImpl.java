package per.cby.frame.gis.service.addreass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.gis.geometry.Point;
import per.cby.frame.gis.service.gaode.geocode.GeocodeService;
import per.cby.frame.gis.service.gaode.geocode.RegeoParameter;
import per.cby.frame.gis.service.gaode.geocode.RegeoResult;
import per.cby.frame.gis.service.gaode.geocode.RegeoResult.Regeocode;
import per.cby.frame.gis.service.gaode.geocode.RegeoResult.Regeocode.Poi;

/**
 * 地址服务接口实现类
 * 
 * @author chenboyang
 *
 */
@Lazy
@Service("__ADDRESS_SERVICE_IMPL__")
public class AddressServiceImpl implements AddressService {

    @Autowired(required = false)
    private GeocodeService geocodeService;

    @Override
    public String geoAddress(Point point) {
        return Optional.ofNullable(geoAddress(BaseUtil.newArrayList(point))).map(t -> t.get(0)).orElse(null);
    }

    @Override
    public List<String> geoAddress(List<Point> points) {
        List<String> addressList = new ArrayList<String>(points.size());
        RegeoParameter regeoParameter = new RegeoParameter();
        regeoParameter.setExtensions(RegeoParameter.EXTENSIONS_ALL);
        regeoParameter.setPoints(points);
        if (points.size() > 1) {
            regeoParameter.setBatch(true);
        }
        RegeoResult result = geocodeService.regeo(regeoParameter);
        if (result.getStatus() == RegeoResult.SUCCESS) {
            if (CollectionUtils.isNotEmpty(result.getRegeocodes())) {
                result.getRegeocodes().forEach((regeo) -> {
                    addressList.add(resolveResult(regeo));
                });
            } else if (result.getRegeocode() != null) {
                addressList.add(resolveResult(result.getRegeocode()));
            }
        }
        return addressList;
    }

    /**
     * 解析逆向地理编码结果
     * 
     * @param result
     *            逆向地理编码结果
     * @return 地理地址
     */
    private String resolveResult(Regeocode result) {
        StringBuilder address = new StringBuilder();
        if (result != null) {
            address.append(result.getFormatted_address());
            if (CollectionUtils.isNotEmpty(result.getPois())) {
                Poi poi = result.getPois().get(0);
                int distance = (int) poi.getDistance();
                address.append(poi.getDirection()).append(distance).append("米");
            }
        }
        return address.toString();
    }

}
