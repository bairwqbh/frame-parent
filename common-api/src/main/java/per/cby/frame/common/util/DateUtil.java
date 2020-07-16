package per.cby.frame.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 日期处理帮助类
 * 
 * @author chenboyang
 *
 */
@Slf4j
@UtilityClass
@SuppressWarnings("deprecation")
public class DateUtil extends DateUtils {

    /** 日期正则表达式 */
    public final String DATE_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

    /** 时间正则表达式 */
    public final String TIME_REGEX = "[0-9]{2}:[0-9]{2}:[0-9]{2}";

    /** 毫秒正则表达式 */
    public final String MILLISECOND_REGEX = "[0-9]{1,3}";

    /** 日期时间正则表达式 */
    public final String DATETIME_REGEX = DATE_REGEX + " " + TIME_REGEX;

    /** 时间戳正则表达式 */
    public final String TIMESTAMP_REGEX = DATETIME_REGEX + "." + MILLISECOND_REGEX;

    /** 星期正则表达式 */
    public final String WEEK_REGEX = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun)";

    /** 月份正则表达式 */
    public final String MONTH_REGEX = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";

    /** GMT正则表达式 */
    public final String GMT_REGEX = "[0-9]{2} " + MONTH_REGEX + " [0-9]{4} " + TIME_REGEX + " GMT";

    /** CTS正则表达式 */
    public final String CST_REGEX = WEEK_REGEX + " " + MONTH_REGEX + " [0-9]{2} " + TIME_REGEX + " CST [0-9]{4}";

    /** WEB-GMT正则表达式 */
    public final String WEB_GMT_REGEX = WEEK_REGEX + " " + MONTH_REGEX + " [0-9]{2} [0-9]{4} " + TIME_REGEX
            + " GMT\\+[0-9]{4} (.+?)";

    /** 日期字符串格式 */
    public final String DATE_FORMAT = "yyyy-MM-dd";

    /** 时间字符串格式 */
    public final String TIME_FORMAT = "HH:mm:ss";

    /** 日期时间字符串格式 */
    public final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    /** 时间戳字符串格式 */
    public final String TIMESTAMP_FORMAT = DATETIME_FORMAT + ".SSS";

    /** 东八时区 */
    public final String GMT_E_8 = "GMT+8";

    /** 东八时区 */
    public final ZoneOffset ZONE_E_8 = ZoneOffset.ofHours(8);

    /** 工作日调整器 */
    private volatile IntFunction<TemporalAdjuster> workDayAdjuster;

    /**
     * 获取工作日调整器
     * 
     * @return 工作日调整器
     */
    private IntFunction<TemporalAdjuster> workDayAdjuster() {
        return Optional.ofNullable(workDayAdjuster)
                .orElseGet(() -> workDayAdjuster = days -> TemporalAdjusters.ofDateAdjuster(d -> {
                    LocalDate baseDate = days > 0 ? d.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                            : days < 0 ? d.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)) : d;
                    int businessDays = days + Math.min(Math.max(baseDate.until(d).getDays(), -4), 4);
                    return baseDate.plusWeeks(businessDays / 5).plusDays(businessDays % 5);
                }));
    }

    /**
     * 调整工作日
     * 
     * @param date 日期
     * @param num  天数
     * @return 日期
     */
    public LocalDate adjustWorkDay(LocalDate date, int num) {
        return date.with(workDayAdjuster().apply(num));
    }

    /**
     * 调整工作日
     * 
     * @param datetime 日期时间
     * @param num      天数
     * @return 日期
     */
    public LocalDateTime adjustWorkDay(LocalDateTime datetime, int num) {
        return datetime.with(workDayAdjuster().apply(num));
    }

    /**
     * 格式化当前日期字符串
     * 
     * @return 日期字符串
     */
    public String formatCurDate() {
        return DateUtil.format(LocalDateTime.now(), DATETIME_FORMAT);
    }

    /**
     * 格式化日期默认字符串
     * 
     * @param date 日期
     * @return 日期字符串
     */
    public String formatDate(Date date) {
        return formatDate(date, DATETIME_FORMAT);
    }

    /**
     * 格式化日期字符串
     * 
     * @param date   日期
     * @param format 格式
     * @return 日期字符串
     */
    public String formatDate(Date date, String format) {
        return (new SimpleDateFormat(format)).format(date);
    }

    /**
     * 根据日期字符串获取日期格式
     * 
     * @param value 日期字符串
     * @return 日期格式
     */
    public String getFormat(String value) {
        String format = null;
        if (StringUtil.isMatche(value, DATE_REGEX)) {
            format = DATE_FORMAT;
        } else if (StringUtil.isMatche(value, TIME_REGEX)) {
            format = TIME_FORMAT;
        } else if (StringUtil.isMatche(value, DATETIME_REGEX)) {
            format = DATETIME_FORMAT;
        } else if (StringUtil.isMatche(value, TIMESTAMP_REGEX)) {
            format = TIMESTAMP_FORMAT;
        }
        return format;
    }

    /**
     * 增加日期
     * 
     * @param date     日期
     * @param dateType 增加日期类型
     * @param amount   增加数量
     * @return 增加后的日期
     */
    public Date plusDate(Date date, int dateType, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateType, amount);
        return calendar.getTime();
    }

    /**
     * 减少日期
     * 
     * @param date     日期
     * @param dateType 减少日期类型
     * @param amount   减少数量
     * @return 减少后的日期
     */
    public Date reduceDate(Date date, int dateType, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(dateType, calendar.get(dateType) - amount);
        return calendar.getTime();
    }

    /**
     * 设置日期
     * 
     * @param date     日期
     * @param dateType 日期单位类型
     * @param amount   单位数量
     * @return 设置后的日期
     */
    public Date setDate(Date date, int dateType, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(dateType, amount);
        return calendar.getTime();
    }

    /**
     * 根据日期字符串判断是否为国际标准格式
     * 
     * @param value 日期字符串
     * @return 是否为国际标准格式
     */
    public boolean isStandardFormat(String value) {
        if (StringUtils.isNotBlank(value)) {
            if (StringUtil.isMatche(value, GMT_REGEX)) {
                return true;
            } else if (StringUtil.isMatche(value, CST_REGEX)) {
                return true;
            } else if (StringUtil.isMatche(value, WEB_GMT_REGEX)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前时间数量
     * 
     * @param timeUnit 时间单位
     * @return 时间数量
     */
    public long currentTime(TimeUnit timeUnit) {
        return timeNum(System.currentTimeMillis(), timeUnit);
    }

    /**
     * 获取日期的时间数量
     * 
     * @param date     日期
     * @param timeUnit 时间单位
     * @return 时间数量
     */
    public long timeNum(Date date, TimeUnit timeUnit) {
        return timeNum(date.getTime(), timeUnit);
    }

    /**
     * 获取时间戳的时间数量
     * 
     * @param timeMillis 时间戳
     * @param timeUnit   时间单位
     * @return 时间数量
     */
    public long timeNum(long timeMillis, TimeUnit timeUnit) {
        double time = 0D;
        if (timeUnit == TimeUnit.NANOSECONDS) {
            time = timeMillis * 1000D * 1000;
        } else if (timeUnit == TimeUnit.MICROSECONDS) {
            time = timeMillis * 1000D;
        } else if (timeUnit == TimeUnit.SECONDS) {
            time = timeMillis / 1000D;
        } else if (timeUnit == TimeUnit.MINUTES) {
            time = timeMillis / 1000D / 60;
        } else if (timeUnit == TimeUnit.HOURS) {
            time = timeMillis / 1000D / 60 / 60;
        } else if (timeUnit == TimeUnit.DAYS) {
            time = timeMillis / 1000D / 60 / 60 / 24;
        } else {
            time = timeMillis;
        }
        return (long) time;
    }

    /**
     * 将时间字符串解析为默认日期对象（默认格式为：yyyy-MM-dd HH:mm:ss）
     * 
     * @param value 时间字符串
     * @return 日期对象
     */
    public Date parse(String value) {
        return parse(value, DateUtil.getFormat(value));
    }

    /**
     * 将时间字符串解析为日期对象
     * 
     * @param value  时间字符串
     * @param format 时间格式
     * @return 日期对象
     */
    public Date parse(String value, String format) {
        if (StringUtils.isNotBlank(value)) {
            try {
                if (DateUtil.isStandardFormat(value)) {
                    return new Date(value);
                }
                if (StringUtils.isNotBlank(format)) {
                    return (new SimpleDateFormat(format)).parse(value);
                }
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 获取日期的起点时间
     * 
     * @param date 日期
     * @return 起点日期
     */
    public Date dateFirst(Date date) {
        date = setHours(date, 0);
        date = setMinutes(date, 0);
        date = setSeconds(date, 0);
        return date;
    }

    /**
     * 获取日期的终点时间
     * 
     * @param date 日期
     * @return 终点日期
     */
    public Date dateLast(Date date) {
        date = setHours(date, 23);
        date = setMinutes(date, 59);
        date = setSeconds(date, 59);
        return date;
    }

    /**
     * 转换日期
     * 
     * @param localDateTime 日期时间
     * @return 日期
     */
    public Date atDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 转换日期
     * 
     * @param localDate 日期
     * @return 日期
     */
    public Date atDate(LocalDate localDate) {
        return atDate(localDate.atStartOfDay());
    }

    /**
     * 转换日期
     * 
     * @param localTime 时间
     * @return 日期
     */
    public Date atDate(LocalTime localTime) {
        return atDate(localTime.atDate(LocalDate.now()));
    }

    /**
     * 转换时区
     * 
     * @param date 日期
     * @return 时区
     */
    public ZonedDateTime atZone(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }

    /**
     * 转换本地日期时间
     * 
     * @param date 日期
     * @return 日期时间
     */
    public LocalDateTime atLocalDateTime(Date date) {
        return atZone(date).toLocalDateTime();
    }

    /**
     * 转换本地日期
     * 
     * @param date 日期
     * @return 日期
     */
    public LocalDate atLocalDate(Date date) {
        return atZone(date).toLocalDate();
    }

    /**
     * 转换本地时间
     * 
     * @param date 日期
     * @return 时间
     */
    public LocalTime atLocalTime(Date date) {
        return atZone(date).toLocalTime();
    }

    /**
     * 解析本地日期时间
     * 
     * @param text 文本
     * @return 日期时间
     */
    public LocalDateTime parseLocalDateTime(String text) {
        if (text.contains("T")) {
            text = text.replace("T", " ");
        }
        return parseLocalDateTime(text, DateUtil.getFormat(text));
    }

    /**
     * 解析本地日期时间
     * 
     * @param text    文本
     * @param pattern 格式
     * @return 日期时间
     */
    public LocalDateTime parseLocalDateTime(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析本地日期
     * 
     * @param text 文本
     * @return 日期
     */
    public LocalDate parseLocalDate(String text) {
        return parseLocalDate(text, DateUtil.getFormat(text));
    }

    /**
     * 解析本地日期
     * 
     * @param text    文本
     * @param pattern 格式
     * @return 日期
     */
    public LocalDate parseLocalDate(String text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析本地时间
     * 
     * @param text 文本
     * @return 时间
     */
    public LocalTime parseLocalTime(String text) {
        return parseLocalTime(text, DateUtil.getFormat(text));
    }

    /**
     * 解析本地时间
     * 
     * @param text    文本
     * @param pattern 格式
     * @return 时间
     */
    public LocalTime parseLocalTime(String text, String pattern) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化日期时间
     * 
     * @param localDateTime 日期时间
     * @return 日期时间字符串
     */
    public String format(LocalDateTime localDateTime) {
        return format(localDateTime, DATETIME_FORMAT);
    }

    /**
     * 格式化日期时间
     * 
     * @param localDateTime 日期时间
     * @param pattern       格式
     * @return 日期时间字符串
     */
    public String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化日期
     * 
     * @param localDate 日期
     * @param pattern   格式
     * @return 日期字符串
     */
    public String format(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化时间
     * 
     * @param localTime 时间
     * @param pattern   格式
     * @return 时间字符串
     */
    public String format(LocalTime localTime, String pattern) {
        return localTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 转为日期时间字符串
     * 
     * @param localDateTime 日期时间
     * @return 时间字符串
     */
    public String toLocalString(LocalDateTime localDateTime) {
        return format(localDateTime, DATETIME_FORMAT);
    }

    /**
     * 转为日期字符串
     * 
     * @param localDate 日期
     * @return 时间字符串
     */
    public String toLocalString(LocalDate localDate) {
        return format(localDate, DATE_FORMAT);
    }

    /**
     * 转为时间字符串
     * 
     * @param localTime 时间
     * @return 时间字符串
     */
    public String toLocalString(LocalTime localTime) {
        return format(localTime, TIME_FORMAT);
    }

    /**
     * 泛日期对象模糊适配本地日期时间
     * 
     * @param date 泛日期对象
     * @return 适配结果
     */
    public Object adapt(Object date) {
        if (!(date instanceof String)) {
            return date;
        }
        String str = date.toString();
        String format = getFormat(str);
        if (DATE_FORMAT.equals(format)) {
            return DateUtil.parseLocalDate(str, DATE_FORMAT);
        } else if (TIME_FORMAT.equals(format)) {
            return DateUtil.parseLocalTime(str, TIME_FORMAT);
        } else if (DATETIME_FORMAT.equals(format)) {
            return DateUtil.parseLocalDateTime(str, DATETIME_FORMAT);
        }
        return date;
    }

}
