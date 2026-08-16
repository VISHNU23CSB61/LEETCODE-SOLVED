1class Solution {
2    public boolean stoneGameIX(int[] stones) {
3        int[] f = {0, 0, 0};
4
5        for (int s : stones)
6            f[s % 3]++;
7
8        if ((f[0] & 1) == 0)
9            return Math.min(f[1], f[2]) > 0;
10
11        return Math.abs(f[1] - f[2]) > 2;
12    }
13}