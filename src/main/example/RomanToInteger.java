package example;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * @lc app=leetcode id=13 lang=java
 *
 * [13] Roman to Integer
 * Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
 * I can be placed before V (5) and X (10) to make 4 and 9.
X can be placed before L (50) and C (100) to make 40 and 90.
C can be placed before D (500) and M (1000) to make 400 and 900.

 *
 */
public class RomanToInteger {
    public int romanToInt(String s) {
        //a = {'I':1, 'V':5, 'X':10, 'L':50, 'C':100, 'D':500, 'M':1000}
        HashMap romaNumber =new HashMap();
        romaNumber.put('I', 1);
        romaNumber.put('V', 5);
        romaNumber.put('X', 10);
        romaNumber.put('L', 50);
        romaNumber.put('C', 100);
        romaNumber.put('D', 500);
        romaNumber.put('M', 1000);
        //reverse&compare
        int firstValue = 0;
        int nextValue = 0;
        int sum = 0;

        for (int i = 0; i < s.length(); i++){
            firstValue = (int)romaNumber.get(s.charAt(i));
            if (i == s.length()-1){
                sum += firstValue;
            }else {
                nextValue = (int)romaNumber.get(s.charAt(i+1));
                if (firstValue >= nextValue){
                    sum += firstValue;
                }else{
                    sum -= firstValue;
                }
            }
        }
        return sum;
    }
    public static void main(String[] argv){
        System.out.println("'V' as:"+'V'+"，v as "+"V");
    }
}
