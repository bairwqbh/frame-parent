package per.cby.frame.message.yunpian;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.result.Result;
import per.cby.frame.common.result.ResultFactory;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.message.MessageService;
import com.yunpian.sdk.YunpianClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 云片短信发送服务
 * 
 * @author chenboyang
 * @since 2019年5月20日
 *
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class YunpianMessageService implements MessageService {

    /** 服务访问KEY */
    private final String key;

    /** 分隔符 */
    private final String SEPARATOR = ",";

    /** 字符集编码 */
    private final String CHARSET = "UTF-8";

    @Override
    public Result<?> send(String phone, String text) {
        BusinessAssert.hasText(phone, "手机号不能为空！");
        BusinessAssert.hasText(text, "消息文本不能为空！");
        return execute(client -> client.sms().single_send(genParam(client, phone, text)));
    }

    @Override
    public Result<?> send(List<String> phones, String text) {
        BusinessAssert.notEmpty(phones, "手机号不能为空！");
        BusinessAssert.isTrue(phones.size() <= 1000, "手机号不能超过1000个！");
        BusinessAssert.hasText(text, "消息文本不能为空！");
        return execute(client -> client.sms().batch_send(genParam(client, StringUtils.join(phones, SEPARATOR), text)));
    }

    @Override
    public Result<?> send(List<String> phones, List<String> texts) {
        BusinessAssert.notEmpty(phones, "手机号不能为空！");
        BusinessAssert.isTrue(phones.size() <= 1000, "手机号不能超过1000个！");
        BusinessAssert.isTrue(texts.size() == phones.size(), "手机号数量必须和短信数量相等！");
        return execute(client -> client.sms()
                .multi_send(genParam(client, StringUtils.join(phones, SEPARATOR), StringUtils.join(texts, SEPARATOR))));
    }

    @Override
    public Result<?> send(String phone, String tplId, String tplVal) {
        BusinessAssert.hasText(phone, "手机号不能为空！");
        BusinessAssert.hasText(tplId, "模板编号不能为空！");
        BusinessAssert.hasText(tplVal, "模板值不能为空！");
        return execute(client -> client.sms().single_send(genParam(client, phone, tplId, tplVal)));
    }

    @Override
    public Result<?> send(List<String> phones, String tplId, String tplVal) {
        BusinessAssert.notEmpty(phones, "手机号不能为空！");
        BusinessAssert.isTrue(phones.size() <= 1000, "手机号不能超过1000个！");
        BusinessAssert.hasText(tplId, "模板编号不能为空！");
        BusinessAssert.hasText(tplVal, "模板值不能为空！");
        return execute(client -> client.sms()
                .batch_send(genParam(client, StringUtils.join(phones, SEPARATOR), tplId, tplVal)));
    }

    @Override
    public Result<?> send(List<String> phones, String tplId, List<String> tplVals) {
        BusinessAssert.notEmpty(phones, "手机号不能为空！");
        BusinessAssert.isTrue(phones.size() <= 1000, "手机号不能超过1000个！");
        BusinessAssert.hasText(tplId, "模板编号不能为空！");
        BusinessAssert.isTrue(tplVals.size() == phones.size(), "手机号数量必须和模板值数量相等！");
        return execute(client -> client.sms().multi_send(
                genParam(client, StringUtils.join(phones, SEPARATOR), tplId, StringUtils.join(tplVals, SEPARATOR))));
    }

    /**
     * 获取客户端
     * 
     * @return 客户端
     */
    private YunpianClient client() {
        return new YunpianClient(key).init();
    }

    /**
     * 执行任务
     * 
     * @param function 执行函数
     */
    private Result<?> execute(Function<YunpianClient, com.yunpian.sdk.model.Result<?>> function) {
        YunpianClient client = null;
        try {
            client = client();
            if (client != null) {
                com.yunpian.sdk.model.Result<?> result = function.apply(client);
                return ResultFactory.create(result.getCode(), result.getMsg(), result.getData());
            }
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

    /**
     * 生成请求参数键值容器
     * 
     * @param client 客户端
     * @param phone  手机号
     * @param text   文本
     * @return 键值容器
     */
    private Map<String, String> genParam(YunpianClient client, String phone, String text) {
        Map<String, String> param = client.newParam(2);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TEXT, text);
        return param;
    }

    /**
     * 生成请求参数键值容器
     * 
     * @param client 客户端
     * @param phone  手机号
     * @param tplId  模板编号
     * @param tplId  模板值
     * @return 键值容器
     */
    private Map<String, String> genParam(YunpianClient client, String phone, String tplId, String tplVal) {
        Map<String, String> param = client.newParam(3);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TPL_ID, tplId);
        param.put(YunpianClient.TPL_VALUE, tplVal);
        return param;
    }

    @Override
    public String tplParam(Object obj) {
        if (obj == null) {
            return StringUtils.EMPTY;
        }
        Map<String, Object> tplVal = JsonUtil.toObject(JsonUtil.toJson(obj), Map.class);
        StringBuilder sb = new StringBuilder();
        if (MapUtils.isNotEmpty(tplVal)) {
            try {
                for (Entry<String, Object> entry : tplVal.entrySet()) {
                    sb.append('&').append(URLEncoder.encode(String.format("#%s#", entry.getKey()), CHARSET)).append('=')
                            .append(URLEncoder.encode(String.valueOf(entry.getValue()), CHARSET));
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }
            if (sb.charAt(0) == '&') {
                sb.deleteCharAt(0);
            }
        }
        return sb.toString();
    }

}
