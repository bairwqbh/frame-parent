package per.cby.frame.ext.qrcode;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 二维码信息参数配置模型
 * 
 * @author chenboyang
 *
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class QRCodeModel {

    /** 二维码信息内容 */
    private String content;

    /** 二维码宽度 */
    private int width = 200;

    /** 二维码高度 */
    private int height = 200;

    /** 二维码Logo配置信息 */
    private Logo logo;

    /** 二维码描述配置信息 */
    private Desc desc;

    /**
     * 实例化二维码信息参数配置模型
     * 
     * @param content 内容
     */
    public QRCodeModel(String content) {
        this.content = content;
    }

    /**
     * 实例化二维码信息参数配置模型
     * 
     * @param content 内容
     * @param width   宽度
     * @param height  高度
     */
    public QRCodeModel(String content, int width, int height) {
        this.content = content;
        this.width = width;
        this.height = height;
    }

    /**
     * 实例化二维码信息参数配置模型
     * 
     * @param content   内容
     * @param width     宽度
     * @param height    高度
     * @param logoFile  Logo文件
     * @param logoRatio Logo占位比
     * @param descTexts 描述文本
     * @param descFont  描述字体
     * @param descColor 描述颜色
     */
    public QRCodeModel(String content, int width, int height, File logoFile, int logoRatio, String[] descTexts,
            Font descFont, Color descColor) {
        this.content = content;
        this.width = width;
        this.height = height;
        this.logo = createLogo(logoFile, logoRatio);
        this.desc = createDesc(descTexts, descFont, descColor);
    }

    /**
     * 创建二维码Logo配置模型
     * 
     * @return 二维码Logo配置模型
     */
    public Logo createLogo() {
        return new Logo();
    }

    /**
     * 创建二维码Logo配置模型
     * 
     * @param file  文件
     * @param ratio 占位比
     * @return 二维码Logo配置模型
     */
    public Logo createLogo(File file, int ratio) {
        return new Logo(file, ratio);
    }

    /**
     * 创建二维码描述配置模型
     * 
     * @return 二维码描述配置模型
     */
    public Desc createDesc() {
        return new Desc();
    }

    /**
     * 创建二维码描述配置模型
     * 
     * @param texts 文本
     * @return 二维码描述配置模型
     */
    public Desc createDesc(String[] texts) {
        return new Desc(texts);
    }

    /**
     * 创建二维码描述配置模型
     * 
     * @param texts 文本
     * @param font  字体
     * @param color 颜色
     * @return 二维码描述配置模型
     */
    public Desc createDesc(String[] texts, Font font, Color color) {
        return new Desc(texts, font, color);
    }

    /**
     * 二维码Logo配置模型
     * 
     * @author chenboyang
     *
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Logo {

        /** Logo图片文件 */
        private File file;

        /** Logo占位比 */
        private int ratio;

    }

    /**
     * 二维码描述配置模型
     * 
     * @author chenboyang
     *
     */
    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Desc {

        /**
         * 实例化二维码描述配置模型
         * 
         * @param texts 文本
         */
        public Desc(String[] texts) {
            this.texts = texts;
        }

        /** 描述文本组 */
        private String[] texts;

        /** 文本字体 */
        private Font font = new Font(null, Font.BOLD, 10);

        /** 文本颜色 */
        private Color color = Color.BLACK;

    }

}
