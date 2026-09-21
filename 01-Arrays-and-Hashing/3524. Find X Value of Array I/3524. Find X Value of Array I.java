1class Solution {
2    public long[] resultArray(int[] A, int k) {
3         long[] res = new long[k];
4        int[] freq = new int[k];
5
6        for (int n : A) {
7            n %= k;
8            int[] cur = new int[k];
9            cur[n] = 1;
10
11            for (int x = 0; x < k; x++)
12                cur[x * n % k] += freq[x];
13
14            freq = cur;
15            for (int x = 0; x < k; x++)
16                res[x] += freq[x];
17        }
18
19        return res;
20    }
21}