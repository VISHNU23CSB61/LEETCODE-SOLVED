1class Solution {
2      static final int[] q=new int[45];
3        static {
4            int n=0;
5            for(int i=1;i<10;i++){
6                q[n++]=i;
7            }
8            for(int i=0;i<n;i++){
9                int d=q[i]%10;
10                if(d<9)
11                q[n++]=q[i]*10+d+1;
12            }
13        }
14    public List<Integer> sequentialDigits(int low, int high) {
15      List<Integer> res=new ArrayList<>();
16      for(int x:q){
17        if(x>=low && x<=high)res.add(x);
18
19      }return res;
20    }
21
22}