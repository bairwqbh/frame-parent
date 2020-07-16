package per.cby.frame.common.encrypt.charc;

import java.util.Optional;

/**
 * 字符置换加密器抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class CharCoder {

    /** 加密代码字符集 */
    protected static final char[] CHAR_CODES = { '`', '1', 'q', 'a', 'z', '2', 'w', 's', 'x', '3', 'e', 'd', 'c', '4',
            'r', 'f', 'v', '5', 't', 'g', 'b', '6', 'y', 'h', 'n', '7', 'u', 'j', 'm', '8', 'i', 'k', ',', '9', 'o',
            'l', '.', '0', 'p', ';', '/', '-', '[', '\'', '=', ']', '\\', ' ', '~', '!', 'Q', 'A', 'Z', '@', 'W', 'S',
            'X', '#', 'E', 'D', 'C', '$', 'R', 'F', 'V', '%', 'T', 'G', 'B', '^', 'Y', 'H', 'N', '&', 'U', 'J', 'M',
            '*', 'I', 'K', '<', '(', 'O', 'L', '>', ')', 'P', ':', '?', '_', '{', '"', '+', '}', '|' };

    /** 加密次数范围 */
    protected static int[] ENCODE_LIMIT = { 1, 11 };

    /** 加密器 */
    private static volatile CharEncoder encoder = null;

    /** 解密器 */
    private static volatile CharDecoder decoder = null;

    /**
     * 获取加密器
     * 
     * @return 加密器
     */
    public static CharEncoder encoder() {
        return Optional.ofNullable(encoder).orElseGet(() -> encoder = new SimpleCharEncoder());
    }

    /**
     * 获取解密器
     * 
     * @return 解密器
     */
    public static CharDecoder decoder() {
        return Optional.ofNullable(decoder).orElseGet(() -> decoder = new SimpleCharDecoder());
    }

}
