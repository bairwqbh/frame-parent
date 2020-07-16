package per.cby.frame.message.aliyun;

import lombok.experimental.UtilityClass;

/**
 * 阿里云短信发送相关常量
 * 
 * @author chenboyang
 * @since 2019年5月17日
 *
 */
@UtilityClass
public class AliyunSmsConstant {

    /** 短信发送 */
    public final String ACTION_SEND_SMS = "SendSms";

    /** 批量短信发送 */
    public final String ACTION_SEND_BATCH_SMS = "SendBatchSms";

    /** 手机号 */
    public final String PHONE_NUMBERS = "PhoneNumbers";

    /** 签名 */
    public final String SIGN_NAME = "SignName";

    /** 模板编号 */
    public final String TEMPLATE_CODE = "TemplateCode";

    /** 模板参数 */
    public final String TEMPLATE_PARAM = "TemplateParam";

    /** 手机号JSON数组 */
    public final String PHONE_NUMBER_JSON = "PhoneNumberJson";

    /** 签名JSON数组 */
    public final String SIGN_NAME_JSON = "SignNameJson";

    /** 模板参数JSON数组 */
    public final String TEMPLATE_PARAM_JSON = "TemplateParamJson";

}
