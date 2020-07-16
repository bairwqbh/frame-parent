package per.cby.frame.web.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import org.apache.commons.collections4.CollectionUtils;

import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.JudgeUtil;
import per.cby.frame.common.util.SystemUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket基础功能抽象类
 * 
 * @author chenboyang
 *
 */
@Slf4j
public abstract class AbstractWebSocket implements WebSocket {

    /** 当前连接会话 */
    protected Session session;

    /** 当前会话业务编号 */
    protected String bizId;

    /**
     * 获取WebSocket连接集合
     * 
     * @return 连接集合
     */
    protected Set<WebSocket> webSocketSet() {
        return SystemUtil.systemStorage().getOrSet(getClass().getName(), CopyOnWriteArraySet::new);
    }

    /**
     * 获取在线客户记录数
     */
    public int getOnlineCount() {
        return webSocketSet().size();
    }

    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam("bizId") String bizId) {
        this.session = session;
        this.bizId = bizId;
        webSocketSet().add(this);
    }

    @OnClose
    @Override
    public void onClose() {
        webSocketSet().remove(this);
    }

    @Override
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(message);
    }

    @OnError
    @Override
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage(), error);
    }

    @Override
    public void sendMessage(Object message) {
        try {
            if (session.isOpen()) {
                if (!(message instanceof String)) {
                    message = JsonUtil.toJson(message);
                }
                String text = message.toString();
                session.getBasicRemote().sendText(text);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendMessage(String bizId, Object message) {
        if (CollectionUtils.isNotEmpty(webSocketSet())) {
            webSocketSet().stream().filter(ws -> ws.bizCompare(bizId)).forEach(ws -> ws.sendMessage(message));
        }
    }

    @Override
    public void sendMessageAll(Object message) {
        if (CollectionUtils.isNotEmpty(webSocketSet())) {
            webSocketSet().forEach(ws -> ws.sendMessage(message));
        }
    }

    @Override
    public boolean bizCompare(String inBizId) {
        return JudgeUtil.isAllEqual(inBizId, bizId);
    }

}
