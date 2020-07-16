package per.cby.frame.gis.service.google.elevation;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.gis.geometry.Point;
import per.cby.frame.gis.service.google.elevation.GoogleElevationResult.Elevation;

/**
 * 谷歌高程服务
 * 
 * @author chenboyang
 * @since 2019年5月23日
 *
 */
@Lazy
@Service("__GOOGLE_ELEVATION_SERVICE_IMPL__")
public class GoogleElevationServiceImpl implements GoogleElevationService {

    /** 服务请求密钥 */
    private final String KEY = "AIzaSyDjzEW3P2rTdk30uDuo6h4-PxTwgoJGrT4";

    @Autowired(required = false)
    private GoogleElevationClient googleElevationClient;

    @Override
    public Elevation elevation(Point point) {
        List<Elevation> list = elevation(BaseUtil.newArrayList(point));
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Elevation> elevation(List<Point> pointList) {
        String locations = pointList.stream().map(t -> String.format("%s,%s", t.getY(), t.getX()))
                .collect(Collectors.joining("|"));
        GoogleElevationResult result = googleElevationClient.elevation(KEY, locations);
        if (GoogleElevationResult.OK.equals(result.getStatus())) {
            return result.getResults();
        }
        return null;
    }

}
