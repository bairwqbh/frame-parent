package per.cby.frame.common.useable;

import per.cby.frame.common.encrypt.bytec.ByteCoder;
import per.cby.frame.common.encrypt.charc.CharCoder;
import per.cby.frame.common.encrypt.symmetry.AbstractCryptor;
import per.cby.frame.common.util.BaseUtil;

/**
 * 统一用户服务接口
 * 
 * @author chenboyang
 *
 */
public interface UseableService {

    /**
     * 获取帐号信息
     * 
     * @return 帐号信息
     */
    Useable getAccount(String account);

    /**
     * 获取加密类型
     * 
     * @return 加密类型
     */
    EncryptEnum encrypt();

    /**
     * 密码加密
     * 
     * @param password 密码
     * @return 密码加密数据
     */
    default String passwordEncode(String password) {
        switch (encrypt()) {
            case MD5:
                return BaseUtil.md5Encode(password);
            case REPLACE_CHAR:
                return CharCoder.encoder().encode(password);
            case REPLACE_BYTE:
                return ByteCoder.encoder().encode(password);
            case SYMMETRY:
                return AbstractCryptor.cryptor().encrypt(password);
            default:
                return password;
        }
    }

}
