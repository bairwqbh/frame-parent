package per.cby.frame.dubbo.listener;

import java.util.Set;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.InvokerListenerAdapter;
import per.cby.frame.dubbo.bean.ClassIdBean;
import per.cby.frame.dubbo.bean.DubboSpringBootStarterConstants;

/**
 * 消费者订阅监听器
 * 
 * @author chenboyang
 *
 */
@Activate
public class ConsumerSubscribeListener extends InvokerListenerAdapter {

    /** 接口信息集合 */
    public static final Set<ClassIdBean> SUBSCRIBEDINTERFACES_SET = new ConcurrentHashSet<ClassIdBean>();

    @Override
    public void referred(Invoker<?> invoker) throws RpcException {
        Class<?> interfaceClass = invoker.getInterface();
        URL url = invoker.getUrl();
        String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
        String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
        ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
        SUBSCRIBEDINTERFACES_SET.add(classIdBean);
    }

    @Override
    public void destroyed(Invoker<?> invoker) {
        Class<?> interfaceClass = invoker.getInterface();
        URL url = invoker.getUrl();
        String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
        String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
        ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
        SUBSCRIBEDINTERFACES_SET.remove(classIdBean);
    }

}
