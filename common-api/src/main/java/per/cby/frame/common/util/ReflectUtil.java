package per.cby.frame.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.CollectionFactory;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * JAVA反射帮助类
 * 
 * @author chenboyang
 * 
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class ReflectUtil {

    /**
     * 获取类的字段集
     * 
     * @param clazz 类
     * @return 字段集
     */
    public List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * 获取类的字段
     * 
     * @param clazz     类
     * @param fieldName 字段名
     * @return 字段
     */
    public Field getField(Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException | SecurityException e) {
            }
        }
        return null;
    }

    /**
     * 获取对象的字段值
     * 
     * @param obj       对象
     * @param fieldName 字段名
     * @return 字段值
     */
    public Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = getField(obj.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(obj);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
        }
        return null;
    }

    /**
     * 设置对象的字段值
     * 
     * @param obj        对象
     * @param fieldName  字段名
     * @param fieldValue 字段值
     */
    public void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        try {
            Field field = getField(obj.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
        }
    }

    /**
     * 获取类的方法
     * 
     * @param clazz          类
     * @param methodName     方法名
     * @param parameterTypes 参数类型集
     * @return 方法
     */
    public Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException | SecurityException e) {
            }
        }
        return null;
    }

    /**
     * 获取类的方法
     * 
     * @param clazz      类
     * @param methodName 方法名
     * @return 方法
     */
    public Method getMethod(Class<?> clazz, String methodName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return getMethod(clazz.getDeclaredMethods(), methodName);
            } catch (SecurityException e) {
            }
        }
        return null;
    }

    /**
     * 调用对象的方法
     * 
     * @param obj        对象
     * @param methodName 方法名
     * @param args       参数集
     * @return 方法调用的返回结果
     */
    public Object invokeMethod(Object obj, String methodName, Object... args) {
        try {
            Method method = getMethod(obj.getClass(), methodName, parameterClass(args));
            if (method != null) {
                method.setAccessible(true);
                return method.invoke(obj, args);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 参数类型封装
     * 
     * @param args 参数
     * @return 参数类集
     */
    public Class<?>[] parameterClass(Object... args) {
        Class<?>[] parameterClass = new Class<?>[args.length];
        for (int i = 0; i < parameterClass.length; i++) {
            parameterClass[i] = args[i].getClass();
        }
        return parameterClass;
    }

    /**
     * 判断是否为包装类
     * 
     * @param clazz 类
     * @return 否为包装类
     */
    public boolean isWrapClass(Class<?> clazz) {
        try {
            Object type = clazz.getDeclaredField("TYPE").get(null);
            if (type instanceof Class) {
                return ((Class<?>) type).isPrimitive();
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
        }
        return false;
    }

    /**
     * 根据方法名获取方法
     * 
     * @param methods 方法集
     * @param name    方法名
     * @return 方法
     */
    public Method getMethod(Method[] methods, String name) {
        if (methods.length > 0 && StringUtils.isNotBlank(name)) {
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 类型中是否包含该方法
     * 
     * @param clazz      类型
     * @param methodName 方法名
     * @return 是否包含
     */
    public boolean hasMethod(Class<?> clazz, String methodName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.getName().equals(methodName)) {
                        return true;
                    }
                }
            } catch (SecurityException e) {
            }
        }
        return false;
    }

    /**
     * 类型中是否包含该方法
     * 
     * @param clazz  类型
     * @param method 方法
     * @return 是否包含
     */
    public boolean hasMethod(Class<?> clazz, Method method) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                for (Method method1 : clazz.getDeclaredMethods()) {
                    if (method1.equals(method)) {
                        return true;
                    }
                }
            } catch (SecurityException e) {
            }
        }
        return false;
    }

    /**
     * 类型中是否包含该字段
     * 
     * @param clazz     类型
     * @param fieldName 字段名
     * @return 是否包含
     */
    public boolean hasField(Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getName().equals(fieldName)) {
                        return true;
                    }
                }
            } catch (SecurityException e) {
            }
        }
        return false;
    }

    /**
     * 类型中是否包含该字段
     * 
     * @param clazz 类型
     * @param field 字段
     * @return 是否包含
     */
    public boolean hasField(Class<?> clazz, Field field) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                for (Field field1 : clazz.getDeclaredFields()) {
                    if (field1.equals(field)) {
                        return true;
                    }
                }
            } catch (SecurityException e) {
            }
        }
        return false;
    }

    /**
     * 调度对象的的设置方法
     * 
     * @param obj        对象
     * @param methodName 方法名
     * @param args       参数
     * @return 设置结果
     */
    public Object invokeSetMethod(Object obj, String methodName, Object args) {
        try {
            Method method = getMethod(obj.getClass(), methodName);
            if (method != null) {
                method.setAccessible(true);
                args = cast(method.getParameterTypes()[0], args);
                return method.invoke(obj, args);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 值类型转换
     * 
     * @param type  类型
     * @param value 值
     * @return 转换结果
     */
    public Object cast(Class<?> type, Object value) {
        if (JudgeUtil.isAllNotNull(type, value) && !type.equals(value.getClass())) {
            String str = value.toString();
            if (JudgeUtil.isOneEqual(type, byte.class, Byte.class)) {
                return Byte.valueOf(str);
            } else if (JudgeUtil.isOneEqual(type, short.class, Short.class)) {
                return Short.valueOf(str);
            } else if (JudgeUtil.isOneEqual(type, int.class, Integer.class)) {
                return Integer.valueOf(str);
            } else if (JudgeUtil.isOneEqual(type, long.class, Long.class)) {
                return Long.valueOf(str);
            } else if (JudgeUtil.isOneEqual(type, float.class, Float.class)) {
                return Float.valueOf(str);
            } else if (JudgeUtil.isOneEqual(type, double.class, Double.class)) {
                return Double.valueOf(str);
            } else if (JudgeUtil.isOneEqual(type, char.class, Character.class)) {
                return str.charAt(0);
            } else if (JudgeUtil.isOneEqual(type, boolean.class, Boolean.class)) {
                return Boolean.valueOf(str);
            } else if (Date.class.equals(type)) {
                return DateUtil.parse(str);
            } else if (LocalDateTime.class.equals(type)) {
                return DateUtil.parseLocalDateTime(str);
            } else if (LocalDate.class.equals(type)) {
                return DateUtil.parseLocalDate(str);
            } else if (LocalTime.class.equals(type)) {
                return DateUtil.parseLocalTime(str);
            } else {
                return type.cast(value);
            }
        }
        return value;
    }

    /**
     * 根据类型创建对象
     * 
     * @param type 对象类型
     * @return 对象
     */
    public Object createObject(Class<?> type) {
        try {
            if (type.isArray()) {
                return createArray(type);
            } else if (Collection.class.isAssignableFrom(type)) {
                return CollectionFactory.createCollection(type, 16);
            } else if (Map.class.isAssignableFrom(type)) {
                return CollectionFactory.createMap(type, 16);
            }
            return type.newInstance();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 递归创建多维数组
     * 
     * @param type 数组类型
     * @return 数组实例
     */
    public Object createArray(Class<?> type) {
        Class<?> componentType = type.getComponentType();
        if (componentType.isArray()) {
            Object array = Array.newInstance(componentType, 1);
            Array.set(array, 0, createArray(componentType));
            return array;
        }
        return Array.newInstance(componentType, 0);
    }

    /**
     * 获取原始类型
     * 
     * @param type 对象类型
     * @return 类型
     */
    public Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            }
        }
        return Object.class;
    }

    /**
     * 获取数组元素
     * 
     * @param array 数组
     * @param key   下标
     * @return 元素
     */
    public Object getArrayElement(Object[] array, Object key) {
        int index = Integer.valueOf(key.toString());
        if (array.length > index) {
            return array[index];
        }
        return null;
    }

    /**
     * 获取属性类型
     * 
     * @param type 对象类型
     * @param key  参数名
     * @return 类型
     */
    public Type getPropertyType(Type type, Object key) {
        Class<?> clazz = getClass(type);
        if (clazz.isArray()) {
            return clazz.getComponentType();
        } else if (Map.class.isAssignableFrom(clazz)) {
            if (type instanceof ParameterizedType) {
                return ((ParameterizedType) type).getActualTypeArguments()[1];
            }
        } else if (Collection.class.isAssignableFrom(clazz)) {
            if (type instanceof ParameterizedType) {
                return ((ParameterizedType) type).getActualTypeArguments()[0];
            }
        } else if (hasField(clazz, (String) key)) {
            return BeanUtils.getPropertyDescriptor(clazz, (String) key).getReadMethod().getGenericReturnType();
        }
        return Object.class;
    }

    /**
     * 获取属性值
     * 
     * @param object 对象
     * @param key    参数名
     * @return 值对象
     */
    public Object getPropertyValue(Object object, Object key) {
        try {
            if (object.getClass().isArray()) {
                return getArrayElement((Object[]) object, key);
            } else if (object instanceof Map) {
                return ((Map<?, ?>) object).get(key);
            } else if (object instanceof Collection) {
                return getArrayElement(((Collection<?>) object).toArray(), key);
            } else if (hasField(object.getClass(), (String) key)) {
                return BeanUtils.getPropertyDescriptor(object.getClass(), (String) key).getReadMethod().invoke(object);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 设置对象属性值
     * 
     * @param object 对象
     * @param key    参数名
     * @param value  参数值
     * @return 对象
     */
    public Object setPropertyValue(Object object, Object key, Object value) {
        try {
            if (object.getClass().isArray()) {
                Object[] array = (Object[]) object;
                int index = Integer.valueOf(key.toString());
                if (array.length > index) {
                    array[index] = value;
                } else {
                    array = BaseUtil.setArray(array, index, value);
                }
                object = array;
            } else if (object instanceof Map) {
                ((Map<Object, Object>) object).put(key, value);
            } else if (object instanceof List) {
                BaseUtil.setList((List<Object>) object, Integer.valueOf(key.toString()), value);
            } else if (hasField(object.getClass(), (String) key)) {
                Method method = BeanUtils.getPropertyDescriptor(object.getClass(), (String) key).getWriteMethod();
                if (method != null) {
                    method.invoke(object, cast(method.getParameterTypes()[0], value));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return object;
    }

    /**
     * 对象属性拷贝（拷贝操作为浅拷贝，默认不拷贝空值）
     * 
     * @param source 源对象
     * @param target 目标对象
     */
    public void copy(Object source, Object target) {
        copy(source, target, false);
    }

    /**
     * 对象属性拷贝（拷贝操作为浅拷贝）
     * 
     * @param source     源对象
     * @param target     目标对象
     * @param isCopyNull 空值是否拷贝
     */
    public void copy(Object source, Object target, boolean isCopyNull) {
        if (source == null || target == null) {
            return;
        }
        List<Field> list = ReflectUtil.getFields(source.getClass());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(field -> {
            String name = field.getName();
            Object value = ReflectUtil.getFieldValue(source, name);
            if (!isCopyNull && value == null) {
                return;
            }
            ReflectUtil.setFieldValue(target, name, value);
        });
    }

    /**
     * 获取类的泛型类型信息
     * 
     * @param clazz 类
     * @return 类型信息
     */
    public Class<?> getParameterizedType(Class<?> clazz) {
        return getParameterizedType(clazz, 0);
    }

    /**
     * 获取类的泛型类型信息
     * 
     * @param clazz 类
     * @param index 类型参数位置
     * @return 类型信息
     */
    public Class<?> getParameterizedType(Class<?> clazz, int index) {
        Type type = clazz.getGenericSuperclass();
        for (; !(type instanceof ParameterizedType); type = ((Class<?>) type).getGenericSuperclass()) {
            if (Object.class.equals(type)) {
                return null;
            }
        }
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        if (ArrayUtils.isEmpty(types)) {
            return null;
        }
//      if (index < 0 || index >= types.length) {
//          throw new IllegalArgumentException("类型参数位置超出范围！");
//      }
        if (index < 0) {
            index = 0;
        }
        if (index >= types.length) {
            index = types.length - 1;
        }
        type = types[index];
        for (; type instanceof ParameterizedType; type = ((ParameterizedType) type).getRawType()) {
        }
        if (!(type instanceof Class)) {
            return null;
        }
        return (Class<?>) type;
    }

    /**
     * 获取基础类型默认值
     * 
     * @param type 类型
     * @return 默认值
     */
    public Object getDefaultValue(Class<?> type) {
        if (byte.class.equals(type)) {
            return (byte) 0;
        } else if (short.class.equals(type)) {
            return (short) 0;
        } else if (int.class.equals(type)) {
            return 0;
        } else if (long.class.equals(type)) {
            return 0L;
        } else if (float.class.equals(type)) {
            return 0F;
        } else if (double.class.equals(type)) {
            return 0D;
        } else if (boolean.class.equals(type)) {
            return false;
        } else if (char.class.equals(type)) {
            return '\0';
        }
        return null;
    }

}
