1class Solution {
2    public boolean winnerSquareGame(int n) {
3       boolean[] dp=new boolean[n+1];
4
5       for(int i=1;i<=n;i++){
6        for(int k=1;k*k<=i;k++){
7            if(!dp[i-k*k]){
8                dp[i]=true;
9            }
10        }
11       }
12       return dp[n];
13    }
14}