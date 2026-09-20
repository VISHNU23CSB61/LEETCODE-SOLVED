1class Solution {
2    public int reverseDegree(String s) {
3        int sum=0;
4        for(int i=0;i<s.length();i++){
5            char c=s.charAt(i);
6            int rev=26-(c-'a');
7            int ind=i+1;
8            sum+=rev*ind;
9        }return sum;
10    }
11}