package per.cby.frame.common.encrypt.charc;

/**
 * 字符置换加密接口
 * 
 * @author chenboyang
 *
 */
public interface CharEncoder {

    /**
     * 根据字符串进行加密
     * 
     * @param str 需要加密的内容
     * @return 加密后的内容
     */
    String encode(String str);

    /**
     * 根据字符串和加密次数进行加密
     * 
     * @param str 需要加密的内容
     * @param num 加密次数
     * @return 加密后的内容
     */
    String encode(String str, int num);

}
