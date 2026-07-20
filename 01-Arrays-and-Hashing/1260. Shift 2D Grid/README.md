<h2><a href="https://leetcode.com/problems/shift-2d-grid">1260. Shift 2D Grid</a></h2>

<p>Given a 2D <code>grid</code> of size <code>m x n</code>&nbsp;and an integer <code>k</code>. You need to shift the <code>grid</code>&nbsp;<code>k</code> times.</p>

<p>In one shift operation:</p>

<ul>
	<li>Element at <code>grid[i][j]</code> moves to <code>grid[i][j + 1]</code>.</li>
	<li>Element at <code>grid[i][n - 1]</code> moves to <code>grid[i + 1][0]</code>.</li>
	<li>Element at <code>grid[m&nbsp;- 1][n - 1]</code> moves to <code>grid[0][0]</code>.</li>
</ul>

<p>Return the <em>2D grid</em> after applying shift operation <code>k</code> times.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2019/11/05/e1.png" style="width: 400px; height: 178px;">
<pre><strong>Input:</strong> <code>grid</code> = [[1,2,3],[4,5,6],[7,8,9]], k = 1
<strong>Output:</strong> [[9,1,2],[3,4,5],[6,7,8]]
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2019/11/05/e2.png" style="width: 400px; height: 166px;">
<pre><strong>Input:</strong> <code>grid</code> = [[3,8,1,9],[19,7,2,5],[4,6,11,10],[12,0,21,13]], k = 4
<strong>Output:</strong> [[12,0,21,13],[3,8,1,9],[19,7,2,5],[4,6,11,10]]
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> <code>grid</code> = [[1,2,3],[4,5,6],[7,8,9]], k = 9
<strong>Output:</strong> [[1,2,3],[4,5,6],[7,8,9]]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>m ==&nbsp;grid.length</code></li>
	<li><code>n ==&nbsp;grid[i].length</code></li>
	<li><code>1 &lt;= m &lt;= 50</code></li>
	<li><code>1 &lt;= n &lt;= 50</code></li>
	<li><code>-1000 &lt;= grid[i][j] &lt;= 1000</code></li>
	<li><code>0 &lt;= k &lt;= 100</code></li>
</ul>


---

# 🛍️ Shift-2D-Grid | Explained

## Approach 1: Flattening with Explicit 1D Arrays (Double Buffering)
### Intuition
Imagine a classroom of students seated in a grid of rows and columns. If we want everyone to shift $k$ seats to the right, students at the end of a row must wrap around to the beginning of the next row, and the student at the very bottom-right must walk all the way to the top-left. 

Instead of dealing with complex 2D boundary conditions (checking if we are at the end of a row, the end of the grid, etc.), we can simplify the problem. If we ask all students to stand up and form a single, straight queue (a 1D line) ordered row-by-row, shifting them becomes incredibly simple. We just move everyone $k$ positions down the line. If someone goes past the end of the line, they loop back to the front (using modulo arithmetic). Finally, we have everyone sit back down in the original grid layout row-by-row. 

This is exactly what this approach does:
1. **Flatten** the 2D grid into a 1D array.
2. **Shift** the elements using a second 1D array and modulo arithmetic to handle wrapping.
3. **Reconstruct** the 2D layout directly into the requested output structure.

### Algorithm Visualized
```mermaid
graph TD
    subgraph Step 1: Flatten 2D Grid to 1D Array
        Grid[2D Grid: m x n] -->|Row-major traversal| FlatArr["1D Array: arr [0, 1, ..., (m*n)-1]"]
    end

    subgraph Step 2: Cyclic Shift
        FlatArr -->|newIndex = (i + k) % len| ShiftedArr["1D Shifted Array: shifted"]
    end

    subgraph Step 3: Reconstruct 2D List
        ShiftedArr -->|Group every n elements| AnsList["List<List<Integer>>: ans"]
    end

    style Grid fill:#f9f,stroke:#333,stroke-width:2px
    style FlatArr fill:#bbf,stroke:#333,stroke-width:2px
    style ShiftedArr fill:#bfb,stroke:#333,stroke-width:2px
    style AnsList fill:#fbb,stroke:#333,stroke-width:2px
```

### Approach
1. **Dimensions & Initialization**:
   - Determine the dimensions of the grid: $m$ (rows) and $n$ (columns).
   - Compute the total number of elements: $m \times n$.
   - Instantiate a 1D array `arr` of size $m \times n$.

2. **Step 1: Flattening**:
   - Iterate through the 2D grid in row-major order (outer loop for rows, inner loop for columns).
   - Copy each element into the 1D array `arr` sequentially, tracked by counter `c`.

3. **Step 2: Cyclic Shift**:
   - Create a second 1D array `shifted` of the same size.
   - For every element at index `i` in `arr`, calculate its new index after shifting by $k$: `newIndex = (i + k) % arr.length`.
   - Place the element at `shifted[newIndex]`.

4. **Step 3: Grid Reconstruction**:
   - Initialize a nested list `ans` to hold the final shifted grid.
   - Read elements sequentially from the `shifted` array. Group every $n$ elements into a new list (representing a row) and append it to `ans`.

---

### Detailed Code Analysis

Let's break down the code implementation step-by-step:

