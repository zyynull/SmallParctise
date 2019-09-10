package example;
import java.util.LinkedList;
public class Solution {
    /**
     * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
     *
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     */
    public class ListNode { int val; ListNode next; ListNode(int x) { val = x; } }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res =new ListNode(l1.val);
        while (res.next!=null){
            //res.=res.next;
        }
        return res;
    }
    public int calVal(int val1,int val2){
        int resVal=val1+val2;
        if(resVal>=10){
            resVal=0;
        }
        return resVal;
    }
}
