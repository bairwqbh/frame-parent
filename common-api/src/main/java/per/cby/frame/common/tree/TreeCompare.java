package per.cby.frame.common.tree;

import org.apache.commons.lang3.StringUtils;

import per.cby.frame.common.util.JudgeUtil;

import lombok.experimental.UtilityClass;

/**
 * 地区树比较通用类
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class TreeCompare {

    /** 国家代码后缀 */
    public final String NATIONAL_SUFFIX = "100000";

    /** 省级代码后缀 */
    public final String PROVINCE_SUFFIX = "0000";

    /** 市级代码后缀 */
    public final String CITY_SUFFIX = "00";

    /**
     * 当前代码是否从属于目标代码
     * 
     * @param fromId 当前代码
     * @param toId   目标代码
     * @return 是否从属
     */
    public boolean isBelong(String fromId, String toId) {
        return toId != null && (JudgeUtil.isOneEqual(toId, fromId, TreeableService.TOP_LEVEL, TreeableService.TOP_CODE)
                || toId.endsWith(NATIONAL_SUFFIX)
                || (toId.endsWith(PROVINCE_SUFFIX) && provinceCodeCompare(fromId, toId))
                || (toId.endsWith(CITY_SUFFIX) && cityCodeCompare(fromId, toId)));
    }

    /**
     * 比较省级代码
     * 
     * @param code0 代码0
     * @param code1 代码1
     * @return 是否匹配
     */
    public boolean provinceCodeCompare(String code0, String code1) {
        return getProvinceCode(code0).equals(getProvinceCode(code1));
    }

    /**
     * 比较市级代码
     * 
     * @param code0 代码0
     * @param code1 代码1
     * @return 是否匹配
     */
    public boolean cityCodeCompare(String code0, String code1) {
        return getCityCode(code0).equals(getCityCode(code1));
    }

    /**
     * 获取省级代码部分
     * 
     * @param code 代码
     * @return 省级代码部分
     */
    public String getProvinceCode(String code) {
        return getCodePrefix(code, 4);
    }

    /**
     * 获取市级代码部分
     * 
     * @param code 代码
     * @return 市级代码部分
     */
    public String getCityCode(String code) {
        return getCodePrefix(code, 2);
    }

    /**
     * 获取代码后缀
     * 
     * @param code   代码
     * @param offset 偏移量
     * @return 代码后缀
     */
    public String getCodePrefix(String code, int offset) {
        return code != null && code.length() > offset ? code.substring(0, code.length() - offset) : StringUtils.EMPTY;
    }

}
