package per.cby.frame.common.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 基础事件发布器
 * 
 * @author chenboyang
 * @since 2019年5月16日
 *
 */
@Lazy
@Component("__EVENT_PUBLISHER__")
public class EventPublisher implements ApplicationEventPublisherAware {

    /** 事件发布器 */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 发布事件
     * 
     * @param event 事件
     */
    public void publish(ApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

}
