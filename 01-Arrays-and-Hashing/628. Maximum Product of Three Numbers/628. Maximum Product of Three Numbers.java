1class Solution {
2    public int maximumProduct(int[] nums) {
3        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
4        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
5
6        for (int n : nums) {
7            if (n > max1) {
8                max3 = max2;
9                max2 = max1;
10                max1 = n;
11            } else if (n > max2) {
12                max3 = max2;
13                max2 = n;
14            } else if (n > max3) {
15                max3 = n;
16            }
17            if (n < min1) {
18                min2 = min1;
19                min1 = n;
20            } else if (n < min2) {
21                min2 = n;
22            }
23        }
24        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
25    }
26}