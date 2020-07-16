package per.cby.frame.common.encrypt.symmetry;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 对称加密器接口抽象类
 * 
 * @author chenboyang
 * @since 2019年6月21日
 *
 */
public abstract class AbstractCryptor implements Cryptor {

    /** 字符集 */
    protected static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    /** 缺省密钥 */
    protected final byte[] defaultKey = { 93, 119, -112, 108, -29, -125, 31, -12, 55, 38, -116, -45, -3, -49, -33, -31,
            -37, -117, 8, -30, 21, 8, -93, -69, 13, -23, 7, 14, -81, -34, -88, 56, 95, 97, -12, -126, 81, 29, 125, -117,
            36, -106, 32, 115, -7, 108, -14, 58, -88, 59, -106, 125, -89, 18, -88, 80, 48, -68, 20, 62, 57, -88, -30,
            66, -71, 14, -50, 55, 78, 17, 100, -116, -35, -33, 47, -107, 106, -17, -104, 114, 82, 120, -99, 33, -125,
            -76, 115, -83, 101, 39, -2, -27, 79, -108, 51, 43, -110, 124, -124, -77, 21, -81, 36, 39, -76, 102, 37, -77,
            -120, -123, 119, -1, -85, 7, -27, -117, 53, -26, 92, 45, 61, 46, 31, -97, 78, -68, 87, -76 };

    /** 对称加密器 */
    private static volatile Cryptor cryptor = null;

    /**
     * 获取对称加密器
     * 
     * @return 对称加密器
     */
    public static Cryptor cryptor() {
        return Optional.ofNullable(cryptor).orElseGet(() -> cryptor = new SimpleCryptor());
    }

    @Override
    public String encrypt(String data) {
        return new String(encrypt(data.getBytes()), CHARSET);
    }

    @Override
    public String decrypt(String data) {
        return new String(decrypt(data.getBytes(CHARSET)));
    }

    @Override
    public String encrypt(String data, String key) {
        return new String(crypt(data.getBytes(), key.getBytes()), CHARSET);
    }

    @Override
    public String decrypt(String data, String key) {
        return new String(crypt(data.getBytes(CHARSET), key.getBytes()));
    }

}
