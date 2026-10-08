# 0ms runtime
class Solution:
    def removeOuterParentheses(self, s: str) -> str:

        depth, ans, p_i = 0, [], []
        for b in s:
            if b == '(':
                depth += 1
            else:
                depth -= 1

            if depth == 0:
                ans.extend(p_i[1:])
                p_i.clear()
            else:
                p_i.append(b)

        return ''.join(ans)