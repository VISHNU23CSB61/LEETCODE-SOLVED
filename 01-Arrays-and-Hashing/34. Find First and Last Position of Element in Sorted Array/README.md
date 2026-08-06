<h2><a href="https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array">34. Find First and Last Position of Element in Sorted Array</a></h2>

<p>Given an array of integers <code>nums</code> sorted in non-decreasing order, find the starting and ending position of a given <code>target</code> value.</p>

<p>If <code>target</code> is not found in the array, return <code>[-1, -1]</code>.</p>

<p>You must&nbsp;write an algorithm with&nbsp;<code>O(log n)</code> runtime complexity.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> nums = [5,7,7,8,8,10], target = 8
<strong>Output:</strong> [3,4]
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> nums = [5,7,7,8,8,10], target = 6
<strong>Output:</strong> [-1,-1]
</pre><p><strong class="example">Example 3:</strong></p>
<pre><strong>Input:</strong> nums = [], target = 0
<strong>Output:</strong> [-1,-1]
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>0 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>9</sup>&nbsp;&lt;= nums[i]&nbsp;&lt;= 10<sup>9</sup></code></li>
	<li><code>nums</code> is a non-decreasing array.</li>
	<li><code>-10<sup>9</sup>&nbsp;&lt;= target&nbsp;&lt;= 10<sup>9</sup></code></li>
</ul>


---

# 🛍️ Find-First-and-Last-Position-of-Element-in-Sorted-Array | Explained

## Approach 1: Modified Binary Search (Two Passes)

### Intuition

Imagine looking up a multi-page entry in an alphabetized physical dictionary. If you open to a page in the middle and find the target word, you cannot immediately assume it is the first or last page where that word appears—it could span several consecutive pages. 

To find the **first** page where the word appears, every time you see the word, you mark down that page number as your best guess, but you deliberately keep flipping pages to the **left** (earlier in the book) to see if it starts even earlier.

To find the **last** page, once you spot the word, you mark down the page number as your best guess, but you deliberately keep flipping pages to the **right** (later in the book) to see if it extends further.

Since the input array `nums` is sorted, we can adapt standard Binary Search. Instead of stopping immediately when `nums[mid] == target`, we save `mid` as a potential answer and continue searching leftwards (for the start index) or rightwards (for the end index).

---

### Algorithm Visualized

```mermaid
flowchart TD
    A[Start binSearch with isLeft flag] --> B{left <= right?}
    B -- No --> K[Return recorded index 'ind']
    B -- Yes --> C[Calculate mid = left + left-right / 2]
    C --> D{target vs nums[mid]}
    D -- target > nums[mid] --> E[left = mid + 1]
    D -- target < nums[mid] --> F[right = mid - 1]
    D -- target == nums[mid] --> G[Update candidate: ind = mid]
    G --> H{isLeft is true?}
    H -- Yes: Search Left --> I[right = mid - 1]
    H -- No: Search Right --> J[left = mid + 1]
    E --> B
    F --> B
    I --> B
    J --> B
```

---

### Approach

1. **Main Method (`searchRange`)**:
   - Call a helper binary search method twice:
     - Once with `isLeft = true` to locate the starting (leftmost) boundary of `target`.
     - Once with `isLeft = false` to locate the ending (rightmost) boundary of `target`.
   - Store the two returned indices in an integer array of size 2 and return it.

2. **Helper Method (`binSearch`)**:
   - Initialize `left = 0`, `right = nums.length - 1`, and `ind = -1` (default if `target` is not found).
   - Enter a standard `while(left <= right)` loop:
     - Calculate `mid = left + (right - left) / 2` to avoid integer overflow.
     - If `nums[mid] < target`, move search space to the right: `left = mid + 1`.
     - If `nums[mid] > target`, move search space to the left: `right = mid - 1`.
     - If `nums[mid] == target`:
       - Update `ind = mid`.
       - If `isLeft` is `true`, force the binary search to keep looking left by setting `right = mid - 1`.
       - If `isLeft` is `false`, force the binary search to keep looking right by setting `left = mid + 1`.
   - Return `ind`.

---

### Detailed Code Analysis

