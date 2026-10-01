1class Solution {
2    public boolean isValid(String s) {
3        Stack<Character> s1=new Stack<>();
4        for(char c:s.toCharArray()){
5            if(c=='('||c=='['||c=='{'){
6                s1.push(c);
7            }else{
8                if(s1.isEmpty()) return false;
9                char top=s1.pop();
10                if(c==')' && top!='(' || c==']' && top!='[' ||c=='}' && top!='{')return false;
11            }
12        }return s1.isEmpty();
13       
14        
15    }
16}