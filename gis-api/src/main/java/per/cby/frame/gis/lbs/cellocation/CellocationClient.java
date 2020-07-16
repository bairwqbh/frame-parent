package per.cby.frame.gis.lbs.cellocation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 垃圾LBS服务接口客户端
 * 
 * @author chenboyang
 *
 */
@FeignClient(name = "cellocationClient", url = "http://api.cellocation.com:81")
public interface CellocationClient {

    /**
     * 基站查询接口
     * 
     * @param mcc    国家代码：中国代码 460
     * @param mnc    网络类型：0移动，1联通(电信对应sid)，十进制
     * @param lac    (电信对应nid)，十进制
     * @param ci     (电信对应bid)，十进制
     * @param coord  坐标类型(wgs84/gcj02/bd09)
     * @param output 返回格式(csv/json/xml)
     * @return 基站位置数据
     */
    @GetMapping("/cell")
    CellResult cell(@RequestParam("mcc") Integer mcc, @RequestParam("mnc") Integer mnc,
            @RequestParam("lac") Integer lac, @RequestParam("ci") Integer ci, @RequestParam("coord") String coord,
            @RequestParam("output") String output);

}
