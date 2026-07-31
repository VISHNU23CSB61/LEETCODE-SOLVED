<h2><a href="https://leetcode.com/problems/minimum-number-of-pushes-to-type-word-ii">3016. Minimum Number of Pushes to Type Word II</a></h2>

<p>You are given a string <code>word</code> containing lowercase English letters.</p>

<p>Telephone keypads have keys mapped with <strong>distinct</strong> collections of lowercase English letters, which can be used to form words by pushing them. For example, the key <code>2</code> is mapped with <code>["a","b","c"]</code>, we need to push the key one time to type <code>"a"</code>, two times to type <code>"b"</code>, and three times to type <code>"c"</code> <em>.</em></p>

<p>It is allowed to remap the keys numbered <code>2</code> to <code>9</code> to <strong>distinct</strong> collections of letters. The keys can be remapped to <strong>any</strong> amount of letters, but each letter <strong>must</strong> be mapped to <strong>exactly</strong> one key. You need to find the <strong>minimum</strong> number of times the keys will be pushed to type the string <code>word</code>.</p>

<p>Return <em>the <strong>minimum</strong> number of pushes needed to type </em><code>word</code> <em>after remapping the keys</em>.</p>

<p>An example mapping of letters to keys on a telephone keypad is given below. Note that <code>1</code>, <code>*</code>, <code>#</code>, and <code>0</code> do <strong>not</strong> map to any letters.</p>
<img alt="" src="https://assets.leetcode.com/uploads/2023/12/26/keypaddesc.png" style="width: 329px; height: 313px;">
<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2023/12/26/keypadv1e1.png" style="width: 329px; height: 313px;">
<pre><strong>Input:</strong> word = "abcde"
<strong>Output:</strong> 5
<strong>Explanation:</strong> The remapped keypad given in the image provides the minimum cost.
"a" -&gt; one push on key 2
"b" -&gt; one push on key 3
"c" -&gt; one push on key 4
"d" -&gt; one push on key 5
"e" -&gt; one push on key 6
Total cost is 1 + 1 + 1 + 1 + 1 = 5.
It can be shown that no other mapping can provide a lower cost.
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2024/08/20/edited.png" style="width: 329px; height: 313px;">
<pre><strong>Input:</strong> word = "xyzxyzxyzxyz"
<strong>Output:</strong> 12
<strong>Explanation:</strong> The remapped keypad given in the image provides the minimum cost.
"x" -&gt; one push on key 2
"y" -&gt; one push on key 3
"z" -&gt; one push on key 4
Total cost is 1 * 4 + 1 * 4 + 1 * 4 = 12
It can be shown that no other mapping can provide a lower cost.
Note that the key 9 is not mapped to any letter: it is not necessary to map letters to every key, but to map all the letters.
</pre>

<p><strong class="example">Example 3:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2023/12/27/keypadv2.png" style="width: 329px; height: 313px;">
<pre><strong>Input:</strong> word = "aabbccddeeffgghhiiiiii"
<strong>Output:</strong> 24
<strong>Explanation:</strong> The remapped keypad given in the image provides the minimum cost.
"a" -&gt; one push on key 2
"b" -&gt; one push on key 3
"c" -&gt; one push on key 4
"d" -&gt; one push on key 5
"e" -&gt; one push on key 6
"f" -&gt; one push on key 7
"g" -&gt; one push on key 8
"h" -&gt; two pushes on key 9
"i" -&gt; one push on key 9
Total cost is 1 * 2 + 1 * 2 + 1 * 2 + 1 * 2 + 1 * 2 + 1 * 2 + 1 * 2 + 2 * 2 + 6 * 1 = 24.
It can be shown that no other mapping can provide a lower cost.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= word.length &lt;= 10<sup>5</sup></code></li>
	<li><code>word</code> consists of lowercase English letters.</li>
</ul>


---

# 🛍️ Minimum-Number-of-Pushes-to-Type-Word-II | Explained

## Approach 1: Greedy Mapping with Frequency Sorting

### Intuition
Imagine you are re-designing a phone keypad (keys 2 through 9, giving 8 usable keys) to minimize the effort required to type a long message. To minimize the total number of key presses, you should place the letters that appear most frequently in the message on the first position of the available keys, requiring only 1 push per character. 

Once all 8 keys have 1 character assigned to their first position (total 8 characters assigned), the next 8 most frequent characters should be assigned to the second position of those keys (requiring 2 pushes). The process continues for the third position (3 pushes) and fourth position (4 pushes). 

This is a classic **Greedy approach**: prioritize mapping characters with higher frequencies to key slots with lower push costs.

---

### Algorithm Visualized

