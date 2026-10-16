1class Solution {
2    HashMap<String, Boolean> dp = new HashMap<>();
3
4    public boolean isScramble(String s1, String s2) {
5        if (s1.length() != s2.length()) return false;
6        return solve(s1, s2);
7    }
8
9    boolean solve(String s1, String s2) {
10        // Base case: strings are identical
11        if (s1.equals(s2)) return true;
12
13        String key = s1 + # + s2;
14        if (dp.containsKey(key)) return dp.get(key);
15
16        int l = s1.length();
17
18        // Pruning: Character frequency check
19        int[] count = new int[26];
20        for (int i = 0; i < l; i++) {
21            count[s1.charAt(i) - 'a']++;
22            count[s2.charAt(i) - 'a']--;
23        }
24        for (int c : count) {
25            if (c != 0) {
26                dp.put(key, false);
27                return false;
28            }
29        }
30
31        // Try splitting at every possible index k
32        for (int k = 1; k < l; k++) {
33            // Case 1: No swap
34            if (solve(s1.substring(0, k), s2.substring(0, k)) &&
35                solve(s1.substring(k), s2.substring(k))) {
36                dp.put(key, true);
37                return true;
38            }
39
40            // Case 2: Swap the two parts
41            if (solve(s1.substring(0, k), s2.substring(l - k)) &&
42                solve(s1.substring(k), s2.substring(0, l - k))) {
43                dp.put(key, true);
44                return true;
45            }
46        }
47
48        dp.put(key, false);
49        return false;
50    }
51}