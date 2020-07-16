package per.cby.frame.web.constant;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.common.util.BaseUtil;

/**
 * 验证签名常量
 * 
 * @author chenboyang
 * @since 2020年5月6日
 *
 */
public interface ValidateSignConstant {

    /** 关键标识参数名 */
    String KEY = "key";

    /** 时间戳参数名 */
    String TIMESTAMP = "timestamp";

    /** 签名参数名 */
    String SIGN = "sign";

    /** 过期时间限制（单位：秒） */
    int EXPIRED_LIMIT = 60;

    /** 验证签名允许的头信息 */
    List<String> ALLOWED_HEADERS = CollectionUtils.collate(WebConstant.ALLOWED_HEADERS,
            BaseUtil.newArrayList("key", "timestamp", "sign"));

    /** 验证签名允许的头部字段 */
    String ALLOWED_HEADERS_VALUE = String.join(WebConstant.DELIMITER, ALLOWED_HEADERS);

}
