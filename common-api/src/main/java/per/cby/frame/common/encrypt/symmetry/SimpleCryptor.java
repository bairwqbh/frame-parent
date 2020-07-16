package per.cby.frame.common.encrypt.symmetry;

/**
 * 对称加密器接口简单实现
 * 
 * @author chenboyang
 * @since 2019年6月21日
 *
 */
public class SimpleCryptor extends AbstractCryptor {

    /** 第一盐值 */
    private final byte salt1 = 0b01111100;

    /** 第二盐值 */
    private final byte salt2 = 0b01001011;

    @Override
    public byte[] crypt(byte[] data, byte[] key) {
        if (key.length <= 0) {
            key = defaultKey;
        }
        byte[] code = new byte[data.length];
        int ki = 0;
        for (int i = 0; i < data.length; i++) {
            code[i] = crypt(data[i], key[ki]);
            if (ki < key.length - 1) {
                ki++;
            } else {
                ki = 0;
            }
        }
        return code;
    }

    @Override
    public byte[] encrypt(byte[] data) {
        byte[] code = new byte[data.length];
        int center = data.length / 2;
        if (data.length % 2 == 1) {
            code[center] = crypt(data[center]);
        }
        for (int i = 0; i < center; i++) {
            boolean isEven = i % 2 == 0;
            int li = data.length - i - 1;
            if (isEven) {
                byte d = data[i];
                byte k = data[li];
                d = crypt(d, k);
                k = crypt(k);
                code[i] = d;
                code[li] = k;
            } else {
                byte d = data[li];
                byte k = data[i];
                d = crypt(d, k);
                k = crypt(k);
                code[li] = d;
                code[i] = k;
            }
        }
        return code;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        byte[] code = new byte[data.length];
        int center = data.length / 2;
        if (data.length % 2 == 1) {
            code[center] = crypt(data[center]);
        }
        for (int i = 0; i < center; i++) {
            boolean isEven = i % 2 == 0;
            int li = data.length - i - 1;
            if (isEven) {
                byte d = data[i];
                byte k = data[li];
                k = crypt(k);
                d = crypt(d, k);
                code[i] = d;
                code[li] = k;
            } else {
                byte d = data[li];
                byte k = data[i];
                k = crypt(k);
                d = crypt(d, k);
                code[li] = d;
                code[i] = k;
            }
        }
        return code;
    }

    /**
     * 根据密钥对数据进行加解密
     * 
     * @param d 数据
     * @param k 密钥
     * @return 数据
     */
    private byte crypt(byte d, byte k) {
        return (byte) ~(d ^ salt1 ^ k ^ salt2);
    }

    /**
     * 对数据进行加解密
     * 
     * @param d 数据
     * @return 数据
     */
    private byte crypt(byte d) {
        return (byte) ~(salt1 ^ d ^ salt2);
    }

}
