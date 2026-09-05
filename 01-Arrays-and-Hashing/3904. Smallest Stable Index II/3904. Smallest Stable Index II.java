1class Solution {
2    public int firstStableIndex(int[] nums, int k) {
3        int n=nums.length;
4        int max=nums[0];
5        int[] min=new int[n];
6        min[n-1]=nums[n-1];
7        for(int i=n-2;i>=0;i--){
8            min[i]=Math.min(nums[i],min[i+1]);
9        }
10        for(int i=0;i<n;i++){
11            max=Math.max(max,nums[i]);
12            int inst=max-min[i];
13            if(inst<=k){
14                return i;
15            }
16        }return -1;
17    }
18}