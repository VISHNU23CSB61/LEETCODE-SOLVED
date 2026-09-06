1class Solution {
2    public int missingNumber(int[] nums) {
3        int n=nums.length;
4        int expected=n*(n+1)/2;
5        int actual=0;
6        for(int x: nums){
7            actual+=x;
8        }
9        return expected-actual;
10    }
11}