package per.cby.frame.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * 编码帮助类
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
public class CodeUtil {

    /** MASK */
    public final int MASK = 0xFF;

    /**
     * short转byte数组
     * 
     * @param num 数值
     * @return byte数组
     */
    public byte[] shortToBytes(short num) {
        return shortToBytes(num, new byte[2]);
    }

    /**
     * short转byte数组
     * 
     * @param num   数值
     * @param bytes byte数组
     * @return byte数组
     */
    public byte[] shortToBytes(short num, byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((num >> ((bytes.length - 1 - i) * 8)) & MASK);
        }
        return bytes;
    }

    /**
     * byte数组转short
     * 
     * @param bytes byte数组
     * @return short
     */
    public short bytesToShort(byte... bytes) {
        short num = 0;
        for (int i = 0; i < bytes.length; i++) {
            num |= (bytes[i] & MASK) << ((bytes.length - 1 - i) * 8);
        }
        return num;
    }

    /**
     * int转byte数组
     * 
     * @param num 数值
     * @return byte数组
     */
    public byte[] intToBytes(int num) {
        return intToBytes(num, new byte[4]);
    }

    /**
     * int转byte数组
     * 
     * @param num   数值
     * @param bytes byte数组
     * @return byte数组
     */
    public byte[] intToBytes(int num, byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((num >> ((bytes.length - 1 - i) * 8)) & MASK);
        }
        return bytes;
    }

    /**
     * byte数组转int
     * 
     * @param bytes byte数组
     * @return int
     */
    public int bytesToInt(byte... bytes) {
        int num = 0;
        for (int i = 0; i < bytes.length; i++) {
            num |= (bytes[i] & MASK) << ((bytes.length - 1 - i) * 8);
        }
        return num;
    }

    /**
     * long转byte数组
     * 
     * @param num 数值
     * @return byte数组
     */
    public byte[] longToBytes(long num) {
        return longToBytes(num, new byte[8]);
    }

    /**
     * long转byte数组
     * 
     * @param num   数值
     * @param bytes byte数组
     * @return byte数组
     */
    public byte[] longToBytes(long num, byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((num >> ((bytes.length - 1 - i) * 8)) & MASK);
        }
        return bytes;
    }

    /**
     * byte数组转long
     * 
     * @param bytes byte数组
     * @return long
     */
    public long bytesToLong(byte... bytes) {
        long num = 0L;
        for (int i = 0; i < bytes.length; i++) {
            num |= (bytes[i] & MASK) << ((bytes.length - 1 - i) * 8);
        }
        return num;
    }

    /**
     * float转byte数组
     * 
     * @param num 数值
     * @return byte数组
     */
    public byte[] floatToBytes(float num) {
        return floatToBytes(num, new byte[4]);
    }

    /**
     * float转byte数组
     * 
     * @param num   数值
     * @param bytes byte数组
     * @return byte数组
     */
    public byte[] floatToBytes(float num, byte[] bytes) {
        int value = Float.floatToRawIntBits(num);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((value >> ((bytes.length - 1 - i) * 8)) & MASK);
        }
        return bytes;
    }

    /**
     * byte数组转float
     * 
     * @param bytes byte数组
     * @return float
     */
    public float bytesToFloat(byte... bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value |= (bytes[i] & MASK) << ((bytes.length - 1 - i) * 8);
        }
        return Float.intBitsToFloat(value);
    }

    /**
     * float转byte数组
     * 
     * @param num 数值
     * @return byte数组
     */
    public byte[] doubleToBytes(double num) {
        return doubleToBytes(num, new byte[8]);
    }

    /**
     * float转byte数组
     * 
     * @param num   数值
     * @param bytes byte数组
     * @return byte数组
     */
    public byte[] doubleToBytes(double num, byte[] bytes) {
        long value = Double.doubleToRawLongBits(num);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((value >> ((bytes.length - 1 - i) * 8)) & MASK);
        }
        return bytes;
    }

    /**
     * byte数组转double
     * 
     * @param bytes byte数组
     * @return double
     */
    public double bytesToDouble(byte... bytes) {
        long value = 0L;
        for (int i = 0; i < bytes.length; i++) {
            value |= (bytes[i] & MASK) << ((bytes.length - 1 - i) * 8);
        }
        return Double.longBitsToDouble(value);
    }

    /**
     * 生成校验和
     * 
     * @param data 数据
     * @return 带校验和的数据
     */
    public int checksum(byte... data) {
        short checksum = 0;
        if (ArrayUtils.isNotEmpty(data)) {
            for (int i = 0; i < data.length; i++) {
                checksum += (short) data[i] & MASK;
            }
        }
        return checksum & 0xFFFF;
    }

    /**
     * 验证校验和
     * 
     * @param data     数据
     * @param checksum 校验和
     * @return 是否正确
     */
    public boolean verifyChecksum(int checksum, byte... data) {
        return checksum == checksum(data);
    }

    /**
     * 修剪16进制字符串
     * 
     * @param hex 16进制字符串
     * @return 16进制字符串
     */
    public String trimHex(String hex) {
        if (StringUtils.isEmpty(hex)) {
            return hex;
        }
        return hex.replace("0x", "").replace("x", "").replace(" ", "");
    }

}
