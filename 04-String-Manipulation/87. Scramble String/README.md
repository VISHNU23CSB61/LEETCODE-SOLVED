<h2><a href="https://leetcode.com/problems/scramble-string">87. Scramble String</a></h2>

<p>We can scramble a string s to get a string t using the following algorithm:</p>

<ol>
	<li>If the length of the string is 1, stop.</li>
	<li>If the length of the string is &gt; 1, do the following:
	<ul>
		<li>Split the string into two non-empty substrings at a random index, i.e., if the string is <code>s</code>, divide it to <code>x</code> and <code>y</code> where <code>s = x + y</code>.</li>
		<li><strong>Randomly</strong>&nbsp;decide to swap the two substrings or to keep them in the same order. i.e., after this step, <code>s</code> may become <code>s = x + y</code> or <code>s = y + x</code>.</li>
		<li>Apply step 1 recursively on each of the two substrings <code>x</code> and <code>y</code>.</li>
	</ul>
	</li>
</ol>

<p>Given two strings <code>s1</code> and <code>s2</code> of <strong>the same length</strong>, return <code>true</code> if <code>s2</code> is a scrambled string of <code>s1</code>, otherwise, return <code>false</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> s1 = "great", s2 = "rgeat"
<strong>Output:</strong> true
<strong>Explanation:</strong> One possible scenario applied on s1 is:
"great" --&gt; "gr/eat" // divide at random index.
"gr/eat" --&gt; "gr/eat" // random decision is not to swap the two substrings and keep them in order.
"gr/eat" --&gt; "g/r / e/at" // apply the same algorithm recursively on both substrings. divide at random index each of them.
"g/r / e/at" --&gt; "r/g / e/at" // random decision was to swap the first substring and to keep the second substring in the same order.
"r/g / e/at" --&gt; "r/g / e/ a/t" // again apply the algorithm recursively, divide "at" to "a/t".
"r/g / e/ a/t" --&gt; "r/g / e/ a/t" // random decision is to keep both substrings in the same order.
The algorithm stops now, and the result string is "rgeat" which is s2.
As one possible scenario led s1 to be scrambled to s2, we return true.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> s1 = "abcde", s2 = "caebd"
<strong>Output:</strong> false
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> s1 = "a", s2 = "a"
<strong>Output:</strong> true
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>s1.length == s2.length</code></li>
	<li><code>1 &lt;= s1.length &lt;= 30</code></li>
	<li><code>s1</code> and <code>s2</code> consist of lowercase English letters.</li>
</ul>


---

# 🛍️ Scramble-String | Explained

## Approach 1: Top-Down Dynamic Programming with Anagram Pruning

### Intuition
Think of the scramble process like a modular toy train. You can slice the train at any coupling into a front section and a back section. You then have two choices: leave them in their relative positions, or physically detach them and swap the front and back sections. You repeat this recursively for every carriage down to individual single-block cars.

To determine if `s1` can be scrambled into `s2`:
1. If the two strings are already identical, no further cuts or swaps are needed—it's immediately valid.
2. If the two strings don't even contain the exact same characters in the exact same quantities (i.e., they are not anagrams), no amount of cutting and swapping will ever transform one into the other. We can prune the entire search tree right there.
3. If they are anagrams, we test every possible pivot point $k$ (where $1 \le k < \text{length}$). At each pivot point $k$, `s1` is split into a prefix of length $k$ and a suffix of length $l - k$. There are only two ways this could match `s2`:
   - **No Swap:** The prefix of `s1` matches the prefix of `s2`, and the suffix of `s1` matches the suffix of `s2`.
   - **Swap:** The prefix of `s1` matches the suffix of `s2` (length $k$), and the suffix of `s1` matches the prefix of `s2` (length $l - k$).

Because smaller sub-segments are re-evaluated repeatedly across different split configurations, memoizing intermediate results prevents redundant re-computations.

---

### Algorithm Visualized

