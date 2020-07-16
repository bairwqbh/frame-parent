package per.cby.frame.common.encrypt.charc;

/**
 * 字符解密接口
 * 
 * @author chenboyang
 *
 */
public interface CharDecoder {

    /**
     * 根据字符串进行解密
     * 
     * @param str 需要解密的内容
     * @return 解密后的内容
     */
    String decode(String str);

    /**
     * 根据字符串进行解密
     * 
     * @param str 需要解密的内容
     * @param num 解密次数
     * @return 解密后的内容
     */
    String decode(String str, int num);

}
