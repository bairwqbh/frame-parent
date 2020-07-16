package per.cby.frame.common.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * 条件判断帮助类
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class JudgeUtil {

    /**
     * 是否全部为真
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isAllTrue(boolean... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return false;
        }
        for (boolean object : objects) {
            if (object != true) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部为假
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isAllFalse(boolean... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (boolean object : objects) {
                if (object != false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否其中一个为真
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isOneTrue(boolean... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (boolean object : objects) {
                if (object == true) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否其中一个为假
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isOneFalse(boolean... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return true;
        }
        for (boolean object : objects) {
            if (object == false) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否全部相等
     * 
     * @param target  比对参数
     * @param objects 对象集
     * @return 结果
     */
    public <T> boolean isAllEqual(T target, T... objects) {
        if (target == null || ArrayUtils.isEmpty(objects)) {
            return false;
        }
        for (T object : objects) {
            if (!target.equals(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部不相等
     * 
     * @param target  比对参数
     * @param objects 对象集
     * @return 结果
     */
    @SafeVarargs
    public <T> boolean isAllNotEqual(T target, T... objects) {
        if (target != null && ArrayUtils.isNotEmpty(objects)) {
            for (T object : objects) {
                if (target.equals(object)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否其中一个相等
     * 
     * @param target  比对参数
     * @param objects 对象集
     * @return 结果
     */
    public <T> boolean isOneEqual(T target, T... objects) {
        if (target != null && ArrayUtils.isNotEmpty(objects)) {
            for (T object : objects) {
                if (target.equals(object)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否其中一个不相等
     * 
     * @param target  比对参数
     * @param objects 对象集
     * @return 结果
     */
    public <T, O> boolean isOneNotEqual(T target, T... objects) {
        if (target == null || ArrayUtils.isEmpty(objects)) {
            return true;
        }
        for (T object : objects) {
            if (!target.equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否全部非空
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public <O> boolean isAllNotNull(O... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return false;
        }
        for (O object : objects) {
            if (object == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部为空
     * 
     * @param objects 参数
     * @return 真假结果
     */
    public <O> boolean isAllNull(O... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (O object : objects) {
                if (object != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否其中一个非空
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public <O> boolean isOneNotNull(O... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (O object : objects) {
                if (object != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否其中一个为空
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public <O> boolean isOneNull(O... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return true;
        }
        for (O object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否全部非空白
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isAllNotBlank(String... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return false;
        }
        for (String object : objects) {
            if (StringUtils.isBlank(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部空白
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isAllBlank(String... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (String object : objects) {
                if (StringUtils.isNotBlank(object)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否其中一个非空白
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isOneNotBlank(String... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (String object : objects) {
                if (StringUtils.isNotBlank(object)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否其中一个空白
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public boolean isOneBlank(String... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return true;
        }
        for (String object : objects) {
            if (StringUtils.isBlank(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否全部非空集
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public <O> boolean isAllNotEmpty(O... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return false;
        }
        for (O object : objects) {
            if (object == null) {
                return false;
            } else if (object instanceof String && StringUtils.isEmpty((String) object)) {
                return false;
            } else if (object instanceof Object[] && ArrayUtils.isEmpty((Object[]) object)) {
                return false;
            } else if (object instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) object)) {
                return false;
            } else if (object instanceof Map<?, ?> && MapUtils.isEmpty((Map<?, ?>) object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部为空集
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public <O> boolean isAllEmpty(O... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (O object : objects) {
                if (object instanceof String && !StringUtils.isEmpty((String) object)) {
                    return false;
                } else if (object instanceof Object[] && ArrayUtils.isNotEmpty((Object[]) object)) {
                    return false;
                } else if (object instanceof Collection<?> && !CollectionUtils.isEmpty((Collection<?>) object)) {
                    return false;
                } else if (object instanceof Map<?, ?> && !MapUtils.isEmpty((Map<?, ?>) object)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否其中一个非空集
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public <O> boolean isOneNotEmpty(O... objects) {
        if (ArrayUtils.isNotEmpty(objects)) {
            for (O object : objects) {
                if (object instanceof String && !StringUtils.isEmpty((String) object)) {
                    return true;
                } else if (object instanceof Object[] && ArrayUtils.isNotEmpty((Object[]) object)) {
                    return true;
                } else if (object instanceof Collection<?> && !CollectionUtils.isEmpty((Collection<?>) object)) {
                    return true;
                } else if (object instanceof Map<?, ?> && !MapUtils.isEmpty((Map<?, ?>) object)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否其中一个为空集
     * 
     * @param objects 参数
     * @return 真假结果
     */
    @SafeVarargs
    public <O> boolean isOneEmpty(O... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return true;
        }
        if (ArrayUtils.isNotEmpty(objects)) {
            for (O object : objects) {
                if (object == null) {
                    return true;
                } else if (object instanceof String && StringUtils.isEmpty((String) object)) {
                    return true;
                } else if (object instanceof Object[] && ArrayUtils.isEmpty((Object[]) object)) {
                    return true;
                } else if (object instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) object)) {
                    return true;
                } else if (object instanceof Map<?, ?> && MapUtils.isEmpty((Map<?, ?>) object)) {
                    return true;
                }
            }
        }
        return false;
    }

}
