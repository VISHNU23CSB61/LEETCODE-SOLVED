<h2><a href="https://leetcode.com/problems/smallest-missing-integer-greater-than-sequential-prefix-sum">2996. Smallest Missing Integer Greater Than Sequential Prefix Sum</a></h2>

<p>You are given a <strong>0-indexed</strong> array of integers <code>nums</code>.</p>

<p>A prefix <code>nums[0..i]</code> is <strong>sequential</strong> if, for all <code>1 &lt;= j &lt;= i</code>, <code>nums[j] = nums[j - 1] + 1</code>. In particular, the prefix consisting only of <code>nums[0]</code> is <strong>sequential</strong>.</p>

<p>Return <em>the <strong>smallest</strong> integer</em> <code>x</code> <em>missing from</em> <code>nums</code> <em>such that</em> <code>x</code> <em>is greater than or equal to the sum of the <strong>longest</strong> sequential prefix.</em></p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,2,3,2,5]
<strong>Output:</strong> 6
<strong>Explanation:</strong> The longest sequential prefix of nums is [1,2,3] with a sum of 6. 6 is not in the array, therefore 6 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [3,4,5,1,12,14,13]
<strong>Output:</strong> 15
<strong>Explanation:</strong> The longest sequential prefix of nums is [3,4,5] with a sum of 12. 12, 13, and 14 belong to the array while 15 does not. Therefore 15 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 50</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 50</code></li>
</ul>


---

# 🛍️ Smallest-Missing-Integer-Greater-Than-Sequential-Prefix-Sum | Explained

## Approach 1: Single-Pass Prefix Tracking with Constraint-Bounded Bitset

### Intuition

Imagine you are auditing item prices on a store shelf from left to right. You must sum up the prices of consecutive items starting from the very first one, but **only** as long as each consecutive item costs exactly $1 more than the previous item. The moment this strict sequence breaks, your total sum is locked in.

Now, you need to find the smallest price tag greater than or equal to this locked-in sum that **does not exist anywhere** on the shelf. 

Because problem constraints dictate that every individual item price is at most $50$, we can leverage a smart observation:
1. If our locked-in sum is strictly greater than $50$, it is physically impossible for any item on the shelf to equal or exceed this sum. Thus, the sum itself is guaranteed to be missing, and we can immediately return it.
2. If the sum is $\le 50$, we only need to track which values between $1$ and $50$ exist on the shelf. A fixed-size bitset (`std::bitset<52>`) allows us to record element presence in $O(1)$ time and space, then iterate upward from `sum` to find the first uncollected integer.

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start]) --> Init[Initialize sum = A[0]<br/>seen.set A[0]<br/>seq = true]
    Init --> Loop[Loop i from 1 to N-1]
    
    Loop --> CheckSeq{seq == true AND<br/>A[i] == A[i-1] + 1?}
    
    CheckSeq -- Yes --> AddSum[sum += A[i]]
    CheckSeq -- No --> BreakSeq[seq = false]
    
    BreakSeq --> CheckCap{sum > 50?}
    CheckCap -- Yes --> ReturnSumEarly([Return sum])
    CheckCap -- No --> MarkSeen
    
    AddSum --> MarkSeen[seen.set A[i]]
    MarkSeen --> NextIter{i < N-1?}
    NextIter -- Yes --> Loop
    NextIter -- No --> ScanLoop[Loop candidate from sum to 51]
    
    ScanLoop --> CheckBit{!seen.test candidate ?}
    CheckBit -- Yes --> ReturnCandidate([Return candidate])
    CheckBit -- No --> NextCandidate[candidate++]
    NextCandidate --> ScanLoop
