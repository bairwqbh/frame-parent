package per.cby.frame.common.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 默认值
 * 
 * @author chenboyang
 * @since 2019年11月7日
 *
 */
public interface DefaultValue {

	/** 空对象 */
	Object NULL = null;

    /** 空字符串 */
	String EMPTY_STRING = "";

    /** 空字符 */
	char EMPTY_CHAR = '\0';

    /** 零 */
	int ZERO = 0;

    /** 假 */
	boolean FLASE = false;

    /** 最早日期 */
	LocalDate FIRST_DATE = LocalDate.of(1970, 1, 1);

    /** 最早时间 */
	LocalDateTime FIRST_DATE_TIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);

}
