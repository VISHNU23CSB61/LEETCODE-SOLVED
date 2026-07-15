1class Solution {
2    public int change(int amount, int[] coins) {
3        int[] dp=new int[amount+1];
4        dp[0]=1;
5        for(int c:coins){
6            for(int i=c;i<amount+1;i++){
7                dp[i]+=dp[i-c];
8            }
9        }
10        return dp[amount];
11    }
12}