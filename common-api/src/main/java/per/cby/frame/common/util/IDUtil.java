package per.cby.frame.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

import lombok.experimental.UtilityClass;

/**
 * ID生成帮助类
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class IDUtil {

    /** 日期时间格式化 */
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    /**
     * 生成长整型唯一ID
     * 
     * @return 长整型ID
     */
    public long createLongId() {
        return Long.valueOf(String.valueOf(System.currentTimeMillis()) + nanoOfms());
    }

    /**
     * 根据当前时间生成唯一时间ID(精确到纳秒)
     * 
     * @return 当前时间ID
     */
    public String createUniqueTimeId() {
        return createTimeId() + nanoOfms();
    }

    /**
     * 获取当前毫秒的纳秒数
     * 
     * @return 纳秒
     */
    private String nanoOfms() {
        String behind = String.valueOf(System.nanoTime());
        return behind.substring(behind.length() - 7, behind.length() - 1);
    }

    /**
     * 根据当前时间生成ID(精确到毫秒)
     * 
     * @return 当前时间ID
     */
    public String createTimeId() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    /**
     * 生成UUID
     * 
     * @return UUID
     */
    public String createUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成32位UUID
     * 
     * @return UUID
     */
    public String createUUID32() {
        return createUUID().replaceAll("-", "");
    }

    /**
     * 生成32位大写UUID
     * 
     * @return UUID
     */
    public String createUUID32Upper() {
        return createUUID32().toUpperCase();
    }

    /**
     * 根据字符串生成UUID
     * 
     * @param str 字符串
     * @return UUID
     */
    public String createUUID(String str) {
        return UUID.nameUUIDFromBytes(str.getBytes()).toString();
    }

    /**
     * 根据字符串生成32位UUID
     * 
     * @param str 字符串
     * @return UUID
     */
    public String createUUID32(String str) {
        return createUUID(str).replaceAll("-", "");
    }

    /**
     * 根据字符串生成32位大写UUID
     * 
     * @param str 字符串
     * @return UUID
     */
    public String createUUID32Upper(String str) {
        return createUUID32(str).toUpperCase();
    }

    /**
     * 生成16位UUID
     * 
     * @return UUID
     */
    public String createUUID16() {
        return String.format("%d%015d", RandomUtils.nextInt(1, 10), Math.abs(createUUID().hashCode()));
    }

    /**
     * 根据字符串生成16位UUID
     * 
     * @param str 字符串
     * @return UUID
     */
    public String createUUID16(String str) {
        return String.format("%d%015d", RandomUtils.nextInt(1, 10), Math.abs(createUUID(str).hashCode()));
    }

}
