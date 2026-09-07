1/**
2 * Definition for a binary tree node.
3 * public class TreeNode {
4 *     int val;
5 *     TreeNode left;
6 *     TreeNode right;
7 *     TreeNode() {}
8 *     TreeNode(int val) { this.val = val; }
9 *     TreeNode(int val, TreeNode left, TreeNode right) {
10 *         this.val = val;
11 *         this.left = left;
12 *         this.right = right;
13 *     }
14 * }
15 */
16class Solution {
17    public TreeNode sortedArrayToBST(int[] nums) {
18        return convert(nums,0,nums.length-1);
19    }
20    private TreeNode convert(int[] nums,int l,int r){
21        if(l>r){
22            return null;
23        }
24        int mid=l+(r-l)/2;
25        TreeNode t=new TreeNode(nums[mid]);
26        t.left=convert(nums,l,mid-1);
27        t.right=convert(nums,mid+1,r);
28        return t;
29    }
30}