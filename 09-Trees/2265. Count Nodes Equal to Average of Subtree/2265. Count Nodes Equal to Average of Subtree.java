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
17    int ans=0;
18    public int averageOfSubtree(TreeNode root) {
19        dfs(root);
20        return ans;
21    }
22    private int[] dfs(TreeNode node){
23        if(node==null)return new int[]{0,0};
24        int[] left=dfs(node.left);
25        int[] right=dfs(node.right);
26        int sum=left[0]+right[0]+node.val;
27        int cnt=left[1]+right[1]+1;
28        if(sum/cnt==node.val)ans++;
29        return new int[]{sum,cnt};
30    }
31}