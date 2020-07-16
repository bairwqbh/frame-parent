package per.cby.frame.gis.arcgis.model;

import per.cby.frame.gis.geometry.Geometry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 要素
 * 
 * @author chenboyang
 *
 * @param <G> 几何数据
 * @param <A> 业务数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Feature<G extends Geometry, A extends Attribute> {

    /** 空间数据 */
    private G geometry;

    /** 业务数据 */
    private A attributes;

}
