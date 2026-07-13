1class Solution {
2    public int longestCommonSubsequence(String text1, String text2) {
3        int n=text1.length();
4        int m=text2.length();
5        int[][] dp=new int[n+1][m+1];
6        for(int i=1;i<=n;i++){
7            for(int j=1;j<=m;j++){
8                if(text1.charAt(i-1)==text2.charAt(j-1)){
9                    dp[i][j]=1+dp[i-1][j-1];
10                }else{
11                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
12                }
13            }
14        }return dp[n][m];
15    }
16}