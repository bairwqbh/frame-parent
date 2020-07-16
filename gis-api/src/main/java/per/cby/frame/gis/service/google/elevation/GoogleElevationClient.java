package per.cby.frame.gis.service.google.elevation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 谷歌高程服务请求客户端
 * 
 * @author chenboyang
 * @since 2019年5月23日
 *
 */
@Lazy
@FeignClient(name = "googleElevationClient", url = "https://ditu.google.cn/maps/api")
public interface GoogleElevationClient {

    /**
     * 根据坐标获取高程
     * 
     * @param key       访问密钥
     * @param locations 坐标地址,排列格式为：y,x|y,x
     * @return 高程结果
     */
    @GetMapping("/elevation/json")
    GoogleElevationResult elevation(@RequestParam("key") String key, @RequestParam("locations") String locations);

}
