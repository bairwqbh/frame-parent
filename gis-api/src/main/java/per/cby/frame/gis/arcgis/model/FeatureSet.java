package per.cby.frame.gis.arcgis.model;

import java.util.ArrayList;
import java.util.List;

import per.cby.frame.gis.geometry.Geometry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 要素集
 * 
 * @author chenboyang
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class FeatureSet {

    /** 几何类型 */
    private String geometryType;

    /** 要素集合 */
    private List<Feature<Geometry, Attribute>> features;

    /**
     * 构造函数
     * 
     * @param geometryType 几何类型
     */
    public FeatureSet(String geometryType) {
        this(geometryType, new ArrayList<Feature<Geometry, Attribute>>());
    }

}
