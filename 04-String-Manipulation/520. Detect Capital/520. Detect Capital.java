1class Solution {
2    public boolean detectCapitalUse(String word) {
3        int count = 0;    
4        for (char x : word.toCharArray()) {
5            if (Character.isUpperCase(x)) {
6                count++;
7            }
8        }
9        if (count == word.length() || count == 0) {
10            return true;
11        }
12        return count == 1 && Character.isUpperCase(word.charAt(0));
13    }
14}