#### 1. Entry Point & Result Wrapper (`searchRange`)
```java
1class Solution {
2    public int[] searchRange(int[] nums, int target) {
3        int[] res={-1,-1};
4        int left=binSearch(nums,target,true);
5        int right=binSearch(nums,target,false);
6        res[0]=left;
7        res[1]=right;
8        return res;
9    
10    }
```
- **Lines 3, 6-8**: We instantiate `res` as `[-1, -1]`. If the target is not present in `nums`, both calls to `binSearch` will return `-1`, keeping this fallback intact.
- **Lines 4-5**: We invoke `binSearch` twice. The third argument (`boolean isLeft`) changes the directional bias of the search when `target` is matched.

---

#### 2. Specialized Binary Search Function (`binSearch`)
```java
11    private int binSearch(int[] nums,int target,boolean isLeft){
12        int left=0,right=nums.length-1;
13        int ind=-1;
```
- **Line 12**: Pointers `left` and `right` bound the search window.
- **Line 13**: `ind` acts as a memory cell holding the most recent index where `nums[mid] == target`.

```java
14        while(left<=right){
15            int mid=left+(right-left)/2;
```
- **Line 14**: Standard loop condition guarantees we inspect every potential sub-array down to a single element.
- **Line 15**: `left + (right - left) / 2` calculates the midpoint safely without risking integer overflow, which can happen with `(left + right) / 2` in Java if the sum exceeds `Integer.MAX_VALUE`.

```java
16            if(target>nums[mid]){
17                left=mid+1;
18            }else if(target<nums[mid]){
19                right=mid-1;
```
- **Lines 16-19**: Standard binary search narrowing. If `target` is larger, it must reside in the right half (`left = mid + 1`). If `target` is smaller, it must reside in the left half (`right = mid - 1`).

```java
20            }else{
21                ind=mid;
22                if(isLeft){
23                    right=mid-1;
24                }else{
25                    left=mid+1;
26                }
27            }
28        }return ind;
29    }
30}
```
- **Line 20-21**: When `nums[mid] == target`, we record the match: `ind = mid`.
- **Lines 22-26**: Instead of immediately returning `mid`, we continue shrinking our boundary:
  - When `isLeft == true`, we discard the right side (`right = mid - 1`) to check if `target` exists at an even lower index.
  - When `isLeft == false`, we discard the left side (`left = mid + 1`) to check if `target` exists at an even higher index.
- **Line 28**: Returns the last saved match position `ind` (or `-1` if no match was ever found).

---

### Code

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1, -1};
        int left = binSearch(nums, target, true);
        int right = binSearch(nums, target, false);
        res[0] = left;
        res[1] = right;
        return res;
    }

    private int binSearch(int[] nums, int target, boolean isLeft) {
        int left = 0, right = nums.length - 1;
        int ind = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target > nums[mid]) {
                left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                ind = mid;
                if (isLeft) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return ind;
    }
}
```

---

### Complexity

- **Time:** $\mathcal{O}(\log N)$
  - Executing a single binary search over an array of size $N$ takes $\mathcal{O}(\log N)$ time because the search space is halved in every step.
  - Doing two sequential binary searches yields $\mathcal{O}(\log N) + \mathcal{O}(\log N) = \mathcal{O}(\log N)$ total time complexity.

- **Space:** $\mathcal{O}(1)$
  - The algorithm operates purely in-place using scalar variables (`left`, `right`, `mid`, `ind`).
  - Memory usage is completely independent of the size of the input array `nums`.

---

## 🕵️‍♂️ Follow-up Questions

### 1. Can we solve this with a single lower-bound function call instead of two customized searches?
**Answer:** Yes. A `lowerBound(nums, target)` helper function can be written to return the *first index where an element is greater than or equal to `target`*. 
- The starting index is `start = lowerBound(nums, target)`.
- The ending index can be found using `end = lowerBound(nums, target + 1) - 1`.
- After checking that `start` is within bounds and actually points to `target`, this returns `[start, end]` using a single helper logic.

### 2. How does this implementation handle edge cases like an empty array or all elements being identical to the target?
**Answer:** 
- **Empty Array (`nums = []`)**: `right` becomes `0 - 1 = -1`. The loop `while (left <= right)` immediately exits without running, returning `-1` for both bounds correctly.
- **All elements match (`nums = [8, 8, 8, 8]`, `target = 8`)**:
  - The left search continually shifts `right = mid - 1` until it reaches index `0`.
  - The right search continually shifts `left = mid + 1` until it reaches index `3`.
  - Result: `[0, 3]`, which is fully correct.