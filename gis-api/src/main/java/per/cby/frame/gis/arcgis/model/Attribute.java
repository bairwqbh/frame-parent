package per.cby.frame.gis.arcgis.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 空间要素业务属性类
 * 
 * @author chenboyang
 * 
 */
@Data
@Accessors(chain = true)
public abstract class Attribute {

    /** 对象编号 */
    protected Integer objectid;

    /** 是否加载要素集 */
    protected boolean loadFeatures;

}
