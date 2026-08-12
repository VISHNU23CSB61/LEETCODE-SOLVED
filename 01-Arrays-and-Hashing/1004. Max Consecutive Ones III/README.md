<h2><a href="https://leetcode.com/problems/max-consecutive-ones-iii">1004. Max Consecutive Ones III</a></h2>

<p>Given a binary array <code>nums</code> and an integer <code>k</code>, return <em>the maximum number of consecutive </em><code>1</code><em>'s in the array if you can flip at most</em> <code>k</code> <code>0</code>'s.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
<strong>Output:</strong> 6
<strong>Explanation:</strong> [1,1,1,0,0,<u><strong>1</strong>,1,1,1,1,<strong>1</strong></u>]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
<strong>Output:</strong> 10
<strong>Explanation:</strong> [0,0,<u>1,1,<strong>1</strong>,<strong>1</strong>,1,1,1,<strong>1</strong>,1,1</u>,0,0,0,1,1,1,1]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>nums[i]</code> is either 0 or 1.</li>
	<li><code>0 &lt;= k &lt;= nums.length</code></li>
</ul>


---

# 🛍️ Max-Consecutive-Ones-III | Explained

## Approach 1: Sliding Window with Frequency Map
### Intuition
Imagine you are walking along a track with a flashlight beam of flexible width (a window). The track contains `1`s and `0`s, and you have at most `k` "free pass" tokens to convert `0`s into `1`s. As long as the number of `0`s inside your light beam is at most `k`, you can expand the beam to the right to see how long a contiguous block of `1`s you can form. However, as soon as your beam covers more than `k` zeros, your budget is exceeded. To fix this, you must shrink the light beam from the left until the count of `0`s falls back within budget (`<= k`).

### Algorithm Visualized
```mermaid
flowchart TD
    A[Start: r = 0, l = 0, maxl = 0] --> B{r < nums.length?}
    B -- Yes --> C[Increment frequency of nums[r] in HashMap]
    C --> D{freq[0] > k ?}
    D -- Yes --> E[Decrement frequency of nums[l] in HashMap]
    E --> F[Move left pointer: l++]
    F --> D
    D -- No --> G[Update maxl = max(maxl, r - l + 1)]
    G --> H[Move right pointer: r++]
    H --> B
    B -- No --> I[Return maxl]
```

### Approach
1. **Maintain Window Boundaries**: Use two pointers (`l` for the left boundary, `r` for the right boundary) to define a variable-size contiguous subarray (sliding window).
2. **Frequency Tracking**: Use a Java `HashMap` named `freq` to count occurrences of numbers (specifically `0`s and `1`s) inside the current window.
3. **Window Expansion**: Iterate `r` from `0` to `nums.length - 1`, expanding the window by including `nums[r]` and incrementing its count using `Map.merge()`.
4. **Window Contraction**: If the number of `0`s (`freq.getOrDefault(0, 0)`) exceeds the allowed limit `k`, shrink the window from the left by decrementing the count of `nums[l]` and advancing `l` until `0`s count becomes $\le k$.
5. **Maximize Answer**: At each valid state, calculate the current window length (`r - l + 1`) and update `maxl`.

### Detailed Code Analysis
- **Lines 3–5**: `Map<Integer,Integer> freq=new HashMap<>(); int l=0; int maxl=0;`
  - Initializes the hash map to keep track of element frequencies within the active window.
  - Initializes `l` (left pointer of the sliding window) to `0` and `maxl` (stores maximum length found) to `0`.
- **Line 6**: `for(int r=0;r<nums.length;r++){`
  - Iterates through the array using `r` as the right boundary of the expanding window.
- **Line 7**: `freq.merge(nums[r],1,Integer::sum);`
  - Leverages Java 8's `Map.merge()` to increment the count of `nums[r]` by `1`. If `nums[r]` does not exist in the map yet, it inserts `(nums[r], 1)`.
- **Lines 8–11**: `while(freq.getOrDefault(0,0)>k){ freq.merge(nums[l],-1,Integer::sum); l++; }`
  - Inspects the zero count using `freq.getOrDefault(0,0)`.
  - If zeros inside the window exceed `k`, the window is invalid. The code contracts the window from the left by decrementing `nums[l]`'s count in the map and incrementing `l`.
- **Line 12**: `maxl=Math.max(maxl,r-l+1);`
  - Calculates the current window size `r - l + 1` and updates `maxl` if the current size is larger than the maximum recorded so far.
- **Line 13**: `return maxl;`
  - Returns the maximum window length recorded after processing all elements.

### Code
```java
class Solution {
    public int longestOnes(int[] nums, int k) {
        Map<Integer,Integer> freq=new HashMap<>();
        int l=0;
        int maxl=0;
        for(int r=0;r<nums.length;r++){
            freq.merge(nums[r],1,Integer::sum);
            while(freq.getOrDefault(0,0)>k){
                freq.merge(nums[l],-1,Integer::sum);
                l++;
            }
            maxl=Math.max(maxl,r-l+1);
        }return maxl;
        
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of `nums`. Even though there is a `while` loop inside the `for` loop, each pointer (`l` and `r`) moves from `0` to $N$ at most once. Hash map operations (`merge`, `getOrDefault`) operate in $\mathcal{O}(1)$ average time.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. The `HashMap` stores at most two distinct keys (`0` and `1`), consuming constant extra memory.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How can we optimize the space and execution overhead of this solution?
**Answer:** While the hash map takes $\mathcal{O}(1)$ space because there are only two keys (`0` and `1`), object boxing (`Integer`) and map lookups introduce overhead. Replacing the `HashMap` with a simple integer variable `zeroCount` avoids heap allocations and map hashing overhead altogether:
```java
int zeroCount = 0;
// Increment zeroCount when nums[r] == 0
// Decrement zeroCount when nums[l] == 0
```

### 2. Can we optimize the algorithm so the window never shrinks?
**Answer:** Yes. Instead of shrinking the window back to a valid size using a `while` loop, we can replace `while` with an `if` statement. If `zeroCount > k`, we increment `l` by 1 without shrinking further. This keeps the window size fixed at the maximum size found so far, allowing the answer to simply be `nums.length - l` at the end.