package per.cby.frame.ext.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import per.cby.frame.ext.qrcode.QRCodeModel.Desc;
import per.cby.frame.ext.qrcode.QRCodeModel.Logo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.extern.slf4j.Slf4j;

/**
 * 二维码生成与解析接口实现类
 * 
 * @author chenboyang
 *
 */
@Slf4j
public class QRCodeServiceImpl implements QRCodeService {

    @Override
    public BufferedImage createQRImage(QRCodeModel model) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix matrix = new MultiFormatWriter().encode(model.getContent(), BarcodeFormat.QR_CODE,
                    model.getWidth(), model.getHeight(), hints);
            BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < model.getWidth(); x++) {
                for (int y = 0; y < model.getHeight(); y++) {
                    image.setRGB(x, y, (matrix.get(x, y) ? BLACK : WHITE));
                }
            }
            if (model.getLogo() != null) {
                addLogo(image, model.getLogo());
            }
            if (model.getDesc() != null) {
                image = pressDesc(image, model.getDesc());
            }
            return image;
        } catch (WriterException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void addLogo(BufferedImage image, Logo logo) {
        try {
            Graphics2D graphics = image.createGraphics();
            BufferedImage logoImage = ImageIO.read(logo.getFile());
            int size = image.getWidth() / logo.getRatio();
            int x = (image.getWidth() - size) / 2;
            int y = (image.getHeight() - size) / 2;
            graphics.drawImage(logoImage, x, y, size, size, null);
            graphics.dispose();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public BufferedImage pressDesc(BufferedImage image, Desc desc) {
        int descHeight = image.getHeight() + (desc.getFont().getSize() * desc.getTexts().length)
                + (desc.getFont().getSize() / 2);
        BufferedImage newImage = new BufferedImage(image.getWidth(), descHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        graphics.setColor(desc.getColor());
        graphics.setFont(desc.getFont());
        for (int i = 0; i < desc.getTexts().length; i++) {
            String text = desc.getTexts()[i];
            int x = 1;
            int y = image.getHeight() + (desc.getFont().getSize() * (i + 1));
            graphics.drawString(text, x, y);
        }
        graphics.dispose();
        return newImage;
    }

    @Override
    public Result readQRImage(BufferedImage image) {
        Result result = null;
        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
            result = (new MultiFormatReader()).decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public File createQRImageTemp(QRCodeModel model) {
        try {
            BufferedImage image = createQRImage(model);
            if (image != null) {
                File file = File.createTempFile("qrcode", ".jpg");
                ImageIO.write(image, "jpg", file);
                return file;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public File createQRImageTemp(QRCodeModel model, File dir) {
        try {
            BufferedImage image = createQRImage(model);
            if (image != null) {
                File file = File.createTempFile("qrcode", ".jpg", dir);
                ImageIO.write(image, "jpg", file);
                return file;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void createQRImageToFile(QRCodeModel model, File file) {
        try {
            BufferedImage image = createQRImage(model);
            if (image != null) {
                ImageIO.write(image, "jpg", file);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void createQRImageToOutputStream(QRCodeModel model, OutputStream output) {
        try {
            BufferedImage image = createQRImage(model);
            if (image != null) {
                ImageIO.write(image, "jpg", output);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String readQRImageFromFile(File file) {
        try {
            return Optional.ofNullable(ImageIO.read(file)).map(this::readQRImage).map(Result::getText).orElse(null);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String readQRImageFromInputStream(InputStream input) {
        try {
            return Optional.ofNullable(ImageIO.read(input)).map(this::readQRImage).map(Result::getText).orElse(null);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
