package per.cby.frame.gis.arcgis.model;

import java.util.ArrayList;
import java.util.List;

import per.cby.frame.common.util.ReflectUtil;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 业务数据字段类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
public class Field {

    /** 名称 */
    private String name;

    /** 类型 */
    private String type;

    /** 别名 */
    private String alias;

    /** 长度 */
    private int length;

    /** 是否编辑 */
    private boolean editable;

    /** 是否为空 */
    private boolean nullable;

    /** 领域范围 */
    private Domain domain;

    /**
     * 构造函数
     * 
     * @param name 名称
     */
    public Field(String name) {
        this(name, null);
    }

    /**
     * 构造函数
     * 
     * @param name 名称
     * @param type 类型
     */
    public Field(String name, String type) {
        this(name, type, null);
    }

    /**
     * 构造函数
     * 
     * @param name  名称
     * @param type  类型
     * @param alias 别名
     */
    public Field(String name, String type, String alias) {
        this.name = name;
        this.type = type;
        this.alias = alias;
    }

    /**
     * 根据类获取字段集
     * 
     * @param clazz 类
     * @return 字段集
     */
    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        List<java.lang.reflect.Field> fieldList = ReflectUtil.getFields(clazz);
        for (java.lang.reflect.Field field : fieldList) {
            fields.add(new Field(field.getName()));
        }
        return fields;
    }

}
