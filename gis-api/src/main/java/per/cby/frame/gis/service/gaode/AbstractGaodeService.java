package per.cby.frame.gis.service.gaode;

import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.constant.FlagString;
import per.cby.frame.common.util.BaseUtil;
import per.cby.frame.common.util.JsonUtil;

/**
 * 高德WEB服务抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractGaodeService {

    /**
     * 服务调用请求执行
     * 
     * @param param    参数
     * @param clazz    返回结果类
     * @param function 请求执行函数
     * @return 返回结果
     */
    protected <P extends GaodeParameter, R extends GaodeResult> R execute(P param, Class<R> clazz,
            Function<Map<String, Object>, String> function) {
        String body = function.apply(BaseUtil.beanToMap(param));
        body = filterStr(body);
        return JsonUtil.toObject(body, clazz);
    }

    /**
     * 过滤异常字符
     * 
     * @param str 字符串
     * @return 过滤后的字符串
     */
    private String filterStr(String str) {
        final int markNum = 8;
        final String nullValue = FlagString.NULL;
        for (int i = markNum; i > 0; i--) {
            String mark = StringUtils.repeat('[', i) + StringUtils.repeat(']', i);
            str = str.replace(mark, nullValue);
        }
        return str;
    }

}
