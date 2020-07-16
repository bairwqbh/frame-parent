package per.cby.frame.gis.arcgis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 要素集合类
 * 
 * @author chenboyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FeatureCollection {

    /** ESRI几何点 */
    public static final String ESRI_GEOMETRY_POINT = "esriGeometryPoint";

    /** ESRI几何点簇 */
    public static final String ESRI_GEOMETRY_MULTI_POINT = "esriGeometryMultiPoint";

    /** ESRI几何线 */
    public static final String ESRI_GEOMETRY_POLYLINE = "esriGeometryPolyline";

    /** ESRI几何面 */
    public static final String ESRI_GEOMETRY_POLYGON = "esriGeometryPolygon";

    /** ESRI几何范围 */
    public static final String ESRI_GEOMETRY_EXTENT = "esriGeometryExtent";

    /** 图层定义 */
    private LayerDefinition layerDefinition;

    /** 要素集 */
    private FeatureSet featureSet;

}
