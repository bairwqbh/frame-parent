package per.cby.frame.common.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;

import lombok.experimental.UtilityClass;

/**
 * 数值处理帮助类
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
public class NumberUtil extends NumberUtils {

    /**
     * 小数位舍入方法
     * 
     * @param num   数值
     * @param scale 保留小数位
     * @return 计算后数值
     */
    public double scale(double num, int scale) {
        return scale(num, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 小数位舍入方法
     * 
     * @param num          数值
     * @param scale        保留小数位
     * @param roundingMode 舍入方式
     * @return 计算后数值
     */
    public double scale(double num, int scale, int roundingMode) {
        return new BigDecimal(num).setScale(scale, roundingMode).doubleValue();
    }

    /**
     * 小数位舍入方法
     * 
     * @param num   数值
     * @param scale 保留小数位
     * @return 计算后数值
     */
    public float scale(float num, int scale) {
        return scale(num, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 小数位舍入方法
     * 
     * @param num          数值
     * @param scale        保留小数位
     * @param roundingMode 舍入方式
     * @return 计算后数值
     */
    public float scale(float num, int scale, int roundingMode) {
        return new BigDecimal(num).setScale(scale, roundingMode).floatValue();
    }

}
