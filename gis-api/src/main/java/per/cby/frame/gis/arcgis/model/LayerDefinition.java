package per.cby.frame.gis.arcgis.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图层定义类
 * 
 * @author chenboyang
 *
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class LayerDefinition {

    /** 主键字段 */
    public static final String PK_FIELD = "objectid";

    /** 几何类型 */
    private String geometryType;

    /** 对象编号字段 */
    private String objectIdField = PK_FIELD;

    /** 字段集 */
    private List<Field> fields;

    /**
     * 构造函数
     * 
     * @param geometryType 几何类型
     * @param fields       字段集
     */
    public LayerDefinition(String geometryType, List<Field> fields) {
        this(geometryType, PK_FIELD, fields);
    }

}
