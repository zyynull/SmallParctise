package hash;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HashConfilct {
    /**
     * hash多种实现方式
     */
    public static Integer hashCode(String str, Integer multiplier) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = multiplier * hash + str.charAt(i);
        }
        return hash;
    }


    /**
     * 计算 hash code 冲突率，顺便分析一下 hash code 最大值和最小值，并输出
     *
     * @param multiplier
     * @param hashs
     */

    public static void calculateConflictRate(Integer multiplier, List<Integer> hashs) {
        Comparator<Integer> cp = (x, y) -> x > y ? 1 : (x < y ? -1 : 0);
        int maxHash = hashs.stream().max(cp).get();
        int minHash = hashs.stream().min(cp).get();
        // 计算冲突数及冲突率
        int uniqueHashNum = (int) hashs.stream().distinct().count();
        int conflictNum = hashs.size() - uniqueHashNum;
        double conflictRate = (conflictNum * 1.0) / hashs.size();
        System.out.println(String.format("multiplier=%4d, minHash=%11d, maxHash=%10d, conflictNum=%6d, conflictRate=%.4f%%", multiplier, minHash, maxHash, conflictNum, conflictRate * 100));
    }

    /**
     * 构造数据集进行测试，记录不重复的hash值与原集合大小比较
     * 冲突率只是概率
     */
    public static void main(String[] args) {
        List<Integer> hash = new ArrayList<Integer>();
        calculateConflictRate(31, hash);
    }
}
