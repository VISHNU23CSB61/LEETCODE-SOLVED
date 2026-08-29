1class Solution {
2    public int calculateMinimumHP(int[][] dungeon) {
3        int m=dungeon.length;
4        int n=dungeon[0].length;
5        int[][] dp=new int[m+1][n+1];
6         for (int i = 0; i <= m; i++) {
7        Arrays.fill(dp[i], Integer.MAX_VALUE);
8    }
9        dp[m][n-1]=1;
10        dp[m-1][n]=1;
11        for(int i=m-1;i>=0;i--){
12            for(int j=n-1;j>=0;j--){
13                int next=Math.min(dp[i+1][j],dp[i][j+1]);
14                dp[i][j]=Math.max(1,next-dungeon[i][j]);
15            }
16        }return dp[0][0];
17    }
18}