package per.cby.frame.gis.arcgis.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import per.cby.frame.common.base.BaseService;
import per.cby.frame.common.util.ReflectUtil;
import per.cby.frame.gis.arcgis.annotation.FeatureType;
import per.cby.frame.gis.arcgis.model.Attribute;
import per.cby.frame.gis.arcgis.model.Feature;
import per.cby.frame.gis.arcgis.model.FeatureCollection;
import per.cby.frame.gis.arcgis.model.FeatureSet;
import per.cby.frame.gis.arcgis.model.Field;
import per.cby.frame.gis.arcgis.model.LayerDefinition;
import per.cby.frame.gis.geometry.Extent;
import per.cby.frame.gis.geometry.Geometry;
import per.cby.frame.gis.geometry.MultiPoint;
import per.cby.frame.gis.geometry.Point;
import per.cby.frame.gis.geometry.Polygon;
import per.cby.frame.gis.geometry.Polyline;
import per.cby.frame.gis.util.WKTUtil;

/**
 * 要素服务接口
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public interface FeatureService<G extends Geometry, A extends Attribute> extends BaseService<A> {

    /** 空间几何字段 */
    String SHAPE = "shape";

    /** 加载要素集标识 */
    String LOAD_FEATURES = "loadFeatures";

    /**
     * 获取日志记录器
     * 
     * @return 日志记录器
     */
    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }

    /**
     * 获取要素类型
     * 
     * @return 要素类型
     */
    default Class<? extends Geometry> geometryClass() {
        return this.getClass().getAnnotation(FeatureType.class).geometry();
    }

    /**
     * 获取属性类型
     * 
     * @return 属性类型
     */
    default Class<? extends Attribute> attributeClass() {
        return this.getClass().getAnnotation(FeatureType.class).attribute();
    }

    /**
     * 将要素数据转换为属性数据
     * 
     * @param feature 要素数据
     * @return 属性数据
     */
    default A valueAttribute(Feature<G, A> feature) {
        A attribute = null;
        try {
            attribute = feature.getAttributes() != null ? feature.getAttributes() : (A) attributeClass().newInstance();
            if (feature.getGeometry() != null && ReflectUtil.hasField(attribute.getClass(), SHAPE)) {
                BeanUtils.setProperty(attribute, SHAPE, WKTUtil.geometryToWkt(feature.getGeometry()));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger().error(e.getMessage(), e);
        }
        return attribute;
    }

    /**
     * 查询要素集合
     * 
     * @param param 查询条件
     * @return 要素集合
     */
    default List<Feature<G, A>> features(Map<String, Object> param) {
        List<Feature<G, A>> result = null;
        try {
            List<A> list = list(param);
            if (!CollectionUtils.isEmpty(list)) {
                result = new ArrayList<Feature<G, A>>();
                for (A attribute : list) {
                    Feature<G, A> feature = new Feature<G, A>();
                    String shape = BeanUtils.getProperty(attribute, SHAPE);
                    if (StringUtils.isNotBlank(shape)) {
                        feature.setGeometry((G) WKTUtil.wktToGeometry(shape));
                        attribute.setObjectid(list.indexOf(attribute));
                        BeanUtils.setProperty(attribute, SHAPE, null);
                    }
                    feature.setAttributes(attribute);
                    result.add(feature);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger().error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 保存要素
     * 
     * @param feature 要素
     * @return 操作记录数
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveFeature(Feature<G, A> feature) {
        return save(valueAttribute(feature));
    }

    /**
     * 更新要素
     * 
     * @param feature 要素
     * @return 操作记录数
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean updateFeature(Feature<G, A> feature) {
        return updateById(valueAttribute(feature));
    }

    /**
     * 封装图层信息
     * 
     * @param param 查询条件
     * @return 图层定义对象
     */
    default FeatureCollection layer(Map<String, Object> param) {
        String geometryType = null;
        if (geometryClass().equals(Point.class)) {
            geometryType = FeatureCollection.ESRI_GEOMETRY_POINT;
        } else if (geometryClass().equals(MultiPoint.class)) {
            geometryType = FeatureCollection.ESRI_GEOMETRY_MULTI_POINT;
        } else if (geometryClass().equals(Polyline.class)) {
            geometryType = FeatureCollection.ESRI_GEOMETRY_POLYLINE;
        } else if (geometryClass().equals(Polygon.class)) {
            geometryType = FeatureCollection.ESRI_GEOMETRY_POLYGON;
        } else if (geometryClass().equals(Extent.class)) {
            geometryType = FeatureCollection.ESRI_GEOMETRY_EXTENT;
        }
        LayerDefinition layerDefinition = new LayerDefinition(geometryType, Field.getFields(attributeClass()));
        FeatureSet featureSet = new FeatureSet(geometryType);
        if (param.get(LOAD_FEATURES) != null && Boolean.valueOf(param.get(LOAD_FEATURES).toString())) {
            featureSet.setFeatures((List) features(param));
        }
        return new FeatureCollection(layerDefinition, featureSet);
    }

}