```mermaid
flowchart TD
    Start([solve s1, s2]) --> CheckIdentical{s1.equals s2?}
    CheckIdentical -- Yes --> ReturnTrue[Return true]
    CheckIdentical -- No --> CheckMemo{In dp cache?}
    
    CheckMemo -- Yes --> ReturnMemo[Return cached boolean]
    CheckMemo -- No --> CheckAnagram{Same character counts?}
    
    CheckAnagram -- No --> CacheFalse[Cache key: false] --> ReturnFalse[Return false]
    CheckAnagram -- Yes --> LoopSplit[Loop k from 1 to length - 1]
    
    LoopSplit --> Case1{Case 1: No Swap<br/>s1[0..k] == s2[0..k] &&<br/>s1[k..l] == s2[k..l]?}
    Case1 -- Yes --> CacheTrue[Cache key: true] --> ReturnTrueFinal[Return true]
    Case1 -- No --> Case2{Case 2: Swap<br/>s1[0..k] == s2[l-k..l] &&<br/>s1[k..l] == s2[0..l-k]?}
    
    Case2 -- Yes --> CacheTrue
    Case2 -- No --> NextK{More k values?}
    
    NextK -- Yes --> LoopSplit
    NextK -- No --> CacheFalseFinal[Cache key: false] --> ReturnFalseFinal[Return false]
```

---

### Approach

1. **Length Validation:** If lengths differ, immediately return `false`.
2. **Base Case:** If `s1.equals(s2)`, return `true`.
3. **Memoization Lookup:** Construct a cache key `s1 + "#" + s2`. If this exact pair has already been solved, return the cached result.
4. **Anagram / Frequency Pruning:** Count character frequencies in both `s1` and `s2` using a fixed-size array of size 26. If any character frequency differs, record `false` in the memo cache and return `false`. This single pruning check eliminates exponentially large subtrees.
5. **Brute-Force Pivot Enumeration:**
   - Iterate split point $k$ from $1$ to $l - 1$.
   - **Check Condition 1 (Unswapped):**
     - Left part: `s1[0...k]` with `s2[0...k]`
     - Right part: `s1[k...l]` with `s2[k...l]`
   - **Check Condition 2 (Swapped):**
     - Left part: `s1[0...k]` with `s2[l-k...l]`
     - Right part: `s1[k...l]` with `s2[0...l-k]`
   - If either condition yields `true`, memoize `true` for this pair and terminate early.
6. **Fallback:** If no pivot $k$ produces a valid scramble, memoize `false` and return `false`.

---

### Detailed Code Analysis

#### Method: `isScramble(String s1, String s2)`
- **Lines 4–7:**
  ```java
  public boolean isScramble(String s1, String s2) {
      if (s1.length() != s2.length()) return false;
      return solve(s1, s2);
  }
  ```
  Guards against mismatched input dimensions. Two strings cannot be scrambles of each other if their lengths differ.

---

#### Method: `solve(String s1, String s2)`

- **Line 11: Base Case**
  ```java
  if (s1.equals(s2)) return true;
  ```
  If `s1` equals `s2`, they are trivially valid scrambles (depth-0 scramble).

- **Lines 13–14: Memoization Retrieval**
  ```java
  String key = s1 + "#" + s2;
  if (dp.containsKey(key)) return dp.get(key);
  ```
  A unique delimiter `#` prevents collision between overlapping strings (e.g., `"a" + "bc"` vs `"ab" + "c"`). If the subproblem has already been evaluated, returns the result immediately in $O(1)$ amortized time.

- **Lines 18–29: Frequency Pruning**
  ```java
  int[] count = new int[26];
  for (int i = 0; i < l; i++) {
      count[s1.charAt(i) - 'a']++;
      count[s2.charAt(i) - 'a']--;
  }
  for (int c : count) {
      if (c != 0) {
          dp.put(key, false);
          return false;
      }
  }
  ```
  A frequency balance array is updated in a single pass of length $l$. If any entry remains non-zero, the strings do not share identical multiset character distributions. Memoizing this negative result avoids re-evaluating these strings if visited through another decomposition path.

