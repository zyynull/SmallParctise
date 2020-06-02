package BootPractise.Logs;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.util.*;
import java.util.Random;

public class main {
    /*
    //初始化
    private static Map<Integer, Integer> numMap = new HashMap<Integer, Integer>() {
        {
            put(0, 0);
            put(1, 30);
            put(2, 25);
            put(3, 20);
            put(4, 18);
            put(5, 16);
            put(6, 14);
            put(7, 13);
            put(8, 12);
        }
    };
    public void test() {
        //boolean res=isValid("()");
        Map<Integer, Integer> score = new HashMap<Integer, Integer>() {
            {
                put(1, 0);
                put(2, 0);
                put(3, 0);
                put(4, 0);
                put(5, 0);
                put(6, 0);
                put(7, 0);
                put(8, 0);
            }
        };
        int scoreTimes = 1;
        //第一次加分
        addScore(score,scoreTimes);
        Iterator<Integer> it = score.values().iterator();
        */
    /**
     * https://www.zhihu.com/club/1187410375191982080/post/1246837761598197760
     * 有限次怎么来的？只能递归
     *//*
        while (it.hasNext()) {
            if (it.equals(it.next())) {
                continue;
            } else {
                addScore(score,scoreTimes);
            }
        }
    }

    ;

    public void addScore(Map<Integer, Integer> score,int scoreTimes) {
        for (int i = 1; i < 9; i++) {
            Random r = new Random();
            //获取0-8之间的随机数
            int number = r.nextInt(9);
            Integer a = score.get(i);
            a += numMap.get(number);
        }
        scoreTimes ++;
    }*/

    /*leetcode T71

    public String simplifyPath(String path) {
        //三种特殊情况
        //在规范路径中，多个连续斜杠需要用一个斜杠替换
        //根目录向上一级是不可行的，因为根是你可以到达的最高级。
        //注意，最后一个目录名后面没有斜杠。
        //运算符
        // .    表示当前目录本身
        // ..   将目录切换到上一级
        String simplifyPath = null;
        String[] split = path.split("/");
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < split.length; i++) {
            if (!stack.isEmpty() && split[i].equals("..")) {
                stack.pop();
            } else if (!split[i].equals("") && !split[i].equals(".") && !split[i].equals("..")) {
                stack.push(split[i]);
            }
        }
        if (stack.isEmpty())
            return "/";

        StringBuffer res = new StringBuffer();
        for (int i = 0; i < stack.size(); i++) {
            res.append("/" + stack.get(i));
        }
        return res.toString();
    }*/
}
