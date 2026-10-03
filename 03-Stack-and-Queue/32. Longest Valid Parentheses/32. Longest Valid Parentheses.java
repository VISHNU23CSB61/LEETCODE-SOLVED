class Solution {
    fun isValid(s: String): Boolean {
        val stack = java.util.ArrayDeque<Char>()
        for (i in 0 until s.length) {
            if (s[i] == '(') {
                stack.push('(')
            } else if (stack.isNotEmpty() && stack.peek() == '(') {
                stack.pop()
            } else {
                return false
            }
        }
        return stack.isEmpty()
    }

    fun longestValidParentheses(s: String): Int {
        var maxlen = 0
        for (i in 0 until s.length) {
            var j = i + 2
            while (j <= s.length) {
                if (isValid(s.substring(i, j))) {
                    maxlen = max(maxlen, j - i)
                }
                j += 2
            }
        }
        return maxlen
    }
}