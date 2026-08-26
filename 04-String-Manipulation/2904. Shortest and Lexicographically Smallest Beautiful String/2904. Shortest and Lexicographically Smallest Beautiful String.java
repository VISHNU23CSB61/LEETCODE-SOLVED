1class Solution {
2    public String shortestBeautifulSubstring(String s, int k) {
3        String ans = ;
4        int n = s.length();
5
6        for (int i = 0; i < n; i++) {
7
8            int oneCnt = 0;
9            StringBuilder cur = new StringBuilder();
10
11            for (int j = i; j < n; j++) {
12
13                cur.append(s.charAt(j));
14
15                if (s.charAt(j) == '1')
16                    oneCnt++;
17
18                // More than k ones can never become valid again
19                if (oneCnt > k)
20                    break;
21
22                if (oneCnt == k) {
23                    String curStr = cur.toString();
24
25                    if (ans.isEmpty() ||
26                        curStr.length() < ans.length() ||
27                        (curStr.length() == ans.length() && curStr.compareTo(ans) < 0)) {
28
29                        ans = curStr;
30                    }
31                }
32            }
33        }
34
35        return ans;
36    }
37}