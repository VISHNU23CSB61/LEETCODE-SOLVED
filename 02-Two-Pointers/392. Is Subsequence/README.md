<h2><a href="https://leetcode.com/problems/is-subsequence">392. Is Subsequence</a></h2>

<p>Given two strings <code>s</code> and <code>t</code>, return <code>true</code><em> if </em><code>s</code><em> is a <strong>subsequence</strong> of </em><code>t</code><em>, or </em><code>false</code><em> otherwise</em>.</p>

<p>A <strong>subsequence</strong> of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., <code>"ace"</code> is a subsequence of <code>"<u>a</u>b<u>c</u>d<u>e</u>"</code> while <code>"aec"</code> is not).</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> s = "abc", t = "ahbgdc"
<strong>Output:</strong> true
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> s = "axc", t = "ahbgdc"
<strong>Output:</strong> false
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>0 &lt;= s.length &lt;= 100</code></li>
	<li><code>0 &lt;= t.length &lt;= 10<sup>4</sup></code></li>
	<li><code>s</code> and <code>t</code> consist only of lowercase English letters.</li>
</ul>

<p>&nbsp;</p>
<strong>Follow up:</strong> Suppose there are lots of incoming <code>s</code>, say <code>s<sub>1</sub>, s<sub>2</sub>, ..., s<sub>k</sub></code> where <code>k &gt;= 10<sup>9</sup></code>, and you want to check one by one to see if <code>t</code> has its subsequence. In this scenario, how would you change your code?

---

# 🛍️ Is-Subsequence | Explained

## Approach 1: Nested Character Matching with String Accumulation
### Intuition
The core idea behind this code is to iterate through every character in string `t` and check if that character exists anywhere within string `s`. If a match is found in `s`, the character from `t` is appended to a temporary collector string (`abc`). Finally, the solution checks whether the constructed string `abc` is equal to `s` or contains `s` as a substring.

To conceptualize this, imagine filtering through a stack of physical letters (`t`). For every letter you pick up, you check against a master key (`s`). If the letter is anywhere on the key, you put it into a new pile (`abc`). At the end, you check if your accumulated pile matches the master key in order.

> **Engineer's Note on Correctness:** While this approach attempts to solve the problem by picking matching characters, searching `s` from index `0` on every iteration of `t` resets the pointer for `s`. This causes characters in `t` to match out-of-order or duplicate prematurely, making this implementation logically flawed for general edge cases (such as handling duplicates or maintaining relative order strictness across all inputs).

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start: isSubsequence s, t]) --> Init[Initialize abc = '']
    Init --> OuterLoop{For each index i in t}
    OuterLoop -- i < len(t) --> InnerLoop{For each index j in s}
    OuterLoop -- i >= len(t) --> CheckResult{abc == s OR abc.find s > 0}

    InnerLoop -- j < len(s) --> MatchCheck{Does s[j] == t[i]?}
    InnerLoop -- j >= len(s) --> NextT[Next i in t]

    MatchCheck -- Yes --> Append[Append t[i] to abc]
    MatchCheck -- No --> NextS[Next j in s]

    Append --> BreakInner[Break Inner Loop]
    BreakInner --> NextT
    NextS --> InnerLoop

    CheckResult -- True --> ReturnTrue[Return True]
    CheckResult -- False --> ReturnFalse[Return False]
```

### Approach
1. Initialize an empty string accumulator `abc` to store matched characters.
2. Iterate through string `t` index by index (`i`).
3. For every character `t[i]`, run an inner loop through string `s` from start to end (`j`).
4. As soon as `s[j]` matches `t[i]`, append `t[i]` to `abc` and terminate the inner loop via `break`.
5. After scanning all characters in `t`, compare `abc` against `s`:
   - Check if `abc` is identical to `s`.
   - Check if `s` appears inside `abc` starting at an index greater than 0 (`abc.find(s) > 0`).
6. Return `True` if either condition holds; otherwise, return `False`.

### Detailed Code Analysis

#### 1. Variable Initialization
```python
abc = ""
```
*Note: Corrected `abc=` syntax error from the submission.* We initialize `abc` as an empty string. This variable acts as a buffer to build up candidate characters from `t`.

#### 2. Nested Iteration & Character Extraction
```python
for i in range(len(t)):
    for j in range(len(s)):
        if s[j] == t[i]:
            abc += t[i]
            break