```java
int m = grid.length;
int n = grid[0].length;

int[] arr = new int[m * n];
int c = 0;
```
* **Analysis**: We retrieve the dimensions of our matrix. We then allocate a 1D array `arr` of size $m \times n$ to store the flattened grid elements. A pointer `c` acts as a write-index tracker.

---

```java
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        arr[c++] = grid[i][j];
    }
}
```
* **Analysis**: This nested loop performs the row-major flattening. Since `c` starts at `0` and post-increments (`c++`), elements from `grid[0][0]` to `grid[m-1][n-1]` are copied contiguously into `arr`.

---

```java
int[] shifted = new int[arr.length];
for (int i = 0; i < arr.length; i++) {
    int newIndex = (i + k) % arr.length;
    shifted[newIndex] = arr[i];
}
```
* **Analysis**: 
  * A second array `shifted` is instantiated to hold the transformed elements without mutating the original source array `arr` mid-operation.
  * The formula `(i + k) % arr.length` ensures cyclic behavior. If $i + k$ exceeds the array bounds, the modulo operator `%` wraps the index back to the beginning. This naturally handles cases where $k \ge m \times n$.

---

```java
List<List<Integer>> ans = new ArrayList<>();
c = 0;

for (int i = 0; i < m; i++) {
    List<Integer> row = new ArrayList<>();
    for (int j = 0; j < n; j++) {
        row.add(shifted[c++]);
    }
    ans.add(row);
}

return ans;
```
* **Analysis**:
  * We initialize the outer `List<List<Integer>>`.
  * We reset our pointer `c` to `0` to read sequentially from the `shifted` array.
  * The nested loop reconstructs the 2D grid shape. For each row $i$, we create a list `row`, populate it with the next $n$ elements from `shifted`, and then add `row` to our final list `ans`.

---

### Code
```java
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int[] arr = new int[m * n];
        int c = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[c++] = grid[i][j];
            }
        }

        int[] shifted = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int newIndex = (i + k) % arr.length;
            shifted[newIndex] = arr[i];
        }

        List<List<Integer>> ans = new ArrayList<>();
        c = 0;

        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(shifted[c++]);
            }
            ans.add(row);
        }

        return ans;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(m \times n)$  
  We perform exactly three linear passes over the grid data: 
  1. Flattening the grid: $\mathcal{O}(m \times n)$
  2. Shifting the 1D array: $\mathcal{O}(m \times n)$
  3. Reconstructing the 2D nested list: $\mathcal{O}(m \times n)$
  
  Thus, total time complexity is strictly linear with respect to the total number of elements in the grid.

- **Space Complexity:** $\mathcal{O}(m \times n)$  
  - We allocate an intermediate 1D array `arr` of size $m \times n$.
  - We allocate a second 1D array `shifted` of size $m \times n$.
  - Excluding the final output list (which is required by the problem description), the auxiliary space allocated is $2 \times (m \times n)$, which simplifies asymptotically to $\mathcal{O}(m \times n)$.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How can we optimize this solution to use $\mathcal{O}(1)$ auxiliary space?
**Answer:**  
Instead of allocating physical intermediate 1D arrays, we can perform virtual index mapping on-the-fly. 
Any cell index `(r, c)` in a 2D grid of width $n$ corresponds to a unique 1D index:
$$\text{flat\_index} = r \times n + c$$

Conversely, a 1D index can be mapped back to its 2D coordinates:
$$\text{row} = \lfloor\text{flat\_index} / n\rfloor, \quad \text{col} = \text{flat\_index} \pmod n$$

Using these formulas, we can build the resulting `List<List<Integer>>` directly from the input `grid` in a single pass without copying elements into temporary arrays:

```java
int total = m * n;
k = k % total; // Optimize shift amount

List<List<Integer>> ans = new ArrayList<>();
for (int i = 0; i < m; i++) {
    List<Integer> row = new ArrayList<>();
    for (int j = 0; j < n; j++) {
        // Calculate the virtual 1D index for the current output slot
        int currentFlatIndex = i * n + j;
        // Find the virtual 1D index of the source element (reverse shift)
        int sourceFlatIndex = (currentFlatIndex - k + total) % total;
        
        // Convert the source 1D index back to 2D coordinates
        int sourceRow = sourceFlatIndex / n;
        int sourceCol = sourceFlatIndex % n;
        
        row.add(grid[sourceRow][sourceCol]);
    }
    ans.add(row);
}
```
This reduces auxiliary space complexity from $\mathcal{O}(m \times n)$ to $\mathcal{O}(1)$.

### 2. What happens if $k$ is extremely large (e.g., $k = 10^9$)? How does your solution handle it, and how can it be optimized?
**Answer:**  
In the current code, if $k$ is very large, the expression `(i + k) % arr.length` will still evaluate correctly because of the modulo operator. However, if $k$ is extremely large, adding it directly to `i` inside the loop can theoretically cause integer overflow if $i + k > \text{Integer.MAX\_VALUE}$. 

To safeguard against integer overflow and eliminate redundant full cycles around the grid, we should normalize $k$ at the very beginning of the function using:
```java
k = k % (m * n);
```
Since shifting a grid of size $S$ by $S$ times results in the exact same configuration, we only need to perform $k \pmod S$ shifts. This ensures $k$ is always strictly less than the size of the grid, saving CPU arithmetic overhead and preventing overflow bugs.