```mermaid
flowchart TD
    A[Start: Input word] --> B[Count Character Frequencies]
    B --> C[Sort Frequency Array Ascending]
    C --> D[Iterate from highest frequency index i = 25 down to 0]
    D --> E{freq[i] > 0?}
    E -- No --> H[Return total pushes ans]
    E -- Yes --> F["Calculate push cost multiplier = ((25 - i) / 8 + 1)"]
    F --> G["Add freq[i] * multiplier to ans"]
    G --> D
```

---

### Approach

1. **Frequency Counting:** Iterate through the string `word` and record the occurrence count of each lowercase letter in a fixed-size integer array of size 26.
2. **Sorting:** Sort the frequency array in ascending order using `Arrays.sort()`. After sorting, the most frequent characters will be at the highest indices (ending at index 25).
3. **Greedy Assignment & Accumulation:**
   - Iterate backwards from index `25` down to `0` (or until frequency is `0`).
   - For the $k$-th most frequent character (where $k = 25 - i$ starting at 0):
     - Compute the required number of pushes per character press: `(k / 8) + 1`.
     - The first 8 most frequent characters ($k = 0 \dots 7$) cost 1 push.
     - The next 8 characters ($k = 8 \dots 15$) cost 2 pushes, and so on.
   - Multiply the character's frequency by its calculated push cost and add it to the total sum `ans`.
4. **Return:** Return the accumulated total push count.

---

### Detailed Code Analysis

- **`int[] freq = new int[26];`**
  Allocates a fixed-size array of 26 integers to store counts for each letter from `'a'` to `'z'`. Selecting a fixed-size array provides $O(1)$ auxiliary space and efficient constant-time indexing.
  
- **`for (char c : word.toCharArray()) freq[c - 'a']++;`**
  Converts the input string into a character array and increments the corresponding bucket by subtracting the ASCII value of `'a'`. This populates character frequencies in linear time relative to the length of `word`.

- **`Arrays.sort(freq);`**
  Sorts the 26-element array in non-decreasing order. Because the length is fixed at 26, this step executes in $O(1)$ constant time. After execution, `freq[25]` holds the maximum frequency and `freq[0]` holds the minimum.

- **`for (int i = 25; i >= 0 && freq[i] > 0; i--)`**
  Loops backwards from index 25 to access frequencies in descending order. The loop terminates early if `freq[i] == 0`, avoiding redundant work for characters that do not appear in the input string.

- **`ans += freq[i] * ((25 - i) / 8 + 1);`**
  Calculates the push contribution for character `i`:
  - `(25 - i)` gives the zero-based rank of the current character (0 for most frequent, 1 for second most frequent, etc.).
  - Integer division `(25 - i) / 8` groups the characters into blocks of 8 (0 for ranks 0-7, 1 for ranks 8-15, etc.).
  - Adding `1` shifts the multiplier to represent the actual push count required (1 push, 2 pushes, 3 pushes, or 4 pushes).
  - Multiplying this by `freq[i]` yields total pushes for that letter, accumulated into `ans`.

---

### Code

```java
class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];
        for (char c : word.toCharArray()) 
            freq[c - 'a']++;
        
        Arrays.sort(freq);
        int ans = 0;
        for (int i = 25; i >= 0 && freq[i] > 0; i--) 
            ans += freq[i] * ((25 - i) / 8 + 1);
        
        return ans;
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(N)$
  - Counting character frequencies requires iterating over the string of length $N$, which takes $\mathcal{O}(N)$ time.
  - Sorting an array of fixed size 26 takes $\mathcal{O}(26 \log 26) = \mathcal{O}(1)$ time.
  - The final loop runs at most 26 times, taking $\mathcal{O}(1)$ time.
  - Total Time Complexity: $\mathcal{O}(N)$.

- **Space Complexity:** $\mathcal{O}(1)$
  - The frequency array `freq` has a constant size of 26 regardless of input size $N$.
  - Additional variables (`ans`, `i`) occupy $\mathcal{O}(1)$ auxiliary space.
  - Total Space Complexity: $\mathcal{O}(1)$.

---

## 🕵️‍♂️ Follow-up Questions

### 1. What if the keypad had $K$ available keys instead of 8?
**Answer:** The algorithm generalizes easily. Instead of dividing `(25 - i)` by `8`, you would divide by `K`. The cost formula becomes `((25 - i) / K + 1)`. The overall time complexity remains $\mathcal{O}(N)$ and space complexity remains $\mathcal{O}(1)$ assuming an alphabet size of 26.

### 2. Can we solve this problem without full sorting?
**Answer:** Yes, using a Max-Heap (PriorityQueue) or by performing a bucket sort on the frequency counts. However, since the alphabet size is constrained to 26 elements, using `Arrays.sort()` on a 26-element array is already $\mathcal{O}(1)$ in time and space, making simple sorting optimal and practical in Java.