```
- **Outer Loop (`range(len(t))`):** Scans input string `t` sequentially.
- **Inner Loop (`range(len(s))`):** Re-scans input string `s` from index `0` up to `len(s)-1` for **every** character `t[i]`.
- **Conditional Match (`s[j] == t[i]`):** If `t[i]` is present in `s`, it appends `t[i]` to `abc` and breaks immediately so that a single `t[i]` adds at most one character to `abc`.

#### 3. Subsequence Validation Check
```python
if abc == s or abc.find(s) > 0:
    return True
else:
    return False
```
- `abc == s`: Checks if the constructed string perfectly matches target sequence `s`.
- `abc.find(s) > 0`: Checks if string `s` is a substring of `abc` starting at index `1` or later.
- If either condition is met, `True` is returned; otherwise, it returns `False`.

### Code

```python
class Solution(object):
    def isSubsequence(self, s, t):
        # Fix: Initialized variable cleanly as empty string
        abc = ""
        
        # Method finds characters of s in t, and appends them to abc
        for i in range(len(t)):
            for j in range(len(s)):
                if s[j] == t[i]:
                    abc += t[i]
                    break
                    
        # Check if accumulated string matches s or contains s
        if abc == s or abc.find(s) > 0:
            return True
        else:
            return False
```

### Complexity
- **Time Complexity:** $\mathcal{O}(|t| \times |s| + |abc| \times |s|)$
  - Outer loop runs $|t|$ times, inner loop runs up to $|s|$ times, yielding $\mathcal{O}(|t| \times |s|)$ string comparisons.
  - String concatenation `abc += t[i]` creates a new string of length up to $|t|$ each time in Python, adding additional overhead.
  - `abc.find(s)` runs in $\mathcal{O}(|abc| \times |s|)$ worst-case time.
- **Space Complexity:** $\mathcal{O}(|t|)$
  - The string `abc` can store up to $|t|$ characters in memory during execution.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How can this problem be solved optimally in $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ auxiliary space?
**Answer:**
By using a **Two-Pointer Approach**. We place one pointer `p_s` at the start of `s` and another pointer `p_t` at the start of `t`. We advance `p_t` on every iteration, and only advance `p_s` when `s[p_s] == t[p_t]`. If `p_s` reaches `len(s)`, all characters in `s` were matched in order.

```python
def isSubsequenceOptimal(s: str, t: str) -> bool:
    p_s = p_t = 0
    while p_s < len(s) and p_t < len(t):
        if s[p_s] == t[p_t]:
            p_s += 1
        p_t += 1
    return p_s == len(s)
```
- **Time Complexity:** $\mathcal{O}(|t|)$
- **Space Complexity:** $\mathcal{O}(1)$

### 2. If there are $k$ incoming strings $S_1, S_2, \dots, S_k$ where $k \ge 10^9$, and we want to check if each $S_i$ is a subsequence of a fixed string $T$, how should we optimize?
**Answer:**
Repeatedly scanning $T$ takes $\mathcal{O}(k \cdot |T|)$, which is too slow. Instead, preprocess $T$ into an **Index Map / Hash Table**:
1. Store the indices of each character in $T$ using a hash map: `map[char] -> list of sorted indices`.
2. For each query string $S_i$, iterate through its characters. Use **Binary Search** (`bisect_right`) on the index list of the current character to find the smallest valid index in $T$ greater than the previous matched index.
- **Preprocessing Time:** $\mathcal{O}(|T|)$
- **Query Time per $S_i$:** $\mathcal{O}(|S_i| \log |T|)$