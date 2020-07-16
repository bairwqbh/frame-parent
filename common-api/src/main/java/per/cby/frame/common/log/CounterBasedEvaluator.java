package per.cby.frame.common.log;

import java.util.Date;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluatorBase;
import lombok.Setter;

/**
 * 用于判断异常邮件是否发送配置于appender中异步不用考虑并发争用
 * 
 * @author chenboyang
 *
 */
public class CounterBasedEvaluator extends EventEvaluatorBase<ILoggingEvent> {

    /** 默认10个error发一次邮件 */
    @Setter
    private int limit = 10;

    /** 默认间隔10分钟 */
    @Setter
    private Long interval = 1000L * 60 * 10;

    /** 当前积攒了多少error */
    private int count = 0;

    /** 最后一次发送的时间 */
    private Date lastSendTime = new Date();

    @Override
    public boolean evaluate(ILoggingEvent event) throws EvaluationException {
        count++;
        if (count >= limit || ((System.currentTimeMillis() - lastSendTime.getTime()) > interval)) {
            count = 0;
            lastSendTime = new Date();
            return true;
        }
        return false;
    }

}
