<h2><a href="https://leetcode.com/problems/remove-outermost-parentheses">1021. Remove Outermost Parentheses</a></h2>

<p>A valid parentheses string is either empty <code>""</code>, <code>"(" + A + ")"</code>, or <code>A + B</code>, where <code>A</code> and <code>B</code> are valid parentheses strings, and <code>+</code> represents string concatenation.</p>

<ul>
	<li>For example, <code>""</code>, <code>"()"</code>, <code>"(())()"</code>, and <code>"(()(()))"</code> are all valid parentheses strings.</li>
</ul>

<p>A valid parentheses string <code>s</code> is primitive if it is nonempty, and there does not exist a way to split it into <code>s = A + B</code>, with <code>A</code> and <code>B</code> nonempty valid parentheses strings.</p>

<p>Given a valid parentheses string <code>s</code>, consider its primitive decomposition: <code>s = P<sub>1</sub> + P<sub>2</sub> + ... + P<sub>k</sub></code>, where <code>P<sub>i</sub></code> are primitive valid parentheses strings.</p>

<p>Return <code>s</code> <em>after removing the outermost parentheses of every primitive string in the primitive decomposition of </em><code>s</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> s = "(()())(())"
<strong>Output:</strong> "()()()"
<strong>Explanation:</strong> 
The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
After removing outer parentheses of each part, this is "()()" + "()" = "()()()".
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> s = "(()())(())(()(()))"
<strong>Output:</strong> "()()()()(())"
<strong>Explanation:</strong> 
The input string is "(()())(())(()(()))", with primitive decomposition "(()())" + "(())" + "(()(()))".
After removing outer parentheses of each part, this is "()()" + "()" + "()(())" = "()()()()(())".
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> s = "()()"
<strong>Output:</strong> ""
<strong>Explanation:</strong> 
The input string is "()()", with primitive decomposition "()" + "()".
After removing outer parentheses of each part, this is "" + "" = "".
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
	<li><code>s[i]</code> is either <code>'('</code> or <code>')'</code>.</li>
	<li><code>s</code> is a valid parentheses string.</li>
</ul>


---

# 🛍️ Remove-Outermost-Parentheses | Explained

## Approach 1: Depth Tracking with Primitive Buffer Slicing

### Intuition
Think of a valid parentheses string as a sequence of nested gift boxes shipped side-by-side. Each self-contained, valid set of parentheses represents one parcel (a primitive valid component). Your goal is to discard the exterior packaging (the outermost opening and closing parentheses) of each parcel and keep only the items inside.

Because valid parentheses have a strict hierarchical balance, we can monitor how deep we are inside a box using a counter (`depth`). Every time we open a lid (`'('`), the depth increases; every time we close one (`')'`), the depth decreases. When our depth reaches `0`, we have completely closed the current exterior box. By accumulating characters in a temporary container and leaving out the very first lid (`p_i[0]`) and the very last lid (the current character that dropped depth to `0`), we successfully isolate the contents.

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start Iteration over string s]) --> ReadChar[Read character b]
    ReadChar --> CheckBracket{Is b == '(' ?}
    CheckBracket -- Yes --> IncDepth[depth += 1]
    CheckBracket -- No --> DecDepth[depth -= 1]
    
    IncDepth --> CheckZero{Is depth == 0 ?}
    DecDepth --> CheckZero
    
    CheckZero -- No (Still inside primitive) --> AppendBuffer[p_i.append b]
    CheckZero -- Yes (Primitive boundary reached) --> SliceBuffer["ans.extend(p_i[1:])<br/>Discard outer '(' at index 0<br/>Do not append current outer ')'"]
    
    SliceBuffer --> ClearBuffer[p_i.clear]
    AppendBuffer --> HasMore{More characters?}
    ClearBuffer --> HasMore
    
    HasMore -- Yes --> ReadChar
    HasMore -- No --> JoinResult[Return ''.join ans]
