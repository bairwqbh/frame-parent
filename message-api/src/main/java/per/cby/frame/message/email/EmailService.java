package per.cby.frame.message.email;

/**
 * 邮件发送服务
 * 
 * @author chenboyang
 * @since 2019年6月4日
 *
 */
public interface EmailService {

    /**
     * 发送邮件
     * 
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param to      收件地址
     */
    default void send(String subject, String content, String... to) {
        send(subject, content, to, null);
    }

    /**
     * 发送邮件
     * 
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param to      收件地址
     * @param cc      抄送地址
     */
    default void send(String subject, String content, String[] to, String[] cc) {
        send(subject, content, to, cc, null);
    }

    /**
     * 发送邮件
     * 
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param to      收件地址
     * @param cc      抄送地址
     * @param bcc     报送地址
     */
    void send(String subject, String content, String[] to, String[] cc, String[] bcc);

}
