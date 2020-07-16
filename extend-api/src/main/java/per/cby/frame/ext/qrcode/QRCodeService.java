package per.cby.frame.ext.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import per.cby.frame.ext.qrcode.QRCodeModel.Desc;
import per.cby.frame.ext.qrcode.QRCodeModel.Logo;
import com.google.zxing.Result;

/**
 * 二维码生成与解析接口
 * 
 * @author chenboyang
 *
 */
public interface QRCodeService {

    /** 黑色 */
    int BLACK = 0xFF000000;

    /** 白色 */
    int WHITE = 0xFFFFFFFF;

    /**
     * 生成二维码图片
     * 
     * @param model 二维码生成参数模型
     * @return 二维码图片
     */
    BufferedImage createQRImage(QRCodeModel model);

    /**
     * 为二维码图片添加Logo
     * 
     * @param image 二维码图片
     * @param logo  Logo参数模型
     */
    void addLogo(BufferedImage image, Logo logo);

    /**
     * 为二维码图片添加描述信息
     * 
     * @param image 二维码图片
     * @param desc  描述信息参数模型
     * @return 添加完描述信息的二维码图片
     */
    BufferedImage pressDesc(BufferedImage image, Desc desc);

    /**
     * 解析二维码信息
     * 
     * @param image 二维码图片
     * @return 二维码信息
     */
    Result readQRImage(BufferedImage image);

    /**
     * 生成二维码图片临时文件
     * 
     * @param model 二维码生成参数模型
     * @return 二维码图片文件
     */
    File createQRImageTemp(QRCodeModel model);

    /**
     * 生成二维码图片临时文件到指定目录下
     * 
     * @param model 二维码生成参数模型
     * @param dir   目录
     * @return 二维码图片文件
     */
    File createQRImageTemp(QRCodeModel model, File dir);

    /**
     * 生成二维码图片到文件
     * 
     * @param model 二维码生成参数模型
     * @param file  二维码图片文件
     */
    void createQRImageToFile(QRCodeModel model, File file);

    /**
     * 生成二维码图片到输出流
     * 
     * @param model  二维码生成参数模型
     * @param output 二维码图片输出流
     */
    void createQRImageToOutputStream(QRCodeModel model, OutputStream output);

    /**
     * 从二维码图片文件读取二维码信息内容
     * 
     * @param file 二维码图片文件
     * @return 二维码信息内容
     */
    String readQRImageFromFile(File file);

    /**
     * 从二维码图片输入流读取二维码信息内容
     * 
     * @param input 二维码图片输入流
     * @return 二维码信息内容
     */
    String readQRImageFromInputStream(InputStream input);

}
