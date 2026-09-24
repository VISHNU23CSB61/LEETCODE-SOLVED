1class Solution {
2    public int smallestIndex(int[] nums) {
3      for(int i=0;i<nums.length;i++){
4        int sum=Sum(nums[i]);
5        if(sum==i){
6            return i;
7        }
8      }return -1;  
9    }
10    private int Sum(int n){
11        int sum=0;
12        while(n>0){
13            sum+=n%10;
14            n/=10;
15        }return sum;
16    }
17}