package per.cby.frame.common.encrypt.bytec;

/**
 * 字节解密接口
 * 
 * @author chenboyang
 *
 */
public interface ByteDecoder {

    /**
     * 根据字符串进行解密
     * 
     * @param str 字符串
     * @return 解密后的字符串
     */
    String decode(String str);

    /**
     * 根据字符串和解密次数进行解密
     * 
     * @param str 字符串
     * @param num 解密次数
     * @return 解密后的字符串
     */
    String decode(String str, int num);

    /**
     * 根据字节数组进行解密
     * 
     * @param bytes 字节数组
     * @return 解密后的字节数组
     */
    byte[] decode(byte[] bytes);

    /**
     * 根据字节数组和解密次数进行解密
     * 
     * @param bytes 字节数组
     * @param num   解密次数
     * @return 解密后的字节数组
     */
    byte[] decode(byte[] bytes, int num);

    /**
     * 根据字节进行解密
     * 
     * @param b 字节
     * @return 解密后的字节
     */
    byte decode(byte b);

    /**
     * 根据字节和解密次数进行解密
     * 
     * @param b   字节
     * @param num 解密次数
     * @return 解密后的字节
     */
    byte decode(byte b, int num);

}
