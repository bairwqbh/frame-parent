package per.cby.frame.web.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import per.cby.frame.common.util.BaseUtil;

/**
 * Web常量
 * 
 * @author chenboyang
 * @since 2020年5月6日
 *
 */
public interface WebConstant {

    /** 字符集编码 */
    String CHARSET = "UTF-8";

    /** 缓存控制 */
    String CACHE_CONTROL = "no-cache, must-revalidate";

    /** token参数名 */
    String TOKEN = "token";

    /** 用户信息参数名 */
    String USER = "user";

    /** 定界符 */
    String DELIMITER = ",";

    /** 允许的头信息列表 */
    List<String> ALLOWED_HEADERS = BaseUtil.newArrayList(HttpHeaders.ORIGIN, "X-Requested-With", "X-Requested-With",
            HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT, HttpHeaders.AUTHORIZATION, TOKEN);

    /** 允许的头信息字符串 */
    String ALLOWED_HEADERS_VALUE = String.join(DELIMITER, ALLOWED_HEADERS);

    /** 允许的请求方式 */
    List<HttpMethod> ALLOWED_METHODS = Arrays.asList(HttpMethod.values());

    /** 允许的头信息字符串 */
    String ALLOWED_METHODS_VALUE = ALLOWED_METHODS.stream().map(HttpMethod::name)
            .collect(Collectors.joining(DELIMITER));

    /** 允许的最大时间 */
    Long MAX_AGE = 3600L;

    /** 盐值 */
    String SALT = BaseUtil.md5Encode("zAq!2#4RfVgY7*9)_206d7d5ed8s92dae1i710f6ahc9fc7f4");

}
