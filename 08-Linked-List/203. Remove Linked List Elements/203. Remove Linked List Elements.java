1/**
2 * Definition for singly-linked list.
3 * public class ListNode {
4 *     int val;
5 *     ListNode next;
6 *     ListNode() {}
7 *     ListNode(int val) { this.val = val; }
8 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
9 * }
10 */
11class Solution {
12    public ListNode removeElements(ListNode head, int val) {
13       ListNode ans=new ListNode(0,head);
14       ListNode dummy=ans;
15       while(dummy!=null){
16        while(dummy.next!=null && dummy.next.val==val){
17            dummy.next=dummy.next.next;
18        }dummy=dummy.next;
19       }return ans.next;
20    }
21}