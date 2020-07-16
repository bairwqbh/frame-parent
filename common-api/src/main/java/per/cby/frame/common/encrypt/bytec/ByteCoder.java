package per.cby.frame.common.encrypt.bytec;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 字节码加密器抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class ByteCoder {

    /** 字节最大值 */
    protected static final byte MAX_VALUE = Byte.MAX_VALUE;

    /** 字节最小值 */
    protected static final byte MIN_VALUE = Byte.MIN_VALUE;

    /** 字节范围 */
    protected static final int EXTENT = (MAX_VALUE - MIN_VALUE) + 1;

    /** 加密次数范围 */
    protected static int[] ENCODE_LIMIT = { 1, 9 };

    /** 默认加密次数 */
    protected static final int DEFAULT_ENCRYPT_NUM = ENCODE_LIMIT[0];

    /** 字符集 */
    protected static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    /** 加密器 */
    private static volatile ByteEncoder encoder = null;

    /** 解密器 */
    private static volatile ByteDecoder decoder = null;

    /**
     * 获取加密器
     * 
     * @return 加密器
     */
    public static ByteEncoder encoder() {
        return Optional.ofNullable(encoder).orElseGet(() -> encoder = new SimpleByteEncoder());
    }

    /**
     * 获取解密器
     * 
     * @return 解密器
     */
    public static ByteDecoder decoder() {
        return Optional.ofNullable(decoder).orElseGet(() -> decoder = new SimpleByteDecoder());
    }

}
