1class Solution {
2    public int firstStableIndex(int[] nums, int k) {
3        int n=nums.length;
4        int max=nums[0];
5        for(int i=0;i<n;i++){
6            max = Math.max(max, nums[i]);
7            int min = nums[i];
8            for(int j=i;j<n;j++){
9                if(nums[j]<min){
10                    min=nums[j];
11                }
12            }
13            int inst=max-min;
14            if(inst<=k){
15                return i;
16            }
17
18        }
19        return -1;
20    }
21}