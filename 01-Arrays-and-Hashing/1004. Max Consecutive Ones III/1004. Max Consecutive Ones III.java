1class Solution {
2    public int longestOnes(int[] nums, int k) {
3        Map<Integer,Integer> freq=new HashMap<>();
4        int l=0;
5        int maxl=0;
6        for(int r=0;r<nums.length;r++){
7            freq.merge(nums[r],1,Integer::sum);
8            while(freq.getOrDefault(0,0)>k){
9                freq.merge(nums[l],-1,Integer::sum);
10                l++;
11            }
12            maxl=Math.max(maxl,r-l+1);
13        }return maxl;
14        
15    }
16}