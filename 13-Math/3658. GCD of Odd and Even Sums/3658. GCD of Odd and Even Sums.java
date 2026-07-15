1class Solution {
2    public int gcdOfOddEvenSums(int n) {
3        int sumodd=0;
4        int sumeven=0;
5        int k=1;
6        int j=2;
7        for(int i=0;i<n;i++){
8            sumodd=sumodd+k;
9            k+=2;
10            sumeven=sumeven+j;
11            j+=2;
12        }
13        int res=gcd(sumodd,sumeven);
14        return res;
15    }
16    public static int gcd(int a,int b){
17        while(b!=0){
18            int temp=b;
19            b=a%b;
20            a=temp;
21        }return Math.abs(a);
22    }
23}