package per.cby.frame.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * 字符串处理帮助类
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class StringUtil extends StringUtils {

    /** 16进制正则表达式 */
    public final String HEX_REGEX = "^[0-9a-fA-F]+$";

    /** 加密符号 */
    public final String ENCRYPT_SYMBOL = "*";

    /**
     * 验证是否为16进制格式
     * 
     * @param str 字符串
     * @return 验证结果
     */
    public boolean isHex(String str) {
        return isMatche(str, HEX_REGEX);
    }

    /**
     * 是否匹配正则表达式
     * 
     * @param str   字符串
     * @param regex 正则表达式
     * @return 是否符合
     */
    public boolean isMatche(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 获取符合正则表达式的分组
     * 
     * @param str   字符串
     * @param regex 正则表达式
     * @param group 分组下标
     * @return 分组
     */
    public String getGroup(String str, String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return matcher.group(group).trim();
        }
        return "";
    }

    /**
     * 字符串是否为数字
     * 
     * @param obj 对象
     * @return 是否为数字
     */
    public boolean isNumeric(Object obj) {
        return obj != null && isMatche(obj.toString(), "-?\\d+?|-?\\d+\\.?\\d+?");
    }

    /**
     * 判断对象是否不为空白
     * 
     * @param obj 对象
     * @return 是否不为空白
     */
    public boolean isNotBlank(Object obj) {
        return obj != null && isNotBlank(obj.toString());
    }

    /**
     * 判断对象是否为空白
     * 
     * @param obj 对象
     * @return 是否为空白
     */
    public boolean isBlank(Object obj) {
        return obj == null || isBlank(obj.toString());
    }

    /**
     * 判断对象是否不为空白
     * 
     * @param obj 对象
     * @return 是否不为空白
     */
    public boolean isNotEmpty(Object obj) {
        return obj != null && isNotEmpty(obj.toString());
    }

    /**
     * 判断对象是否为空白
     * 
     * @param obj 对象
     * @return 是否为空白
     */
    public boolean isEmpty(Object obj) {
        return obj == null || isEmpty(obj.toString());
    }

    /**
     * 下划线转驼峰
     * 
     * @param str 字符串
     * @return 字符串
     */
    public String lineToCamel(String str) {
        str = str.toLowerCase();
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     * 
     * @param str 字符串
     * @return 字符串
     */
    public String camelToLine(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 真实姓名脱敏
     * 
     * @param name 真实姓名
     * @return 真实姓名
     */
    public String nameEncrypt(String name) {
        if (isEmpty(name) || name.length() < 2) {
            return name;
        }
        int length = name.length();
        int start = 1;
        int size = length > 3 ? length - 2 : start;
        int end = start + size;
        return overlay(name, repeat(ENCRYPT_SYMBOL, size), start, end);
    }

    /**
     * 手机号码前三后四脱敏
     * 
     * @param mobile 手机号码
     * @return 手机号码
     */
    public String mobileEncrypt(String mobile) {
        if (isEmpty(mobile) || mobile.length() < 11) {
            return mobile;
        }
        int length = mobile.length();
        int size = 4;
        return overlay(mobile, repeat(ENCRYPT_SYMBOL, size), length - size * 2, length - size);
    }

    /**
     * 身份证号前六后四脱敏
     * 
     * @param idCard 身份证号
     * @return 身份证号
     */
    public String idCardEncrypt(String idCard) {
        if (isEmpty(idCard) || idCard.length() < 15) {
            return idCard;
        }
        int length = idCard.length();
        return overlay(idCard, repeat(ENCRYPT_SYMBOL, length - 10), 6, length - 4);
    }

    /**
     * 护照号码前二后三位脱敏
     * 
     * @param passport 护照号码
     * @return 护照号码
     */
    public String passportEncrypt(String passport) {
        if (isEmpty(passport) || (passport.length() < 9)) {
            return passport;
        }
        int length = passport.length();
        return overlay(passport, repeat(ENCRYPT_SYMBOL, 4), 2, length - 3);
    }

    /**
     * 获取文件名后缀
     * 
     * @param fileName 文件名
     * @return 后缀
     */
    public String getFileNameSuffix(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return StringUtils.EMPTY;
    }

}
