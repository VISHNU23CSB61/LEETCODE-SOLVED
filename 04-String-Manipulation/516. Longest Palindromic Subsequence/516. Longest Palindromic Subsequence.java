1import java.util.*;
2class Solution {
3    public int longestPalindromeSubseq(String s) {
4        StringBuilder sb= new StringBuilder(s);
5        sb.reverse();
6        String s2=sb.toString();
7        int n=s.length();
8        int m=s2.length();
9        int[][] dp=new int[n+1][m+1];
10        for(int i=1;i<=n;i++){
11            for(int j=1;j<=m;j++){
12                if(s.charAt(i-1)==s2.charAt(j-1)){
13                    dp[i][j]=1+dp[i-1][j-1];
14                }else{
15                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
16                }
17            }
18        }return dp[n][m];
19    }
20}