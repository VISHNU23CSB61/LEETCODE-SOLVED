1class Solution {
2    public int gcd(int a,int b){
3        while(b!=0){
4            int temp=b;
5            b=a%b;
6            a=temp;
7        }return Math.abs(a);
8    }
9    public long gcdSum(int[] nums) {
10        int max=0;
11        for(int i=0;i<nums.length;i++){
12            max=Math.max(max,nums[i]);
13            nums[i]=gcd(nums[i],max);
14        }
15        Arrays.sort(nums);
16        long res=0;
17        for(int i=0,j=nums.length-1;i<j;i++,j--)
18           res+=gcd(nums[i],nums[j]);
19        return res;
20    }
21}