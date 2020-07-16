package per.cby.frame.dubbo.context.event;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

import com.alibaba.dubbo.common.Version;
import com.alibaba.dubbo.qos.server.DubboLogo;
import per.cby.frame.dubbo.bean.DubboSpringBootStarterConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Dubbo横幅应用监听器
 * 
 * @author chenboyang
 *
 */
@Slf4j
@Order(LoggingApplicationListener.DEFAULT_ORDER)
public class DubboBannerApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    /** 横幅模型 */
    private static Mode bannerMode = Mode.CONSOLE;

    /**
     * 设置横幅模型
     * 
     * @param mode 横幅模型
     */
    public static void setBannerMode(Mode mode) {
        bannerMode = mode;
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        if (bannerMode == Mode.OFF) {
            return;
        }
        String bannerText = this.buildBannerText();
        if (bannerMode == Mode.CONSOLE) {
            System.out.print(bannerText);
        } else if (bannerMode == Mode.LOG) {
            log.info(bannerText);
        }
    }

    /**
     * 构建横幅文本
     * 
     * @return 横幅文本
     */
    private String buildBannerText() {
        StringBuilder bannerTextBuilder = new StringBuilder();
        bannerTextBuilder.append(DubboSpringBootStarterConstants.LINE_SEPARATOR).append(DubboLogo.dubbo)
                .append(" :: Dubbo ::        (v").append(Version.getVersion()).append(")")
                .append(DubboSpringBootStarterConstants.LINE_SEPARATOR);
        return bannerTextBuilder.toString();
    }

}
