<h2><a href="https://leetcode.com/problems/contains-duplicate-ii">219. Contains Duplicate II</a></h2>

<p>Given an integer array <code>nums</code> and an integer <code>k</code>, return <code>true</code> <em>if there are two <strong>distinct indices</strong> </em><code>i</code><em> and </em><code>j</code><em> in the array such that </em><code>nums[i] == nums[j]</code><em> and </em><code>abs(i - j) &lt;= k</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,2,3,1], k = 3
<strong>Output:</strong> true
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [1,0,1,1], k = 1
<strong>Output:</strong> true
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> nums = [1,2,3,1,2,3], k = 2
<strong>Output:</strong> false
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>9</sup> &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>0 &lt;= k &lt;= 10<sup>5</sup></code></li>
</ul>


---

# 🛍️ Contains-Duplicate-II | Explained

## Approach 1: Sliding Window with HashSet
### Intuition
Imagine you are walking down a conveyer belt of items looking at a moving window of size $k$. You only care if the current item matches any of the items currently visible inside your window. As you move one step forward, a new item enters your field of view, and the oldest item that is now further than $k$ steps away leaves your field of view. 

By keeping a dynamic set of elements representing this sliding window of size $k$, we can check for duplicate occurrences in $O(1)$ average time per element.

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start Loop at index i]) --> CheckWindow{i > k ?}
    CheckWindow -- Yes --> RemoveOld[Remove nums[i - k - 1] from Set]
    CheckWindow -- No --> AddElement
    RemoveOld --> AddElement{Try set.add nums[i]}
    AddElement -- False duplicate exists --> ReturnTrue([Return true])
    AddElement -- True element added --> Continue[Continue loop]
    Continue --> CheckEnd{i < nums.length - 1 ?}
    CheckEnd -- Yes --> Start
    CheckEnd -- No --> ReturnFalse([Return false])
```

### Approach
1. Initialize an empty hash set to maintain elements within the sliding window of size $k$.
2. Iterate through the array using index `i`.
3. Before processing `nums[i]`, check if `i > k`. If so, remove the element that has fallen outside the window of length $k$, which is located at `nums[i - k - 1]`.
4. Attempt to insert `nums[i]` into the set using `set.add(nums[i])`.
   - If `add` returns `false`, it means `nums[i]` already exists in the current sliding window. Return `true` immediately.
5. If the loop completes without finding any duplicate within distance $k$, return `false`.

### Detailed Code Analysis
- `Set<Integer> set = new HashSet<>();`: A Java `HashSet` provides $O(1)$ expected time complexity for `add`, `remove`, and lookup operations, making it ideal for maintaining the active window elements.
- `for (int i = 0; i < nums.length; i++)`: Iterates through each number in the array sequentially.
- `if (i > k) { set.remove(nums[i - k - 1]); }`: Ensures the set size never exceeds $k$. When index `i` reaches $k + 1$, the element at index $0$ (`nums[i - k - 1]`) is no longer within distance $k$ of the current index `i` and is removed.
- `if (!set.add(nums[i])) { return true; }`: The `Set.add()` method returns `false` if the element is already present in the set. If it fails to add `nums[i]`, a duplicate exists within the window, so we immediately return `true`.

### Code
```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Remove the element that is now outside the window
            if (i > k) {
                set.remove(nums[i - k - 1]);
            }
            
            // Try to add the current element. If it fails, a duplicate exists.
            if (!set.add(nums[i])) {
                return true;
            }
        }
        
        return false;
    }
}
```

### Complexity
- **Time:** $O(n)$, where $n$ is the length of `nums`. We traverse the array once, performing $O(1)$ average-time set insertion and deletion operations per element.
- **Space:** $O(\min(n, k))$, as the hash set stores at most $\min(n, k)$ elements at any given time.

## 🕵️‍♂️ Follow-up Questions (Optional)

**1. How would you solve this if $k$ is extremely large compared to $n$?**
- The algorithm already handles this naturally because the set size is bounded by $\min(n, k)$. If $k \ge n$, no elements will ever be removed from the set via `i > k`, and the space complexity will cap at $O(n)$.

**2. How does this solution compare to using a `HashMap` mapping values to their most recent indices?**
- A `HashMap<Integer, Integer>` approach stores `(value -> last_seen_index)`. While checking `i - map.get(nums[i]) <= k` uses $O(n)$ time, it maintains up to $n$ entries in the worst case without explicit deletion, whereas the sliding window `HashSet` caps space explicitly at $O(\min(n, k))$.