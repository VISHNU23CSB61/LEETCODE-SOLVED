<h2><a href="https://leetcode.com/problems/length-of-longest-subarray-with-at-most-k-frequency">2958. Length of Longest Subarray With at Most K Frequency</a></h2>

<p>You are given an integer array <code>nums</code> and an integer <code>k</code>.</p>

<p>The <strong>frequency</strong> of an element <code>x</code> is the number of times it occurs in an array.</p>

<p>An array is called <strong>good</strong> if the frequency of each element in this array is <strong>less than or equal</strong> to <code>k</code>.</p>

<p>Return <em>the length of the <strong>longest</strong> <strong>good</strong> subarray of</em> <code>nums</code><em>.</em></p>

<p>A <strong>subarray</strong> is a contiguous non-empty sequence of elements within an array.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,2,3,1,2,3,1,2], k = 2
<strong>Output:</strong> 6
<strong>Explanation:</strong> The longest possible good subarray is [1,2,3,1,2,3] since the values 1, 2, and 3 occur at most twice in this subarray. Note that the subarrays [2,3,1,2,3,1] and [3,1,2,3,1,2] are also good.
It can be shown that there are no good subarrays with length more than 6.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [1,2,1,2,1,2,1,2], k = 1
<strong>Output:</strong> 2
<strong>Explanation:</strong> The longest possible good subarray is [1,2] since the values 1 and 2 occur at most once in this subarray. Note that the subarray [2,1] is also good.
It can be shown that there are no good subarrays with length more than 2.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> nums = [5,5,5,5,5,5,5], k = 4
<strong>Output:</strong> 4
<strong>Explanation:</strong> The longest possible good subarray is [5,5,5,5] since the value 5 occurs 4 times in this subarray.
It can be shown that there are no good subarrays with length more than 4.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= k &lt;= nums.length</code></li>
</ul>


---

# 🛍️ Length-of-Longest-Subarray-With-at-Most-K-Frequency | Explained

## Approach 1: Sliding Window with Frequency Map

### Intuition
Think of a night club with a strict security guard enforcing a capacity rule: no specific group or type of guest (a number in `nums`) can have more than `k` members inside the dance floor (the sliding window) at any given time. 

As guests queue up from left to right, the right border (`right`) invites guests in one by one while keeping count using a tally counter (`HashMap`). Everything runs smoothly until a guest arrives whose group count exceeds `k`. To resolve this violation, the guard pauses entry and starts escorting guests out from the exit door on the far left (`left`) in sequential order until the over-represented group's count drops back to `k`. 

Because both the entrance (`right`) and exit (`left`) move monotonically forward across the array without ever rewinding, we efficiently explore all potential valid window sizes in linear time.

### Algorithm Visualized

```mermaid
graph TD
    A[Start iteration at index right] --> B{Is right == nums.length?}
    B -- Yes --> C[Update max = max max, right - left]
    C --> D[Terminate Loop]
    
    B -- No --> E[Get num = nums[right]]
    E --> F[Increment map count for num]
    F --> G{Is fre <= k?}
    
    G -- Yes --> H[Window valid: move right pointer]
    
    G -- No --> I[Window invalid: update max = max max, right - left]
    I --> J[Loop left pointer forward until nums[left] == num]
    J --> K[Decrement frequency of evicted elements in map]
    K --> L[Advance left past matching element left++]
    L --> M[Decrement frequency of num: set to fre - 1]
    M --> H
```

### Approach

1. **Initialize State**: Maintain a `left` pointer for the window start, a `max` variable to record the maximum valid subarray length found so far, and a `HashMap<Integer, Integer>` to track element frequencies inside the active window.
2. **Expand Window (`right` pointer)**: Loop through the array using `right` as the leading index.
3. **Handle Terminal Boundary**: When `right` reaches `nums.length`, compute the final window length (`right - left`), update `max`, and terminate.
4. **Frequency Accounting**: Increment the count of `nums[right]` in the frequency map.
5. **Check Validity**:
   - If the updated frequency of `nums[right]` is $\le k$, the window remains valid. Continue expanding `right`.
   - If the updated frequency exceeds $k$, the window becomes invalid.
