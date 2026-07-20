1class Solution {
2    public String decodeString(String s) {
3        Stack<Integer> count=new Stack<>();
4        Stack<String> str=new Stack<>();
5        String curr=;
6        int num=0;
7        for(char ch:s.toCharArray()){
8            if(Character.isDigit(ch)){
9                num=num*10+(ch-'0');
10            }else if(ch=='['){
11                count.push(num);
12                str.push(curr);
13                curr=;
14                num=0;
15            }else if(ch==']'){
16                int repeat=count.pop();
17                String prev=str.pop();
18                StringBuilder temp=new StringBuilder(prev);
19                for(int i=0;i<repeat;i++){
20                    temp.append(curr);
21                }curr=temp.toString();
22            }else{
23                curr+=ch;
24            }
25        }return curr;
26    }
27}