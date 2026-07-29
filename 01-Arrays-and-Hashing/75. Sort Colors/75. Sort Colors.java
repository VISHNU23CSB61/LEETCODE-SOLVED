1class Solution {
2    public void sortColors(int[] nums) {
3        for(int i=0;i<nums.length;i++){
4            for(int j=0;j<nums.length-i-1;j++){
5                if(nums[j]>nums[j+1]){
6                    int temp=nums[j];
7                    nums[j]=nums[j+1];
8                    nums[j+1]=temp;
9                }
10            }
11        }
12    }
13}