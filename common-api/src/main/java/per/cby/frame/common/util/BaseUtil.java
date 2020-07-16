package per.cby.frame.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.constant.DefaultValue;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 基本帮助类
 * 
 * @author chenboyang
 * 
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class BaseUtil {

    /**
     * MD5加密返回16进制字符串
     * 
     * @param str 字符串
     * @return MD5代码
     */
    public String md5Encode(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * MD5加密返回16进制大写字符串
     * 
     * @param str 字符串
     * @return MD5代码
     */
    public String md5EncodeUpper(String str) {
        return md5Encode(str).toUpperCase();
    }

    /**
     * Base64加密
     * 
     * @param data 数据
     * @return 数据
     */
    public byte[] base64Encode(byte[] data) {
        return Base64.encodeBase64(data);
    }

    /**
     * Base64解密
     * 
     * @param data 数据
     * @return 数据
     */
    public byte[] base64Decode(byte[] data) {
        return Base64.decodeBase64(data);
    }

    /**
     * Base64加密
     * 
     * @param data 数据
     * @return 数据
     */
    public String base64Encode(String data) {
        return new String(base64Encode(data.getBytes()));
    }

    /**
     * Base64解密
     * 
     * @param data 数据
     * @return 数据
     */
    public String base64Decode(String data) {
        return new String(base64Decode(data.getBytes()));
    }

    /**
     * 是否为Base64字符串
     * 
     * @param data 数据
     * @return 判断结果
     */
    public boolean isBase64(String data) {
        int length = data.length();
        return length >= 4 && length % 4 == 0 && Base64.isBase64(data);
    }

    /**
     * 创建MAP容器
     * 
     * @return MAP容器
     */
    public <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * 创建MAP容器
     * 
     * @param capacity 容量
     * @return MAP容器
     */
    public <K, V> HashMap<K, V> newHashMap(int capacity) {
        return new HashMap<K, V>(capacity);
    }

    /**
     * 创建MAP容器及设置初始键值
     * 
     * @param key   键
     * @param value 值
     * @return MAP容器
     */
    public <K, V> HashMap<K, V> newHashMap(K key, V value) {
        HashMap<K, V> map = newHashMap();
        map.put(key, value);
        return map;
    }

    /**
     * 新建ArrayList
     * 
     * @return ArrayList
     */
    public <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    /**
     * 新建ArrayList
     * 
     * @param capacity 容量
     * @return ArrayList
     */
    public <E> ArrayList<E> newArrayList(int capacity) {
        return new ArrayList<E>(capacity);
    }

    /**
     * 根据元素集创建ArrayList
     * 
     * @param e 元素集
     * @return ArrayList
     */
    public <E> ArrayList<E> newArrayList(E... e) {
        ArrayList<E> list = newArrayList();
        for (E e2 : e) {
            list.add(e2);
        }
        return list;
    }

    /**
     * 将JAVA对象转为MAP对象
     * 
     * @param obj JAVA对象
     * @return MAP对象
     */
    public <K, V> Map<K, V> beanToMap(Object obj) {
        Map<K, V> map = newHashMap();
        List<Field> fields = ReflectUtil.getFields(obj.getClass());
        if (CollectionUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                String key = field.getName();
                Object value = ReflectUtil.getFieldValue(obj, key);
                map.put((K) key, (V) value);
            }
        }
        return map;
    }

    /**
     * 将MAP对象转为JAVA对象
     * 
     * @param map   MAP对象
     * @param clazz JAVA类型
     * @return JAVA对象
     */
    public <T> T mapToBean(Map<?, ?> map, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            if (MapUtils.isNotEmpty(map)) {
                Set<?> keySet = map.keySet();
                for (Object key : keySet) {
                    String methodName = "set" + key.toString().substring(0, 1).toUpperCase()
                            + key.toString().substring(1);
                    if (ReflectUtil.hasMethod(clazz, methodName)) {
                        ReflectUtil.invokeSetMethod(t, methodName, map.get(key));
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return t;
    }

    /**
     * 获取properties文件对象
     * 
     * @param filePath 文件路径
     * @return Properties对象
     */
    @Deprecated
    public Properties getProperties(String filePath) {
        Properties properties = new Properties();
        if (StringUtils.isNotBlank(filePath)) {
            InputStream input = null;
            try {
                input = BaseUtil.class.getClassLoader().getResourceAsStream(filePath);
                if (input != null) {
                    properties.load(input);
                } else {
                    log.warn(filePath + "文件输入流为空！");
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return properties;
    }

    /**
     * 对象类型转换
     * 
     * @param value 需要转换的对象
     * @param clazz 转换的目标类型
     * @return 转换后的对象
     */
    public <T> T convert(Object value, Class<T> clazz) {
        return (T) ConvertUtils.convert(value, clazz);
    }

    /**
     * 设置列表元素
     * 
     * @param list    列表
     * @param index   元素下标
     * @param element 元素
     * @return 列表
     */
    public <T> List<T> setList(List<T> list, int index, T element) {
        collectionFillByIndex(list, index);
        list.set(index, element);
        return list;
    }

    /**
     * 设置数组元素
     * 
     * @param array   数组
     * @param index   元素下标
     * @param element 元素
     * @return 数组
     */
    public <T> T[] setArray(T[] array, int index, T element) {
        array = arrayExtendByIndex(array, index);
        array[index] = element;
        return array;
    }

    /**
     * 数组去重
     * 
     * @param array 数组
     * @return 去重后的数组
     */
    public <T> T[] derepeat(T... array) {
        return toArray(new TreeSet<T>(newArrayList(array)));
    }

    /**
     * 将字符串转基础类型
     * 
     * @param value 字符串
     * @param clazz 基础类型
     * @return 基础类型值
     */
    public <T> T castByType(Object obj, Class<T> clazz) {
        String value = obj.toString();
        Object result = null;
        if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
            result = StringUtil.isNumeric(value) ? Byte.valueOf(value) : 0;
        } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
            result = StringUtil.isNumeric(value) ? Short.valueOf(value) : 0;
        } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            result = StringUtil.isNumeric(value) ? Integer.valueOf(value) : 0;
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            result = StringUtil.isNumeric(value) ? Long.valueOf(value) : 0;
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            result = StringUtil.isNumeric(value) ? Float.valueOf(value) : 0;
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            result = StringUtil.isNumeric(value) ? Double.valueOf(value) : 0;
        } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            result = "true".equals(value) || "1".equals(value);
        } else if (clazz.equals(char.class) || clazz.equals(Character.class)) {
            result = StringUtils.isNotEmpty(value) ? value.charAt(0) : DefaultValue.EMPTY_CHAR;
        }
        return (T) result;
    }

    /**
     * 如果下标超出集合长度就进行集合填充
     * 
     * @param collection 集合
     * @param index      下标
     */
    public <T> void collectionFillByIndex(Collection<T> collection, int index) {
        if (collection != null && collection.size() <= index) {
            collectionFill(collection, index - collection.size() + 1);
        }
    }

    /**
     * 集合填充
     * 
     * @param collection 集合
     * @param length     填充数量
     */
    public <T> void collectionFill(Collection<T> collection, int num) {
        if (collection != null && num > 0) {
            for (int i = 0; i < num; i++) {
                collection.add(null);
            }
        }
    }

    /**
     * 根据下标扩展数组容量
     * 
     * @param array 数组
     * @param index 下标
     * @return 扩展后的数组
     */
    public <T> T[] arrayExtendByIndex(T[] array, int index) {
        if (array != null && array.length <= index) {
            array = arrayExtend(array, index - array.length + 1);
        }
        return array;
    }

    /**
     * 扩展数组容量
     * 
     * @param array 数组
     * @param num   扩展数组单元数量
     * @return 扩展后的数组
     */
    public <T> T[] arrayExtend(T[] array, int num) {
        if (array != null && num > 0) {
            for (int i = 0; i < num; i++) {
                array = ArrayUtils.add(array, null);
            }
        }
        return array;
    }

    /**
     * 集合转数组
     * 
     * @param collection 集合
     * @return 数组
     */
    public <T> T[] toArray(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }
        T t = collection.stream().findFirst().orElse(null);
        if (t == null) {
            return null;
        }
        return collection.toArray((T[]) Array.newInstance(t.getClass(), collection.size()));
    }

    /**
     * 根据条件进行集合数据去重
     * 
     * @param <E>       元素类型
     * @param iterable  集合数据
     * @param predicate 条件
     */
    public <E> Collection<E> derepeat(Collection<E> collection, Predicate<E> predicate) {
        if (CollectionUtils.isNotEmpty(collection) && predicate != null) {
            Iterator<E> iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (predicate.test(iterator.next())) {
                    iterator.remove();
                }
            }
        }
        return collection;
    }

    /**
     * 根据元素属性进行集合数据去重
     * 
     * @param <E>   元素类型
     * @param list  列表
     * @param props 去重根据的属性
     * @return 列表
     */
    public <E> Collection<E> derepeat(Collection<E> collection, String... props) {
        if (CollectionUtils.isEmpty(collection)) {
            return collection;
        }
        if (ArrayUtils.isEmpty(props)) {
            return collection.stream().distinct().collect(Collectors.toList());
        }
        Set<String> set = new HashSet<String>();
        Iterator<E> iterator = collection.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            E e = iterator.next();
            sb.delete(0, sb.length());
            for (int i = 0; i < props.length; i++) {
                sb.append(String.valueOf(ReflectUtil.getFieldValue(e, props[i])));
            }
            if (set.contains(sb.toString())) {
                iterator.remove();
            } else {
                set.add(sb.toString());
            }
        }
        return collection;
    }

    /**
     * 根据条件过滤去除Map数据
     * 
     * @param <K>       键类型
     * @param <V>       值类型
     * @param map       Map容器
     * @param predicate 条件
     */
    public <K, V> Map<K, V> derepeat(Map<K, V> map, BiPredicate<K, V> predicate) {
        if (MapUtils.isNotEmpty(map) && predicate != null) {
            Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (predicate.test(entry.getKey(), entry.getValue())) {
                    iterator.remove();
                }
            }
        }
        return map;
    }

    /**
     * 对象转字符串，如果为空就转空字符串
     * 
     * @param <T>    对象类型
     * @param t      对象
     * @param mapper 转换器
     * @return 字符串
     */
    public <T> String toStringOrEmpty(T t, Function<T, String> mapper) {
        return Optional.ofNullable(t).map(mapper).orElse("");
    }

    /**
     * 获取本机IP地址
     * 
     * @return IP地址
     */
    public String getIp() {
        String ip = null;
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                Enumeration<InetAddress> ias = nis.nextElement().getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress ia = ias.nextElement();
                    if (!ia.isLoopbackAddress() && !ia.getHostAddress().contains(":")) {
                        ip = ia.getHostAddress();
                        if (!ia.isSiteLocalAddress()) {
                            return ip;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.error(e.getMessage(), e);
        }
        return ip;
    }

}
