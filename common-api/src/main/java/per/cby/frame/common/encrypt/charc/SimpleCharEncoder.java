package per.cby.frame.common.encrypt.charc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符置换加密接口实现类
 * 
 * @author chenboyang
 *
 */
public class SimpleCharEncoder extends CharCoder implements CharEncoder {

    /**
     * 加密置换容器
     */
    private final Map<Character, Character> encodeMap = new HashMap<Character, Character>();

    /**
     * 构造字符加密器
     */
    public SimpleCharEncoder() {
        init();
    }

    /**
     * 初始化加密器
     */
    private void init() {
        for (int i = 0; i < CHAR_CODES.length; i++) {
            encodeMap.put(CHAR_CODES[i], CHAR_CODES[encodePosition(i)]);
        }
    }

    /**
     * 根据字符下标获取对应的加密位
     * 
     * @param index
     *            字符下标
     * @return 加密位
     */
    private int encodePosition(int index) {
        int length = CHAR_CODES.length;
        if (length > index) {
            int center = length >>> 1;
            if (index % 2 == 0) {
                index = center + (index >>> 1);
                if (length % 2 != 0) {
                    if (index == length - 1) {
                        index = center;
                    } else {
                        index += 1;
                    }
                }
            } else {
                index = center - ((index + 1) >>> 1);
            }
        }
        return index;
    }

    @Override
    public String encode(String str) {
        if (StringUtils.isNotEmpty(str)) {
            char[] chars = str.toCharArray();
            char[] newChars = new char[chars.length];
            for (int i = 0; i < chars.length; i++) {
                if (encodeMap.containsKey(chars[i])) {
                    newChars[i] = encodeMap.get(chars[i]);
                } else {
                    newChars[i] = chars[i];
                }
            }
            str = String.valueOf(newChars);
        }
        return str;
    }

    @Override
    public String encode(String str, int num) {
        if (num >= ENCODE_LIMIT[0]) {
            if (num > ENCODE_LIMIT[1]) {
                num = ENCODE_LIMIT[1];
            }
            for (int i = 0; i < num; i++) {
                str = encode(str);
            }
        }
        return str;
    }

}
