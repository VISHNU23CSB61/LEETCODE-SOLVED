1class Solution {
2    public boolean isPowerOfFour(int n) {
3        return (n & (n - 1)) == 0 && n % 3 == 1;
4    }
5}