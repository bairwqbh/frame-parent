package per.cby.frame.common.encrypt.bytec;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字节解密接口实现类
 * 
 * @author chenboyang
 *
 */
public class SimpleByteDecoder extends ByteCoder implements ByteDecoder {

    @Override
    public String decode(String str) {
        return decode(str, DEFAULT_ENCRYPT_NUM);
    }

    @Override
    public byte[] decode(byte[] bytes) {
        return decode(bytes, DEFAULT_ENCRYPT_NUM);
    }

    @Override
    public String decode(String str, int num) {
        if (StringUtils.isNotEmpty(str) && num > 0) {
            str = new String(decode(str.getBytes(CHARSET), num));
        }
        return str;
    }

    @Override
    public byte[] decode(byte[] bytes, int num) {
        if (ArrayUtils.isNotEmpty(bytes)) {
            byte[] newBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                newBytes[i] = decode(bytes[i], num);
            }
            bytes = newBytes;
        }
        return bytes;
    }

    @Override
    public byte decode(byte b) {
        return decode(b, DEFAULT_ENCRYPT_NUM);
    }

    @Override
    public byte decode(byte b, int num) {
        if (num >= ENCODE_LIMIT[0]) {
            if (num > ENCODE_LIMIT[1]) {
                num = ENCODE_LIMIT[1];
            }
            for (int i = 0; i < num; i++) {
                b = decodeByte(b);
            }
        }
        return b;
    }

    /**
     * 根据加密字节获取对应的解密字节
     * 
     * @param b 加密字节
     * @return 解密字节
     */
    private byte decodeByte(byte b) {
        if (b < 0) {
            b = (byte) (MIN_VALUE + (Math.abs(b) << 1) - 1);
        } else {
            b = (byte) (MIN_VALUE + (b << 1));
        }
        return b;
    }

}
