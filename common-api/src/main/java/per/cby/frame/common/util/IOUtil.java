package per.cby.frame.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * IO帮助类
 * 
 * @author chenboyang
 *
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class IOUtil extends IOUtils {

    /**
     * 将输入流数据写入到输出流
     * 
     * @param input  输入流
     * @param output 输出流
     */
    public void write(InputStream input, OutputStream output) {
        try {
            int count, bufferLen = 1024;
            byte data[] = new byte[bufferLen];
            while ((count = input.read(data, 0, bufferLen)) != -1) {
                output.write(data, 0, count);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 对象转字节数组
     * 
     * @param obj 对象
     * @return 字节数组
     */
    public byte[] objectToBytes(Serializable obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);) {
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 字节数组转对象
     * 
     * @param bytes 字节数组
     * @return 对象
     */
    public <T extends Serializable> T bytesToObject(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis);) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
