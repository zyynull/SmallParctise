//package example;
//
//import com.sun.tools.javac.util.ArrayUtils;
//
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.*;
//
//public class encryption {
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
//
//    String a;
//    String b;
//    encryption(String a,String b){
//        this.a=a;
//        this.b=b;
//    }
//
//    public String getA() {
//        return a;
//    }
//
//    public void setA(String a) {
//        this.a = a;
//    }
//
//    public String getB() {
//        return b;
//    }
//
//    public void setB(String b) {
//        this.b = b;
//    }
//
//    public static void main(String[] argv) {
////        String res = getSHA1("هناك عدد من الخيارات المختلفة التي يمكنك تضمينها مع `git branch` " +
////                "لمشاهدة معلومات مختلفة. لمزيد من التفاصيل حول الفروع ، يمكنك استخدام الخيار `-v`" +
////                " (أو `-vv` ، أو `--verbose` ). ستشمل قائمة الفروع قيمة SHA-1 2312313وخط الموضوع " +
////                "المخصص `HEAD` كل فرع بجانب اسمه.");
////        System.out.println("res length:"+res.length()+",codePointCount"+res.codePointCount(0,res.length()));
//        /**
//        String[] a={"1","2","3"};
//        String ab="2131";
//        //1.直接循环数组进行判断
//        long la=System.currentTimeMillis();
//        boolean abc=false;
//        for(String b : a){
//            abc=ab.matches(b)?true:false&&abc;
//        }
//        long la2=System.currentTimeMillis()-la;
//        System.out.println(abc?"affirmative":"negative"+",cost is:"+la2);
//        //2.转化为集合判断
//        long lb=System.currentTimeMillis();
//        int c=Arrays.asList(ab).indexOf(ab);
//        boolean d=c>0?true:false;
//        long lb2=System.currentTimeMillis()-lb;
//        System.out.println(d?"affirmative":"negative"+",cost is:"+lb2);
//
//
//        1.BigDecimal 设置值
//        BigDecimal lastYearAllPrem = new BigDecimal(0);
//        //必须这样写
//        lastYearAllPrem = lastYearAllPrem.add(new BigDecimal(0.11));
//        System.out.println(MAXIMUM_CAPACITY + "   " + DEFAULT_INITIAL_CAPACITY);
//        **/
//
///**        list<object> 按照object.attr进行修改、排序
//        List<encryption> value=new ArrayList();
//        value.add(new encryption("01","1"));
//        value.add(new encryption("02","2"));
//        value.add(new encryption("03","3"));
//        for(encryption xas:value){
//            switch (xas.getA()) {
//                case "01":
//                    xas.setA("02");
//                    break;
//                case "02":
//                    xas.setA("03");
//                    break;
//                case "03":
//                    xas.setA("01");
//                    break;
//            }
//        }
//        Collections.sort(value, new Comparator<encryption>() {
//            @Override
//            public int compare(encryption o1, encryption o2) {
//                int i = o1.getA().compareTo(o2.getA());
//                return i;
//            }
//        });
//
//        System.out.println(value);
// **/
//        System.out.println(null.equals("Y"));
//    }
//
//    /**
//     *     * 利用java原生的类实现SHA256加密
//     *     * @param str 加密后的报文
//     *     * @return
//     *     
//     */
//    public static String getSHA256(String str) {
//        MessageDigest messageDigest;
//        String encodestr = "";
//        try {
//            messageDigest = MessageDigest.getInstance("SHA-256");
//            messageDigest.update(str.getBytes("UTF-8"));
//            encodestr = byte2Hex(messageDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return encodestr;
//    }
//    /**
//     *     * 利用java原生的类实现SHA1加密
//     *     * @param str 加密后的报文
//     *     * @return
//     *     
//     */
//    public static String getSHA1(String str) {
//        MessageDigest messageDigest;
//        String encodestr = "";
//        try {
//            messageDigest = MessageDigest.getInstance("SHA-1");
//            messageDigest.update(str.getBytes("UTF-8"));
//            encodestr = byte2Hex(messageDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return encodestr;
//    }
//    /**
//     *     * 将byte转为16进制
//     *     * @param bytes
//     *     * @return
//     *     
//     */
//    private static String byte2Hex(byte[] bytes) {
//        StringBuffer stringBuffer = new StringBuffer();
//        String temp = null;
//        for (int i = 0; i < bytes.length; i++) {
//            temp = Integer.toHexString(bytes[i] & 0xFF);
//            if (temp.length() == 1) {
//                //1得到一位的进行补0操作
//                stringBuffer.append("0");
//            }
//            stringBuffer.append(temp);
//        }
//        return stringBuffer.toString();
//    }
//}
