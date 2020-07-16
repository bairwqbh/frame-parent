package per.cby.frame.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * 抽象基础事件
 * 
 * @author chenboyang
 * @since 2019年5月16日
 *
 * @param <T> 业务类型
 */
@SuppressWarnings("unchecked")
public abstract class AbstractEvent<T> extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    /**
     * 构建事件
     * 
     * @param t 业务信息
     */
    public AbstractEvent(T t) {
        super(t);
    }

    @Override
    public T getSource() {
        return (T) super.getSource();
    }

}
