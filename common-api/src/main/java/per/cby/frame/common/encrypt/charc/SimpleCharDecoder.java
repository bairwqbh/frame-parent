package per.cby.frame.common.encrypt.charc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符置换解密接口实现类
 * 
 * @author chenboyang
 *
 */
public class SimpleCharDecoder extends CharCoder implements CharDecoder {

    /**
     * 解密置换容器
     */
    private final Map<Character, Character> decodeMap = new HashMap<Character, Character>();

    /**
     * 构造字符解密器
     */
    public SimpleCharDecoder() {
        init();
    }

    /**
     * 初始化解密器
     */
    private void init() {
        for (int i = 0; i < CHAR_CODES.length; i++) {
            decodeMap.put(CHAR_CODES[i], CHAR_CODES[decodePosition(i)]);
        }
    }

    /**
     * 根据加密位获取对应的解密位
     * 
     * @param index 加密位
     * @return 解密位
     */
    private int decodePosition(int index) {
        int length = CHAR_CODES.length;
        if (length > index) {
            int center = length >>> 1;
            if (index == center) {
                index = length - 1;
            } else if (index < center) {
                index = length - ((index + 1) << 1);
            } else if (index > center) {
                index = ((index - center) - 1) << 1;
            }
        }
        return index;
    }

    @Override
    public String decode(String str) {
        if (StringUtils.isNotEmpty(str)) {
            char[] chars = str.toCharArray();
            char[] newChars = new char[chars.length];
            for (int i = 0; i < chars.length; i++) {
                if (decodeMap.containsKey(chars[i])) {
                    newChars[i] = decodeMap.get(chars[i]);
                } else {
                    newChars[i] = chars[i];
                }
            }
            str = String.valueOf(newChars);
        }
        return str;
    }

    @Override
    public String decode(String str, int num) {
        if (num >= ENCODE_LIMIT[0]) {
            if (num > ENCODE_LIMIT[1]) {
                num = ENCODE_LIMIT[1];
            }
            for (int i = 0; i < num; i++) {
                str = decode(str);
            }
        }
        return str;
    }

}
