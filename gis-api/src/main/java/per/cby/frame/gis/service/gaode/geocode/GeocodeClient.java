package per.cby.frame.gis.service.gaode.geocode;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import per.cby.frame.gis.service.gaode.GaodeConfig;

/**
 * 高德地理编码服务接口客户端
 * 
 * @author chenboyang
 *
 */
@Lazy
@FeignClient(name = "geocodeClient", url = GaodeConfig.SERVICE_URL + "/geocode")
public interface GeocodeClient {

    /**
     * 逆向地理编码请求服务
     * 
     * @param param 高德地理编码服务请求参数
     * @return 逆向地理编码响应结果
     */
    @PostMapping("/regeo")
    String regeo(@RequestParam Map<String, Object> param);

}
