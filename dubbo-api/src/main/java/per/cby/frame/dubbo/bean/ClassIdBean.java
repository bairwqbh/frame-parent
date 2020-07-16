package per.cby.frame.dubbo.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接口详细信息
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class ClassIdBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 接口类信息 */
    private final Class<?> interfaceClass;

    /** 分组 */
    private final String group;

    /** 版本 */
    private final String version;

}
