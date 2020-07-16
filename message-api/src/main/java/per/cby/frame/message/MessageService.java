package per.cby.frame.message;

import java.util.List;
import java.util.stream.Collectors;

import per.cby.frame.common.result.Result;

/**
 * 手机短信发送服务接口
 * 
 * @author chenboyang
 *
 */
public interface MessageService {

    /**
     * 单条消息发送
     * 
     * @param phone 手机号
     * @param text  消息文本
     * @return 发送结果
     */
    Result<?> send(String phone, String text);

    /**
     * 消息批量发送
     * 
     * @param phones 手机号集合
     * @param text   消息文本
     * @return 发送结果
     */
    Result<?> send(List<String> phones, String text);

    /**
     * 消息批量发送
     * 
     * @param phones 手机号集合
     * @param texts  消息文本集合
     * @return 发送结果
     */
    Result<?> send(List<String> phones, List<String> texts);

    /**
     * 单条消息发送
     * 
     * @param phone 手机号
     * @param tplId 模板编号
     * @param param 参数
     * @return 发送结果
     */
    Result<?> send(String phone, String tplId, String param);

    /**
     * 消息批量发送
     * 
     * @param phones 手机号集合
     * @param tplId  模板编号
     * @param param  参数
     * @return 发送结果
     */
    Result<?> send(List<String> phones, String tplId, String param);

    /**
     * 消息批量发送
     * 
     * @param phones 手机号集合
     * @param tplId  模板编号
     * @param params 参数集合
     * @return 发送结果
     */
    Result<?> send(List<String> phones, String tplId, List<String> params);

    /**
     * 生成模板参数
     * 
     * @param obj 参数对象
     * @return 参数字符串
     */
    String tplParam(Object obj);

    /**
     * 单条消息发送
     * 
     * @param phone 手机号
     * @param tplId 模板编号
     * @param obj   参数对象
     * @return 发送结果
     */
    default Result<?> sendObj(String phone, String tplId, Object obj) {
        return send(phone, tplId, tplParam(obj));
    }

    /**
     * 消息批量发送
     * 
     * @param phones 手机号集合
     * @param tplId  模板编号
     * @param obj    参数对象
     * @return 发送结果
     */
    default Result<?> sendObj(List<String> phones, String tplId, Object obj) {
        return send(phones, tplId, tplParam(obj));
    }

    /**
     * 消息批量发送
     * 
     * @param phones 手机号集合
     * @param tplId  模板编号
     * @param objs   参数对象集合
     * @return 发送结果
     */
    default Result<?> sendObj(List<String> phones, String tplId, List<? extends Object> objs) {
        return send(phones, tplId, objs.stream().map(this::tplParam).collect(Collectors.toList()));
    }

}
