<h2><a href="https://leetcode.com/problems/sort-colors">75. Sort Colors</a></h2>

<p>Given an array <code>nums</code> with <code>n</code> objects colored red, white, or blue, sort them <strong><a href="https://en.wikipedia.org/wiki/In-place_algorithm" target="_blank">in-place</a> </strong>so that objects of the same color are adjacent, with the colors in the order red, white, and blue.</p>

<p>We will use the integers <code>0</code>, <code>1</code>, and <code>2</code> to represent the color red, white, and blue, respectively.</p>

<p>You must solve this problem without using the library's sort function.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [2,0,2,1,1,0]
<strong>Output:</strong> [0,0,1,1,2,2]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [2,0,1]
<strong>Output:</strong> [0,1,2]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == nums.length</code></li>
	<li><code>1 &lt;= n &lt;= 300</code></li>
	<li><code>nums[i]</code> is either <code>0</code>, <code>1</code>, or <code>2</code>.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow up:</strong>&nbsp;Could you come up with a one-pass algorithm using only&nbsp;constant extra space?</p>


---

# 🛍️ Sort-Colors | Explained

## Approach 1: Bubble Sort (Nested Pass In-Place Swapping)

### Intuition
Imagine you have a line of colored balls (red, white, and blue, represented by numbers `0`, `1`, and `2`) arranged in a random order. You want to line them up so that all reds come first, followed by whites, and then blues. 

The core idea behind this approach is analogous to gravity or buoyancy: heavier numbers "sink" to the right end of the array, while lighter numbers "float" to the left. By repeatedly scanning adjacent pairs from left to right and swapping them whenever they are out of order, the largest remaining value in the unsorted portion guaranteed "bubbles up" to its correct position at the end of every full pass.

### Algorithm Visualized

```mermaid
graph TD
    Start([Start sortColors]) --> OuterLoop[Outer Loop: i from 0 to N-1]
    OuterLoop --> InnerLoop[Inner Loop: j from 0 to N-i-2]
    InnerLoop --> Check{Is nums[j] > nums[j+1]?}
    Check -- Yes --> Swap[Swap nums[j] and nums[j+1]]
    Check -- No --> Advance[Advance j Pointer]
    Swap --> Advance
    Advance --> InnerCheck{Is j < N-i-1?}
    InnerCheck -- Yes --> InnerLoop
    InnerCheck -- No --> MarkSorted[Element at index N-i-1 is in final position]
    MarkSorted --> OuterCheck{Is i < N?}
    OuterCheck -- Yes --> OuterLoop
    OuterCheck -- No --> End([Array Fully Sorted])
```

### Approach
1. **Outer Boundary Tracking:** Run an outer loop `i` from `0` to `nums.length - 1`. Each pass guarantees that the $i$-th largest element reaches its final destination at index `nums.length - 1 - i`.
2. **Adjacent Comparison:** Run an inner loop `j` from `0` to `nums.length - i - 2`. Compare each element `nums[j]` with its immediate right neighbor `nums[j + 1]`.
3. **In-Place Swap:** If `nums[j] > nums[j + 1]`, perform an in-place swap using a temporary variable `temp`.
4. **Repeat:** Continue until all passes complete, guaranteeing the entire array is sorted in ascending order.

### Detailed Code Analysis

Let's break down the execution step-by-step:

* **Line 3: `for(int i=0;i<nums.length;i++)`**  
  This controls the total number of passes over the array. Since there are $N$ elements, running this loop $N$ times ensures that all elements are positioned correctly.

* **Line 4: `for(int j=0;j<nums.length-i-1;j++)`**  
  This is the scan pointer for adjacent comparisons. Notice the upper bound `nums.length - i - 1`: after $i$ iterations of the outer loop, the last $i$ elements are already in their correct sorted positions. Subtracting $i$ prevents redundant comparisons against already-sorted tail elements, avoiding out-of-bounds access when evaluating `nums[j + 1]`.

* **Line 5: `if(nums[j]>nums[j+1])`**  
  This condition checks for an inversion (a larger number appearing before a smaller number). Since the target array order is `0`s, then `1`s, then `2`s, any pair where `nums[j] > nums[j + 1]` (e.g., `2` before `1`, or `1` before `0`) must be swapped.

* **Lines 6–8:**
  ```java
  int temp=nums[j];
  nums[j]=nums[j+1];
  nums[j+1]=temp;
  ```
  Standard variable swap logic using `temp` to temporarily preserve `nums[j]` while replacing it with `nums[j + 1]`. This mutates the input array directly in-place without allocating dynamic memory.

### Code

```java
class Solution {
    public void sortColors(int[] nums) {
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<nums.length-i-1;j++){
                if(nums[j]>nums[j+1]){
                    int temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                }
            }
        }
    }
}
```

### Complexity

- **Time Complexity:** $\mathcal{O}(N^2)$  
  - **Worst Case:** $\mathcal{O}(N^2)$ when the array is sorted in reverse order.
  - **Average Case:** $\mathcal{O}(N^2)$ due to the nested loops executing $\frac{N(N-1)}{2}$ comparisons.
  - **Best Case:** $\mathcal{O}(N^2)$ because there is no early exit mechanism (like a `swapped` boolean flag) to break out if the array becomes sorted early.

- **Space Complexity:** $\mathcal{O}(1)$ Auxiliary Space  
  - The algorithm operates purely in-place, relying only on a single primitive integer variable `temp` for swaps. Memory utilization remains constant regardless of array size $N$.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How can we optimize this problem to run in $\mathcal{O}(N)$ time complexity and a single pass?
**Answer:** Use the **Dutch National Flag algorithm** (a 3-pointer partition pattern):
- Maintain three pointers: `low = 0`, `mid = 0`, and `high = nums.length - 1`.
- While `mid <= high`:
  - If `nums[mid] == 0`: Swap `nums[low]` and `nums[mid]`, increment both `low++` and `mid++`.
  - If `nums[mid] == 1`: Increment `mid++`.
  - If `nums[mid] == 2`: Swap `nums[mid]` and `nums[high]`, decrement `high--`.
This classifies elements into three partitions in a single $\mathcal{O}(N)$ pass with $\mathcal{O}(1)$ space.

### 2. Is this Bubble Sort implementation stable?
**Answer:** Yes. Stability means equal elements maintain their relative order after sorting. Because the condition uses a strict inequality (`nums[j] > nums[j+1]`), adjacent equal values (such as two identical `1`s) are never swapped, preserving their original relative order.