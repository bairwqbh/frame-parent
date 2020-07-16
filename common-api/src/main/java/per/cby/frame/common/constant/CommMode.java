package per.cby.frame.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 系统内部通信方式
 * 
 * @author chenboyang
 * @since 2019年12月9日
 *
 */
@Getter
@RequiredArgsConstructor
public enum CommMode {

    MQ("mq", "消息队列"),
    EVENT("event", "事件监听");

    private final String code;
    private final String desc;

    /**
     * 根据代码获取枚举
     * 
     * @param code 代码
     * @return 枚举
     */
    public static CommMode get(String code) {
        for (CommMode commMode : values()) {
            if (commMode.getCode().equals(code)) {
                return commMode;
            }
        }
        return null;
    }

}
