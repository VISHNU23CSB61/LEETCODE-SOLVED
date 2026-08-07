1class Solution {
2    public boolean isIsomorphic(String s, String t) {
3        if(s.length()!=t.length())return false;
4
5        Map<Character,Character> map1=new HashMap<>();
6        Map<Character,Character> map2=new HashMap<>();
7
8        for(int i=0;i<s.length();i++){
9            char ch1=s.charAt(i);
10            char ch2=t.charAt(i);
11
12            if(map1.containsKey(ch1)){
13                if(map1.get(ch1)!=ch2)
14                  return false;
15            }
16                else{
17                    map1.put(ch1,ch2);
18                }
19            
20             if(map2.containsKey(ch2)){
21                if(map2.get(ch2)!=ch1)
22                  return false;
23             }
24                else{
25                    map2.put(ch2,ch1);
26                }
27            
28            
29        }return true;
30        
31    }
32}