```

### Approach
1. **Initialize State:**
   - Maintain an integer `depth` to track balance.
   - Maintain a list `ans` to store the final filtered characters.
   - Maintain a list `p_i` (primitive buffer) to accumulate characters belonging to the current primitive string.
2. **Iterate Through the Input String:**
   - Update `depth`: increment by `1` if `'('`, decrement by `1` if `')'`.
   - **Boundary Check (`depth == 0`):** 
     - When `depth` drops to `0`, we have just encountered the closing parenthesis of the current primitive block.
     - Crucially, this closing character has not yet been added to `p_i`. The opening parenthesis of this block sits at `p_i[0]`.
     - Slice `p_i[1:]` (which excludes the first character) and extend `ans` with these inner characters.
     - Clear `p_i` using `p_i.clear()` to prepare for the next primitive component.
   - **Internal Character (`depth != 0`):**
     - Append the character `b` to `p_i`.
3. **Assemble Final String:**
   - Join all characters in `ans` into a single string and return it.

### Detailed Code Analysis

```python
class Solution:
    def removeOuterParentheses(self, s: str) -> str:
        # depth: tracks nesting level
        # ans: accumulator for characters that survive the filtering
        # p_i: buffer holding characters of the current primitive valid block
        depth, ans, p_i = 0, [], []
```
Here, multiple assignment initializes the state variables in a concise line. Lists are used instead of immutable string concatenation (`+=`) to avoid $O(N^2)$ quadratic reallocation overhead in Python.

```python
        for b in s:
            if b == '(':
                depth += 1
            else:
                depth -= 1
```
The loop inspects each character `b` sequentially. By modifying `depth` before inspecting whether a primitive boundary has closed, `depth == 0` triggers precisely on the terminal `')'`.

```python
            if depth == 0:
                ans.extend(p_i[1:])
                p_i.clear()
            else:
                p_i.append(b)
```
- When `depth == 0`:
  - The current character `b` must be `')'`. Because it belongs to the outer layer, we do **not** append it to `p_i`.
  - The matching outermost `'('` was placed at `p_i[0]` when this primitive block started.
  - Slicing `p_i[1:]` extracts everything strictly between the outermost `'('` and `')'`.
  - `ans.extend(...)` efficiently adds all remaining internal characters in bulk to our result container.
  - `p_i.clear()` wipes the buffer in-place with $O(K)$ cleanup, where $K$ is the length of the primitive component.
- When `depth != 0`:
  - We are either entering or currently traversing the primitive block. The character `b` is placed into `p_i`.

```python
        return ''.join(ans)
```
Finally, `''.join(ans)` merges the collected list of characters into the output string in a single linear pass.

### Code
```python
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
```

### Complexity
- **Time:** $O(N)$, where $N$ is the length of the string `s`. Each character is visited once in the loop, appended to `p_i` at most once, sliced and appended to `ans` at most once, and finally joined in $O(N)$ time.
- **Space:** $O(N)$. The algorithm stores intermediate characters inside `p_i` (up to $O(N)$ in the worst case where the entire string is a single primitive block) and in `ans` (up to $N - 2$ characters).

## 🕵️‍♂️ Follow-up Questions

### 1. Can this problem be solved without the temporary buffer `p_i`?
Yes. Instead of buffering an entire primitive component and slicing off `p_i[0]`, we can evaluate the depth on the fly:
- For `'('`: if `depth > 0`, it is not an outermost bracket, so append it; then increment `depth`.
- For `')'`: decrement `depth` first; if `depth > 0`, it is not an outermost bracket, so append it.
This eliminates the auxiliary buffer `p_i` and the list-slicing operations completely, keeping space complexity bounded strictly by the output container.

### 2. How does `p_i.clear()` affect performance compared to reassigning `p_i = []`?
`p_i.clear()` modifies the existing list in-place by resetting its length and clearing internal pointers without deallocating and re-instantiating a brand-new list object in Python's memory manager. While both take amortized $O(1)$ relative to total traversal, mutating the existing list in-place reduces object allocation churn.