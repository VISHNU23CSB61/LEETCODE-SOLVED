<h2><a href="https://leetcode.com/problems/valid-parentheses">20. Valid Parentheses</a></h2>

<p>Given a string <code>s</code> containing just the characters <code>'('</code>, <code>')'</code>, <code>'{'</code>, <code>'}'</code>, <code>'['</code> and <code>']'</code>, determine if the input string is valid.</p>

<p>An input string is valid if:</p>

<ol>
	<li>Open brackets must be closed by the same type of brackets.</li>
	<li>Open brackets must be closed in the correct order.</li>
	<li>Every close bracket has a corresponding open bracket of the same type.</li>
</ol>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "()"</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "()[]{}"</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "(]"</span></p>

<p><strong>Output:</strong> <span class="example-io">false</span></p>
</div>

<p><strong class="example">Example 4:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "([])"</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>
</div>

<p><strong class="example">Example 5:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "([)]"</span></p>

<p><strong>Output:</strong> <span class="example-io">false</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 10<sup>4</sup></code></li>
	<li><code>s</code> consists of parentheses only <code>'()[]{}'</code>.</li>
</ul>


---

# 🛍️ Valid-Parentheses | Explained

## Approach 1: Stack-Based Bracket Matching (LIFO)

### Intuition
Think of parentheses matching like stacking cafeteria trays or nesting Russian Matryoshka dolls. When you open a container (an opening bracket like `(`, `[`, or `{`), any new container opened inside it must be completely sealed and put away before you can close the outer one. The most recently opened bracket is always the first one that needs to be closed. 

This strict "Last-In, First-Out" (LIFO) relationship makes a **Stack** the ideal data structure. As we scan the string from left to right, we store every opening bracket we encounter. When we run into a closing bracket, it must correspond to the bracket currently sitting at the very top of our stack. If it matches, we discard the pair and move on; if it doesn't match or the stack is already empty, the sequence is invalid.

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start: Iterate over characters]) --> ReadChar[Read character 'c']
    ReadChar --> CheckOpen{Is 'c' an opening bracket?<br>'(' or '[' or '{'}
    
    CheckOpen -- Yes --> PushStack[Push 'c' onto Stack]
    PushStack --> NextChar{More characters?}
    
    CheckOpen -- No --> CheckEmpty{Is Stack empty?}
    CheckEmpty -- Yes --> ReturnFalse1[Return false<br>Unmatched closing bracket]
    CheckEmpty -- No --> PopStack[Pop 'top' from Stack]
    
    PopStack --> CheckMatch{Does 'c' match 'top'?<br>')' with '('<br>']' with '['<br>'}' with '{'}
    CheckMatch -- No --> ReturnFalse2[Return false<br>Mismatched bracket pair]
    CheckMatch -- Yes --> NextChar
    
    NextChar -- Yes --> ReadChar
    NextChar -- No --> FinalCheck{Is Stack empty?}
    
    FinalCheck -- Yes --> ReturnTrue[Return true<br>All brackets balanced]
    FinalCheck -- No --> ReturnFalse3[Return false<br>Unclosed opening brackets]
