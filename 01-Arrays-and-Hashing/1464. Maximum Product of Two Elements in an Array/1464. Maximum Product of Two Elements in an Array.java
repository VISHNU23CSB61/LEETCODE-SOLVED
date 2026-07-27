1class Solution {
2    public int maxProduct(int[] nums) {
3        int max1=0;
4        int max2=0;
5        for(int n:nums){
6            if(n>max1){
7              max2=max1;
8              max1=n;
9            }else if(n>max2){
10              max2=n;
11            }
12        }
13        
14        return (max1-1)*(max2-1); 
15    }
16}