6. **Shrink Window (`left` pointer)**:
   - Before shrinking, record `right - left` into `max` (since the window prior to inserting `nums[right]` was valid).
   - Advance `left` forward until it hits the first occurrence of `nums[right]` in the current window. Decrement the map frequency for all elements evicted along the way.
   - Advance `left` one step further to exclude that first occurrence.
   - Manually decrement the frequency of `nums[right]` in the map to reflect its reduced count ($fre - 1$).

---

### Detailed Code Analysis

```java
class Solution {

    public int maxSubarrayLength(int[] nums, int k) {
        // Frequency counter mapping each unique number to its frequency in the current window [left, right]
        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int max = 0;
        int right = 0;
        
        // Loop condition goes up to nums.length to cleanly handle boundary checking
        for(; right <= nums.length; right++){

            // Terminal check: when right moves past the last index,
            // process the remaining valid window length and exit.
            if(right == nums.length){
                max = Math.max(max, right - left);
                continue;
            }

            int num = nums[right];
            // Update frequency count for the current right element
            map.put(num, map.getOrDefault(num, 0) + 1);

            int fre = map.get(num);

            // Fast path: if the added element does not violate the <= k condition, continue expanding
            if(fre <= k) continue;

            // Frequency constraint violated (fre > k):
            // The window [left, right - 1] was valid before adding nums[right].
            max = Math.max(max, right - left);

            // Evict elements from left until we find the first occurrence of `num`
            while(nums[left] != num){
                map.put(nums[left], map.get(nums[left]) - 1);
                left++;
            }
            
            // Increment left once more to skip over the matching `num`
            left++;
            
            // Since we skipped one occurrence of `num`, manually set its frequency to fre - 1
            map.put(num, fre - 1);
        }
        
        return max;
    }
}
```

---

### Code

```java
class Solution {

    public int maxSubarrayLength(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int max = 0;
        int right = 0;
        for(; right <= nums.length; right++){

            if(right == nums.length){
                max = Math.max(max, right - left);
                continue;
            }

            int num = nums[right];
            map.put(num, map.getOrDefault(num,0)+1);

            int fre = map.get(num);

            if(fre <= k) continue;

            max = Math.max(max, right - left);

            while(nums[left] != num){
                map.put(nums[left], map.get(nums[left])-1);
                left++;
            }
            left++;
            map.put(num, fre-1);
        }
        
        return max;
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of the array `nums`.
  - Both the `right` and `left` pointers start at `0` and traverse to $N$ at most once.
  - Even though there is a nested `while` loop, `left` is only incremented; it never resets or backtracks. Thus, each element is inserted into the hash map once and removed at most once.
  - HashMap lookups and updates run in amortized $\mathcal{O}(1)$ time.

- **Space Complexity:** $\mathcal{O}(N)$ worst-case.
  - The `HashMap` stores frequencies for distinct elements present in the active sliding window. In the worst-case scenario where all elements in `nums` are unique, the map will store up to $N$ key-value pairs.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How would you optimize the space overhead and garbage collection pressure in a real-time system?
**Answer:** If the element range of `nums` is known and bounded (for example, $1 \le nums[i] \le 10^5$), we can replace the heavy `HashMap<Integer, Integer>` with a primitive integer array `int[] counts = new int[MAX_VAL + 1]`. This eliminates object boxing/unboxing (`Integer`), avoids hash collision overhead, guarantees true $\mathcal{O}(1)$ random access, and drastically reduces cache misses and GC allocations.

### 2. Can we eliminate the inner `while` loop to shrink the window even faster?
**Answer:** Yes. Instead of storing just frequency integers in the hash map, we could map each number to a queue (`Queue<Integer>`) or dynamic array of its indices. When the queue size exceeds `k`, we can directly jump the `left` pointer to `map.get(num).poll() + 1`. While the asymptotic complexity remains $\mathcal{O}(N)$, skipping pointer increments in bulk reduces CPU instructions during window shrinking.