```

---

### Approach

1. **Sequential Prefix Calculation**:
   - Maintain a boolean flag `seq = true` to monitor the contiguous sequential prefix starting at index `0`.
   - Iterate through array `A` starting from index `1`. While `seq` is `true` and `A[i] == A[i-1] + 1`, add `A[i]` to `sum`.
   - When the sequence breaks for the first time, flip `seq = false`.

2. **Early Exit Optimization**:
   - At the point of breaking the sequence, check if `sum > 50`. Since all elements $A[i] \le 50$, any `sum > 50` cannot exist in `A`. Return `sum` immediately.

3. **Global Presence Tracking**:
   - In the same loop pass, set `seen.set(A[i])` for every element encountered to construct a complete set of elements present in `A`.

4. **Linear Search for Smallest Missing Integer**:
   - Starting from `i = sum`, query `seen.test(i)`.
   - The first value `i` where `seen.test(i)` is `false` is returned as the smallest missing integer.

---

### Detailed Code Analysis

```cpp
class Solution {
public:
    int missingInteger(vector<int>& A) {
        // Initialize prefix sum with the first element
        int sum = A[0];
        
        // Bitset sized 52 to hold elements in range [1, 50]
        bitset<52> seen;
        
        // State flag tracking whether sequential increment chain is intact
        bool seq = true;

        // Register the first element in our bitset
        seen.set(A[0]);

        for (int i = 1; i < A.size(); i++) {
            // Extend sum if sequence is uninterrupted
            if (seq && A[i] == A[i - 1] + 1)
                sum += A[i];
            else {
                seq = false;

                // Optimization: values in A are <= 50. 
                // If sum > 50, sum cannot exist in A.
                if (sum > 50)
                    return sum;
            }

            // Record presence of current element
            seen.set(A[i]);
        }

        // Search for the smallest missing integer >= sum
        for (int i = sum; i < 52; i++)
            if (!seen.test(i))
                return i;

        return sum;
    }
};
```

#### Key Design Decisions:
- **`std::bitset<52>` selection**: Since $1 \le A[i] \le 50$, indices $1$ to $50$ fit comfortably inside a 52-bit bitset. Bitset operations (`set` and `test`) execute in $O(1)$ CPU cycle time using register bitwise masking.
- **Index 51 buffer**: Setting the bitset size to `52` handles the boundary condition where all numbers from `sum` up to `50` are present in `A`. Index `51` will never be set (as $A[i] \le 50$), so `!seen.test(51)` will evaluate to `true` and cleanly return `51`.

---

### Code

```cpp
class Solution {
public:
    int missingInteger(vector<int>& A) {
        int sum = A[0];
        bitset<52> seen;
        bool seq = true;

        seen.set(A[0]);

        for (int i = 1; i < A.size(); i++) {
            if (seq && A[i] == A[i - 1] + 1)
                sum += A[i];
            else {
                seq = false;

                if (sum > 50)
                    return sum;
            }

            seen.set(A[i]);
        }

        for (int i = sum; i < 52; i++)
            if (!seen.test(i))
                return i;

        return sum;
    }
};
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(N)$
  - Scanning array `A` of length $N$ takes $\mathcal{O}(N)$ time.
  - The second loop runs from `sum` to $51$, performing at most $51$ iterations ($\mathcal{O}(1)$ bounded time).
  - Overall time complexity is strictly linear, $\mathcal{O}(N)$.

- **Space Complexity:** $\mathcal{O}(1)$
  - The bitset allocation `std::bitset<52>` consumes fixed stack memory (typically 8 bytes on standard 64-bit architectures).
  - Space usage does not scale with input size $N$, yielding true $\mathcal{O}(1)$ auxiliary space.

---

## 🕵️‍♂️ Follow-up Questions

### 1. What if the constraint $A[i] \le 50$ is lifted to $A[i] \le 10^9$?
If values can be up to $10^9$, a fixed `std::bitset<52>` will overflow and cause undefined behavior. We would replace the `bitset` with an `std::unordered_set<int>` to store seen values in $\mathcal{O}(1)$ average time. Additionally, the early return condition `sum > 50` must be removed, and the final search loop will check `while (seen.count(sum)) sum++;` before returning `sum`.

### 2. Can we solve this in $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ auxiliary space without hash sets or bitsets?
Yes. First, compute `sum` by scanning the sequential prefix. Next, sort the array in-place ($\mathcal{O}(N \log N)$ time, $\mathcal{O}(1)$ space) or use Cyclic Sort / In-place Hash Placement if array values are within $1 \dots N$. After sorting, scan from left to right: if an element matches `sum`, increment `sum` by $1$. Once an element exceeds `sum` or the array ends, `sum` is guaranteed to be the smallest missing integer.