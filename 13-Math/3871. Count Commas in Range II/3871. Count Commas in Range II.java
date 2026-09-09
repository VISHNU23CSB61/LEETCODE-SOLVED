1class Solution {
2    public long countCommas(long n) {
3        long ans=0;
4        for(long base=1000;base<=n;base*=1000){
5            ans+=(n-base+1);
6            if(base>Long.MAX_VALUE/1000)break;
7        }
8        
9        
10        return ans;
11    }
12}