1class Solution {
2    public int[] searchRange(int[] nums, int target) {
3        int[] res={-1,-1};
4        int left=binSearch(nums,target,true);
5        int right=binSearch(nums,target,false);
6        res[0]=left;
7        res[1]=right;
8        return res;
9    
10    }
11    private int binSearch(int[] nums,int target,boolean isLeft){
12        int left=0,right=nums.length-1;
13        int ind=-1;
14        while(left<=right){
15            int mid=left+(right-left)/2;
16            if(target>nums[mid]){
17                left=mid+1;
18            }else if(target<nums[mid]){
19                right=mid-1;
20            }else{
21                ind=mid;
22                if(isLeft){
23                    right=mid-1;
24                }else{
25                    left=mid+1;
26                }
27            }
28        }return ind;
29    }
30}