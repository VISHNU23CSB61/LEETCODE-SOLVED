1class Solution {
2    public int smallestNumber(int n, int t) {
3        while(!check(n,t)){
4            n++;
5        }return n;
6    }
7
8
9    public boolean check(int n,int t){
10        int pro=1;
11        while(n>0){
12            int r=n%10;
13            pro*=r;
14            n=n/10;
15        }if(pro%t==0){
16            return true;
17        }else{
18            return false;
19        }
20    }
21}