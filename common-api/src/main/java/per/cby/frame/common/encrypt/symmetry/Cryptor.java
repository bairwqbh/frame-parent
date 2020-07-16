package per.cby.frame.common.encrypt.symmetry;

/**
 * 对称加密器接口
 * 
 * @author chenboyang
 * @since 2019年6月21日
 *
 */
public interface Cryptor {

    /**
     * 对称加密
     * 
     * @param data 数据
     * @return 数据
     */
    String encrypt(String data);

    /**
     * 对称解密
     * 
     * @param data 数据
     * @return 数据
     */
    String decrypt(String data);

    /**
     * 对称加密
     * 
     * @param data 数据
     * @param key  密钥
     * @return 数据
     */
    String encrypt(String data, String key);

    /**
     * 对称解密
     * 
     * @param data 数据
     * @param key  密钥
     * @return 数据
     */
    String decrypt(String data, String key);

    /**
     * 对称加密
     * 
     * @param data 明文数据
     * @return 密文数据
     */
    byte[] encrypt(byte[] data);

    /**
     * 对称解密
     * 
     * @param data 密文数据
     * @return 明文数据
     */
    byte[] decrypt(byte[] data);

    /**
     * 对称加解密
     * 
     * @param data 数据
     * @param key  密钥
     * @return 数据
     */
    byte[] crypt(byte[] data, byte[] key);

}
