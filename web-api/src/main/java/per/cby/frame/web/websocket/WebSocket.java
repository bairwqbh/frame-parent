package per.cby.frame.web.websocket;

import javax.websocket.Session;

/**
 * WebSocket服务端处理接口
 * 
 * @author chenboyang
 *
 */
public interface WebSocket {

    /**
     * 有会话连接成功
     * 
     * @param session 会话
     * @param bizId  业务编号
     */
    void onOpen(Session session, String bizId);

    /**
     * 会话连接关闭
     */
    void onClose();

    /**
     * 收到客户端消息
     * 
     * @param message 客户端发送过来的消息
     * @param session 会话
     */
    void onMessage(String message, Session session);

    /**
     * 发生错误
     * 
     * @param session 客户端
     * @param error   错误信息
     */
    void onError(Session session, Throwable error);

    /**
     * 发送消息到当前客户端
     * 
     * @param message 消息
     */
    void sendMessage(Object message);

    /**
     * 根据业务标识发送消息到当前客户端
     * 
     * @param bizId  业务标识
     * @param message 消息
     */
    void sendMessage(String bizId, Object message);

    /**
     * 发送消息到所有客户端
     * 
     * @param message 消息
     */
    void sendMessageAll(Object message);

    /**
     * 业务关联比较
     * 
     * @param inBusiId 传入外部业务标识
     * @return 是否关联
     */
    boolean bizCompare(String inBusiId);

}
