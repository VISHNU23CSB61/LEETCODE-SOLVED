1class Solution {
2    public int coinChange(int[] coins, int amount) {
3        int[] dp=new int[amount+1];
4        Arrays.fill(dp,amount+1);
5        dp[0]=0;
6        for(int c:coins){
7            for(int i=c;i<=amount;i++){
8                dp[i]=Math.min(dp[i],dp[i-c]+1);
9            }
10        }return dp[amount]>amount?-1:dp[amount];
11    }
12}