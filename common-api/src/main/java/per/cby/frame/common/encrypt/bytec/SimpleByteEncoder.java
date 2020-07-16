package per.cby.frame.common.encrypt.bytec;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字节加密接口实现类
 * 
 * @author chenboyang
 *
 */
public class SimpleByteEncoder extends ByteCoder implements ByteEncoder {

    @Override
    public String encode(String str) {
        return encode(str, DEFAULT_ENCRYPT_NUM);
    }

    @Override
    public String encode(String str, int num) {
        if (StringUtils.isNotEmpty(str) && num > 0) {
            str = new String(encode(str.getBytes(), num), CHARSET);
        }
        return str;
    }

    @Override
    public byte[] encode(byte[] bytes) {
        return encode(bytes, DEFAULT_ENCRYPT_NUM);
    }

    @Override
    public byte[] encode(byte[] bytes, int num) {
        if (ArrayUtils.isNotEmpty(bytes)) {
            byte[] newBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                newBytes[i] = encode(bytes[i], num);
            }
            bytes = newBytes;
        }
        return bytes;
    }

    @Override
    public byte encode(byte b) {
        return encode(b, DEFAULT_ENCRYPT_NUM);
    }

    @Override
    public byte encode(byte b, int num) {
        if (num >= ENCODE_LIMIT[0]) {
            if (num > ENCODE_LIMIT[1]) {
                num = ENCODE_LIMIT[1];
            }
            for (int i = 0; i < num; i++) {
                b = encodeByte(b);
            }
        }
        return b;
    }

    /**
     * 根据字节获取对应的加密字节
     * 
     * @param b 字节
     * @return 加密字节
     */
    private byte encodeByte(byte b) {
        int center = EXTENT >>> 1;
        if (b % 2 == 0) {
            b = (byte) ((center + b) >>> 1);
        } else {
            if (b > 0) {
                b = (byte) ((-(b + 1) + MIN_VALUE) >>> 1);
            } else {
                b = (byte) ((Math.abs(b + 1) >>> 1) - (center >>> 1));
            }
        }
        return b;
    }

}
