1class Solution {
2    public int totalNumbers(int[] digits) {
3        int[] f=new int[10];
4        int res=0;
5        for (int d : digits) f[d]++;
6        for(int i=1;i<10;i++){
7            for(int j=0;j<10;j++){
8                for(int k=0;k<9;k+=2){
9                    f[i]--;f[j]--;f[k]--;
10                    if(f[i]>=0&&f[j]>=0&&f[k]>=0)res++;
11                    f[i]++;f[j]++;f[k]++;
12                }
13            }
14        }return res;
15
16    }
17}