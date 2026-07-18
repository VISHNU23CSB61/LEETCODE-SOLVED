1class Solution {
2    public int findGCD(int[] nums) {
3        int mx=Integer.MIN_VALUE;
4        int mn=Integer.MAX_VALUE;
5        for(int x:nums){
6            mx=Math.max(mx,x);
7            mn=Math.min(mn,x);
8        }
9        return gcd(mx,mn);
10    }
11    public int gcd(int a,int b){
12        while(b!=0){
13            int temp=b;
14            b=a%b;
15            a=temp;
16        }return Math.abs(a);
17    }
18}