- **Lines 31–46: Recursive Splitting & Swapping**
  ```java
  for (int k = 1; k < l; k++) {
      // Case 1: No swap
      if (solve(s1.substring(0, k), s2.substring(0, k)) &&
          solve(s1.substring(k), s2.substring(k))) {
          dp.put(key, true);
          return true;
      }

      // Case 2: Swap the two parts
      if (solve(s1.substring(0, k), s2.substring(l - k)) &&
          solve(s1.substring(k), s2.substring(0, l - k))) {
          dp.put(key, true);
          return true;
      }
  }
  ```
  - `k` represents the length of `s1`'s left child.
  - **No swap:** `s1`'s left child of length $k$ (`s1.substring(0, k)`) aligns with `s2`'s left child of length $k$ (`s2.substring(0, k)`). The remaining segments of length $l - k$ are also checked.
  - **Swap:** `s1`'s left child of length $k$ (`s1.substring(0, k)`) aligns with `s2`'s rightmost segment of length $k$ (`s2.substring(l - k)`). The right part of `s1` (`s1.substring(k)`) aligns with the leftmost segment of `s2` (`s2.substring(0, l - k)`).
  - Short-circuit evaluation (`&&`) ensures the second recursive branch is only evaluated if the first one returns `true`.

- **Lines 48–49: Exhaustion**
  ```java
  dp.put(key, false);
  return false;
  ```
  If no split point $k \in [1, l - 1]$ yields a valid match under either case, the state is recorded as unsolvable.

---

### Code

```java
class Solution {
    HashMap<String, Boolean> dp = new HashMap<>();

    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        return solve(s1, s2);
    }

    boolean solve(String s1, String s2) {
        // Base case: strings are identical
        if (s1.equals(s2)) return true;

        String key = s1 + "#" + s2;
        if (dp.containsKey(key)) return dp.get(key);

        int l = s1.length();

        // Pruning: Character frequency check
        int[] count = new int[26];
        for (int i = 0; i < l; i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) {
                dp.put(key, false);
                return false;
            }
        }

        // Try splitting at every possible index k
        for (int k = 1; k < l; k++) {
            // Case 1: No swap
            if (solve(s1.substring(0, k), s2.substring(0, k)) &&
                solve(s1.substring(k), s2.substring(k))) {
                dp.put(key, true);
                return true;
            }

            // Case 2: Swap the two parts
            if (solve(s1.substring(0, k), s2.substring(l - k)) &&
                solve(s1.substring(k), s2.substring(0, l - k))) {
                dp.put(key, true);
                return true;
            }
        }

        dp.put(key, false);
        return false;
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(n^4)$ in the worst-case memoized state space.
  - Substrings of `s1` and `s2` can be defined by their starting index and length. There are $\mathcal{O}(n^3)$ possible state configurations (start index in `s1`, start index in `s2`, and segment length $L$).
  - For each state, we iterate over $L - 1$ split positions ($\mathcal{O}(n)$).
  - Inside the loop, string operations (`substring`, hash calculation, string concatenation for `key`) take $\mathcal{O}(n)$ time.
  - The frequency check takes $\mathcal{O}(n)$ time to scan strings of length $L \le n$.
  - In practice, the character frequency pruning prunes the majority of recursive paths, resulting in runtime orders of magnitude lower than the theoretical worst-case bound.

- **Space Complexity:** $\mathcal{O}(n^4)$
  - The `dp` map stores keys formed by concatenating pairs of substrings of length $L \le n$. The total number of unique states is bounded by $\mathcal{O}(n^3)$, each key string occupying $\mathcal{O}(n)$ memory, leading to $\mathcal{O}(n^4)$ auxiliary heap space.
  - The recursion stack reaches a maximum depth of $\mathcal{O}(n)$, with each frame consuming $\mathcal{O}(n)$ temporary memory for substring allocations.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How would you eliminate string allocations to optimize memory and speed?
Instead of creating new `String` objects via `substring()` and string keys for the `HashMap`, use index-based DP. Because all subproblems are substrings of the original inputs, a state can be represented by three integers:
- `i`: starting index in `s1`
- `j`: starting index in `s2`
- `len`: length of the segment

This allows memoization using a 3D array `Boolean[][][] dp = new Boolean[n][n][n + 1]`, reducing lookup and storage overhead to $\mathcal{O}(1)$ per state and lowering memory consumption from $\mathcal{O}(n^4)$ to $\mathcal{O}(n^3)$.

### 2. How can this approach be adapted for full Unicode support?
The `int[26]` frequency array assumes lowercase English letters (`'a'` through `'z'`). For full Unicode, replace the array with a `HashMap<Integer, Integer>` to track code points, or an `int[128]` / `int[256]` array if inputs are restricted to standard ASCII / Extended ASCII.