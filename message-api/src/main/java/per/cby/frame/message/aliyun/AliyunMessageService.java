package per.cby.frame.message.aliyun;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.result.Result;
import per.cby.frame.common.result.ResultEnum;
import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.message.MessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云短信服务
 * 
 * @author chenboyang
 * @since 2019年5月17日
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public class AliyunMessageService implements MessageService {

    @Autowired(required = false)
    private IAcsClient iAcsClient;

    @Autowired(required = false)
    private AliyunSmsProperties aliyunSmsProperties;

    @Override
    @Deprecated
    public Result<?> send(String phone, String text) {
        return ResultEnum.FAIL.message("此功能无法使用！");
    }

    @Override
    @Deprecated
    public Result<?> send(List<String> phones, String text) {
        return ResultEnum.FAIL.message("此功能无法使用！");
    }

    @Override
    @Deprecated
    public Result<?> send(List<String> phones, List<String> texts) {
        return ResultEnum.FAIL.message("此功能无法使用！");
    }

    @Override
    public Result<?> send(String phone, String tplId, String tplVal) {
        try {
            CommonRequest request = getRequest(AliyunSmsConstant.ACTION_SEND_SMS);
            request.putQueryParameter(AliyunSmsConstant.PHONE_NUMBERS, phone);
            request.putQueryParameter(AliyunSmsConstant.SIGN_NAME, aliyunSmsProperties.getSign());
            request.putQueryParameter(AliyunSmsConstant.TEMPLATE_CODE, tplId);
            request.putQueryParameter(AliyunSmsConstant.TEMPLATE_PARAM, tplVal);
            CommonResponse response = iAcsClient.getCommonResponse(request);
            return valueResult(response);
        } catch (ClientException e) {
            log.error(e.getMessage(), e);
        }
        return ResultEnum.FAIL.result();
    }

    @Override
    public Result<?> send(List<String> phones, String tplId, String tplVal) {
        try {
            BusinessAssert.isTrue(phones.size() <= 1000, "手机号不能超过1000个！");
            CommonRequest request = getRequest(AliyunSmsConstant.ACTION_SEND_SMS);
            request.putQueryParameter(AliyunSmsConstant.PHONE_NUMBERS, String.join(",", phones));
            request.putQueryParameter(AliyunSmsConstant.SIGN_NAME, aliyunSmsProperties.getSign());
            request.putQueryParameter(AliyunSmsConstant.TEMPLATE_CODE, tplId);
            request.putQueryParameter(AliyunSmsConstant.TEMPLATE_PARAM, tplVal);
            CommonResponse response = iAcsClient.getCommonResponse(request);
            return valueResult(response);
        } catch (ClientException e) {
            log.error(e.getMessage(), e);
        }
        return ResultEnum.FAIL.result();
    }

    @Override
    public Result<?> send(List<String> phones, String tplId, List<String> tplVals) {
        try {
            BusinessAssert.isTrue(phones.size() <= 100, "手机号不能超过100个！");
            BusinessAssert.isTrue(phones.size() == tplVals.size(), "手机号数量必须跟模板结果数量一致！");
            CommonRequest request = getRequest(AliyunSmsConstant.ACTION_SEND_BATCH_SMS);
            request.putQueryParameter(AliyunSmsConstant.PHONE_NUMBER_JSON, JsonUtil.toJson(phones));
            request.putQueryParameter(AliyunSmsConstant.SIGN_NAME_JSON, "[\"" + aliyunSmsProperties.getSign() + "\"]");
            request.putQueryParameter(AliyunSmsConstant.TEMPLATE_CODE, tplId);
            request.putQueryParameter(AliyunSmsConstant.TEMPLATE_PARAM_JSON, tplVals.toString());
            CommonResponse response = iAcsClient.getCommonResponse(request);
            return valueResult(response);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return ResultEnum.FAIL.result();
    }

    /**
     * 获取服务请求基本信息
     * 
     * @param action 服务业务
     * @return 请求信息
     */
    private CommonRequest getRequest(String action) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction(action);
        return request;
    }

    /**
     * 根据响应信息赋值返回结果
     * 
     * @param response 响应信息
     * @return 返回结果
     */
    private Result<?> valueResult(CommonResponse response) {
        if (StringUtils.isNotBlank(response.getData())) {
            Map<String, String> map = JsonUtil.toObject(response.getData(), Map.class);
            if ("OK".equals(map.get("Code"))) {
                return ResultEnum.SUCCESS.message("发送成功！").setData(map);
            } else {
                return ResultEnum.FAIL.message(map.get("Message")).setData(map);
            }
        }
        return ResultEnum.FAIL.result();
    }

    @Override
    public String tplParam(Object obj) {
        if (obj == null) {
            return StringUtils.EMPTY;
        }
        return JsonUtil.toJson(obj);
    }

}
