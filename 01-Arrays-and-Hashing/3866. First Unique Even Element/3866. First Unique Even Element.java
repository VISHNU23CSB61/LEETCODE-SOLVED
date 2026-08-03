1class Solution {
2    public int firstUniqueEven(int[] nums) {
3       HashMap<Integer,Integer> m=new HashMap<>();
4       for(int x:nums){
5        m.put(x,m.getOrDefault(x,0)+1);
6       }
7       for(int x:nums){
8        if(m.get(x)==1 && x%2==0){
9            return x;
10        }
11       }return -1;
12    }
13}