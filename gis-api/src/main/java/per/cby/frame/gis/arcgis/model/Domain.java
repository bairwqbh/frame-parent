package per.cby.frame.gis.arcgis.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字段领域类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class Domain {

    /** 名称 */
    private String name;

    /** 类型 */
    private String type;

}
