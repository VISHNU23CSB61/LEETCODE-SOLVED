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
17    int diameter=0;
18    public int diameterOfBinaryTree(TreeNode root) {
19        height(root);
20        return diameter;
21    }
22    private int height(TreeNode r){
23        if(r==null)return 0;
24        int l=height(r.left),rt=height(r.right);
25        diameter=Math.max(diameter,l+rt);
26        return 1+Math.max(l,rt);
27    }
28}