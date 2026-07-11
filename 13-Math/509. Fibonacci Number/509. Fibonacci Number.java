1class Solution {
2    public int fib(int n) {
3        if(n==1){
4            return 1;
5        }
6        if(n==0){
7            return 0;
8        }
9        return fib(n-1)+fib(n-2);
10    }
11}