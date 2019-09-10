package example;
import java.util.*;
public class testUtils {
  /**
   * 翻转一个数字
   * @param x
   * @return
   */
  public static int reverse(int x) {
    int sum = 0;
    int t = 0;
    while (x != 0) {
      if ((sum * 10L) > Integer.MAX_VALUE || (sum * 10L) < Integer.MIN_VALUE)
      {
        return 0;
      }
      sum = sum * 10 + x % 10;
      x = x / 10;
      System.out.println(sum);
    }
    return sum;
  }

  /**
   * 去除ArrayList重复object
   */
  public static void testDuplicate(){
  Map m=new HashMap();
  List value=new ArrayList();
  value.add("01");
  value.add("02");
  value.add("03");
  value.add("04");
  m.put("3008",value);
  List<Map> a=new ArrayList<>();
  for(int i=0;i<4;i++) {
    a.add(m);
  }
  System.out.println("original ArrayList a:"+a);
  HashSet b=new HashSet(a);
  System.out.println("HashSet b:"+b);
  ArrayList<HashMap>c=new ArrayList<>(b);
  System.out.println("after ArrayList c:"+c);
  //conclusion：this can get rid of the duplicate
  ArrayList<HashMap<String, Object>> d = new ArrayList<HashMap<String, Object>>(new HashSet(a));
  System.out.println("need test ArrayList d:"+d);
}

  public static void main(String[] argv) {

  }
}
