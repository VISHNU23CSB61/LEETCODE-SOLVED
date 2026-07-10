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
17       public int maxAncestorDiff(TreeNode root) {
18        return dfs(root, root.val, root.val);
19    }
20
21    public int dfs(TreeNode root, int mn, int mx) {
22        if (root == null) return mx - mn;
23        mx = Math.max(mx, root.val);
24        mn = Math.min(mn, root.val);
25        return Math.max(dfs(root.left, mn, mx), dfs(root.right, mn, mx));
26    }
27}