```

### Approach
1. **Initialize Storage**: Create a stack of characters to hold opening brackets as they appear.
2. **Iterate Across String**: Convert the string to a character array and inspect each character `c` sequentially:
   - **Case 1: Opening Bracket**: If `c` is `'('`, `'['`, or `'{'`, push it onto the stack.
   - **Case 2: Closing Bracket**:
     - Check if the stack is empty. If it is empty, a closing bracket appeared without a preceding opening bracket, so return `false`.
     - Pop the top character `top` from the stack.
     - Validate that `c` correctly matches the opening type of `top`. If `c` is `')'` but `top` is not `'('` (or the equivalent for brackets/braces), return `false`.
3. **Final Validation**: After processing all characters, verify whether the stack is empty. If it is empty, all opening brackets were matched and closed in the correct order (`true`). If items remain, there are unclosed opening brackets (`false`).

### Detailed Code Analysis

```java
1class Solution {
2    public boolean isValid(String s) {
3        Stack<Character> s1=new Stack<>();
```
- **Line 3**: We instantiate `s1`, an object of `java.util.Stack<Character>`. This creates our LIFO container. While `Stack` inherits from `Vector` (which carries synchronization overhead), functionally it provides the `push`, `pop`, and `isEmpty` primitives required for bracket tracking.

```java
4        for(char c:s.toCharArray()){
```
- **Line 4**: `s.toCharArray()` extracts a contiguous `char[]` copy of string `s`. The enhanced `for` loop iterates through each character `c` from left to right.

```java
5            if(c=='('||c=='['||c=='{'){
6                s1.push(c);
```
- **Lines 5–6**: We check if `c` is one of the three valid opening characters. If true, we invoke `s1.push(c)`, placing it on top of the stack and postponing its resolution until its closing counterpart is encountered.

```java
7            }else{
8                if(s1.isEmpty()) return false;
```
- **Lines 7–8**: If `c` is not an opening bracket, it must be a closing bracket (given LeetCode's constraint that `s` consists of parentheses only). Before attempting to inspect the top of the stack, we call `s1.isEmpty()`. If the stack is empty, we have encountered an orphan closing bracket (e.g., `s = "]"`) which causes an immediate failure condition (`return false`).

```java
9                char top=s1.pop();
10               if(c==')' && top!='(' || c==']' && top!='[' ||c=='}' && top!='{')return false;
11           }
```
- **Line 9**: `s1.pop()` simultaneously removes and returns the most recently pushed opening bracket, storing it in the local variable `top`.
- **Line 10**: We evaluate matching correctness using logical operators. In Java, the logical AND (`&&`) has higher operator precedence than logical OR (`||`). The condition evaluates whether any mismatch occurs:
  - `c == ')'` but `top != '('`
  - `c == ']'` but `top != '['`
  - `c == '}'` but `top != '{'`
  If any of these conditions are true, the parentheses are interleaved incorrectly (e.g., `"(]"`) or out of order, and the function returns `false`.

```java
12        }return s1.isEmpty();
13       
14        
15    }
16}
```
- **Line 12**: After the loop finishes, we check `s1.isEmpty()`. It is not sufficient to merely reach the end of the string without an explicit mismatch; all opened brackets must be matched. If `s1` still contains elements (e.g., `s = "(("`), `s1.isEmpty()` evaluates to `false`. If completely cleared, it evaluates to `true`.

### Code
```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> s1 = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                s1.push(c);
            } else {
                if (s1.isEmpty()) return false;
                char top = s1.pop();
                if (c == ')' && top != '(' || c == ']' && top != '[' || c == '}' && top != '{') return false;
            }
        }
        return s1.isEmpty();
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of the string `s`.
  - `s.toCharArray()` traverses the string once to produce the array: $\mathcal{O}(n)$.
  - The `for` loop executes $n$ times.
  - Inside the loop, `push`, `pop`, and `isEmpty` operations on `java.util.Stack` run in amortized $\mathcal{O}(1)$ time.
  - Thus, the overall execution time scales linearly with the input size.

- **Space Complexity:** $\mathcal{O}(n)$.
  - `s.toCharArray()` allocates a character array of size $n$, contributing $\mathcal{O}(n)$ auxiliary heap space.
  - In the worst-case scenario (e.g., `s = "((((((("`), no brackets are popped, and the stack stores all $n$ characters, requiring $\mathcal{O}(n)$ space.
  - Total auxiliary space is $\mathcal{O}(n)$.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

### 1. Why is `java.util.ArrayDeque` generally preferred over `java.util.Stack` in production Java code?
`java.util.Stack` extends `java.util.Vector`, which uses method-level synchronization on operations like `push()` and `pop()`. This introduces unnecessary thread-synchronization overhead in single-threaded environments. Additionally, extending `Vector` violates strict stack semantics by allowing index-based access (e.g., `s1.get(0)`). Modern Java best practices recommend using `Deque<Character> stack = new ArrayDeque<>()`, which is unsynchronized, faster, and backed by a resizable cyclic array.

### 2. How can we optimize this solution to achieve $\mathcal{O}(1)$ auxiliary space beyond the stack?
Calling `s.toCharArray()` allocates an entirely new array of size $n$ on the heap. We can eliminate this allocation by querying the string directly via `s.charAt(i)` in a standard indexed `for` loop (`for (int i = 0; i < s.length(); i++)`). While the stack itself still requires up to $\mathcal{O}(n)$ memory in the worst case, eliminating `toCharArray()` avoids doubling the memory footprint.