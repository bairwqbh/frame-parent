package per.cby.frame.web.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 简单会话工厂
 * 
 * @author chenboyang
 */
public class SimpleSessionFactory implements SessionFactory {

    @Override
    public Session create(String bizId, String host, long timeout) {
        SimpleSession session = new SimpleSession();
        session.setBizId(bizId);
        session.setHost(host);
        session.setTimeout(timeout);
        session.setStopTime(LocalDateTime.now().plus(timeout, ChronoUnit.MILLIS));
        return session;
    }

}
