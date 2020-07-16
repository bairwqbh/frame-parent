package per.cby.frame.common.encrypt.bytec;

/**
 * 字节加密接口
 * 
 * @author chenboyang
 *
 */
public interface ByteEncoder {

    /**
     * 根据字符串进行加密
     * 
     * @param str 字符串
     * @return 加密后的字符串
     */
    String encode(String str);

    /**
     * 根据字符串和加密次数进行加密
     * 
     * @param str 字符串
     * @param num 加密次数
     * @return 加密后的字符串
     */
    String encode(String str, int num);

    /**
     * 根据字节数组进行加密
     * 
     * @param bytes 字节数组
     * @return 加密后的字节数组
     */
    byte[] encode(byte[] bytes);

    /**
     * 根据字节数组和加密次数进行加密
     * 
     * @param bytes 字节数组
     * @param num   加密次数
     * @return 加密后的字节数组
     */
    byte[] encode(byte[] bytes, int num);

    /**
     * 根据字节进行加密
     * 
     * @param b 字节
     * @return 加密后的字节
     */
    byte encode(byte b);

    /**
     * 根据字节和加密次数进行加密
     * 
     * @param b   字节
     * @param num 加密次数
     * @return 加密后的字节
     */
    byte encode(byte b, int num);

}
