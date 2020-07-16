package per.cby.frame.gis.service.gaode.geocode;

import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.gis.service.gaode.AbstractGaodeService;
import per.cby.frame.gis.service.gaode.GaodeParameter;

/**
 * 高德地理编码服务接口
 * 
 * @author chenboyang
 *
 */
@Lazy
@Service("__GEOCODE_SERVICE__")
public class GeocodeService extends AbstractGaodeService {

    @Autowired(required = false)
    private GeocodeClient geocodeClient;

    /**
     * 逆向地理编码请求服务
     * 
     * @param param 高德地理编码服务请求参数
     * @return 逆向地理编码响应结果
     */
    public RegeoResult regeo(RegeoParameter param) {
        BusinessAssert.notNull(param, "参数不能为空！");
        BusinessAssert.notEmpty(param.getPoints(), "坐标不能为空！");
        param.setLocation(param.getPoints().stream().map(point -> StringUtils.join(point.getCoords(), ','))
                .collect(Collectors.joining("|")));
        if (GaodeParameter.EXTENSIONS_ALL.equals(param.getExtensions())) {
            if (CollectionUtils.isNotEmpty(param.getPois())) {
                param.setPoitype(param.getPois().stream().collect(Collectors.joining("|")));
            }
        }
        return execute(param, RegeoResult.class, map -> geocodeClient.regeo(map));
    }

}
