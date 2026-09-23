1class Solution {
2    public String truncateSentence(String s, int k) {
3        int count=0;
4        for(int i=0;i<s.length();i++){
5            if(s.charAt(i)==' '){
6                count++;
7            }
8            if(count==k){
9                return s.substring(0,i);
10            }
11        }return s;
12    }
13}