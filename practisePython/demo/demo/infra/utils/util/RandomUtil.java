package demo.demo.infra.utils.util;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Author:yaowenxi
 * Date: 2018/5/3
 */
@Component
public class RandomUtil {

    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final String RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final Random RANDOM = new Random();

    private static final int RANDOM_COUNT = 16;

    public String getRandomStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < RANDOM_COUNT; i++) {
            sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
        }
        return sb.toString();
    }

    /**
     * func -根据随机数长度返回一个随机数
     *
     * @param length
     * @return java.lang.String
     * @version 1.0  2018/4/29 yaowenxi
     */
    public String getRandomStrByLength(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成随机len位数字字符串
     *
     * @param len
     * @return
     */
    public static String genRandomNumber(int len) {
        return generate("0123456789", len);
    }

    /**
     * 生成随机len位验证码，已排除部分易混淆的字符
     *
     * @param len
     * @return
     */
    public static String genRandomStr(int len) {
        return generate("abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789", len);
    }

    /**
     * 根据chars的字符生成len位随机字符串
     *
     * @param chars
     * @param len
     * @return
     */
    private static String generate(String chars, int len) {
        int lens = chars.length();
        StringBuilder randomSB = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int rand = RandomUtils.nextInt(lens);
            randomSB.append(chars.charAt(rand));
        }
        return randomSB.toString();
    }

    /**
     * 生成包含数字和字母的八位激活码
     *
     * @param n 激活码个数
     * @return
     */
    public static List<String> generateActivationCode(int n) {
        List<String> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(generate(RANDOM_CHARS, 8));
        }
        return list;
    }

    /**
     * 获取只包含大写字母和数字的uuid-32位
     *
     * @return
     */
    public static String getUpperUUID_32() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 获取只包含大写字母和数字的uuid-16位
     *
     * @return
     */
    public static String getUpperUUID_16() {
        String uuid = UUID.randomUUID().toString();
        char[] cs = new char[32];
        char c = 0;
        for (int i = uuid.length() / 2, j = 1; i-- > 0; ) {
            if ((c = uuid.charAt(i)) != '-') {
                cs[j++] = c;
            }
        }
        String uid = String.valueOf(cs);
        return uid.trim().toUpperCase();
    }
    
    /**
     * 利用时间戳转换成16进制码
     * */
    public static String generateShotTimeId() {
    	return Integer.toHexString((int)(new Date().getTime()));
    }
}
