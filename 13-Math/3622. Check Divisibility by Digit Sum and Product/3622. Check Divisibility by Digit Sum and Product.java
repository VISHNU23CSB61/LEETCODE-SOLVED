1class Solution {
2    public boolean checkDivisibility(int n) {
3        int sum=0;
4        int prod=1;
5        int temp=n;
6        while(n>0){
7            int r=n%10;
8            sum+=r;
9            prod*=r;
10            n/=10;
11        }
12        int tot=sum+prod;
13        if(tot==temp){
14            return true;
15        }return false;
16    }
17}