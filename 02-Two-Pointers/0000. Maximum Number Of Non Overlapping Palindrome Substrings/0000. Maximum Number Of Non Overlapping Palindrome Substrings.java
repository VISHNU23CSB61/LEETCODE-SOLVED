1class Solution {
2    public int maxPalindromes(String s, int k) {
3        int n=s.length();
4        if(k==1)return n;
5        int res=0;
6        for(int i=0;i<=n-k;i++){
7            if(check(s,i,i+k-1)){
8                res++;
9                i+=k-1;
10            }else if(i<n-k && check(s,i,i+k)){
11                res++;
12                i+=k;
13            }
14        }return res;
15
16    }
17    boolean check(String s,int l,int r){
18        for(;l<r;l++,r--){
19            if(s.charAt(l)!=s.charAt(r))return false;
20
21        }return true;
22    }
23}