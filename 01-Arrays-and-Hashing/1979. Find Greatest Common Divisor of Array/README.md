<h2><a href="https://leetcode.com/problems/find-greatest-common-divisor-of-array">1979. Find Greatest Common Divisor of Array</a></h2>

<p>Given an integer array <code>nums</code>, return<strong> </strong><em>the <strong>greatest common divisor</strong> of the smallest number and largest number in </em><code>nums</code>.</p>

<p>The <strong>greatest common divisor</strong> of two numbers is the largest positive integer that evenly divides both numbers.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [2,5,6,9,10]
<strong>Output:</strong> 2
<strong>Explanation:</strong>
The smallest number in nums is 2.
The largest number in nums is 10.
The greatest common divisor of 2 and 10 is 2.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [7,5,6,8,3]
<strong>Output:</strong> 1
<strong>Explanation:</strong>
The smallest number in nums is 3.
The largest number in nums is 8.
The greatest common divisor of 3 and 8 is 1.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> nums = [3,3]
<strong>Output:</strong> 3
<strong>Explanation:</strong>
The smallest number in nums is 3.
The largest number in nums is 3.
The greatest common divisor of 3 and 3 is 3.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= nums.length &lt;= 1000</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 1000</code></li>
</ul>


---

# 🛍️ Find-Greatest-Common-Divisor-of-Array | Explained

## Approach 1: Single-Pass Extrema Search with Euclidean Algorithm
### Intuition
To find the Greatest Common Divisor (GCD) of the smallest and largest numbers in an array, we must solve two distinct sub-problems:
1. Find the minimum and maximum values in the array.
2. Compute the GCD of those two values.

An intuitive real-world analogy is finding the largest common packing box size that can perfectly fit both the smallest and largest items in a warehouse. Instead of sorting the entire inventory (which is computationally expensive), we can scan the items once in a single pass to identify the smallest and largest items. Once identified, we apply the ancient **Euclidean Algorithm** (repeated division/modulo reduction) to find their greatest common divisor. This is highly efficient because the Euclidean algorithm continuously scales down the problem size exponentially.

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start]) --> Init[Initialize mx = Integer.MIN_VALUE<br/>mn = Integer.MAX_VALUE]
    Init --> Loop{For each x in nums}
    Loop -- Yes --> Update[mx = Math.max(mx, x)<br/>mn = Math.min(mn, x)]
    Update --> Loop
    Loop -- No --> CallGCD[Call gcd(mx, mn)]
    
    subgraph GCD_Computation [Euclidean Step]
        CallGCD --> LoopGCD{b != 0?}
        LoopGCD -- Yes --> Step[temp = b<br/>b = a % b<br/>a = temp]
        Step --> LoopGCD
        LoopGCD -- No --> Return[Return Math.abs(a)]
    end
    
    Return --> End([End])
```

### Approach
1. **Initialize State:** Establish baseline extremes by initializing `mx` (maximum) to the smallest possible integer value and `mn` (minimum) to the largest possible integer value.
2. **Single-Pass Scan:** Iterate through the array `nums` exactly once. In each iteration, update the running maximum (`mx`) and running minimum (`mn`).
3. **Euclidean GCD Reduction:** Pass `mx` and `mn` into an iterative implementation of the Euclidean algorithm:
   - While the divisor `b` is not zero, calculate the remainder of `a % b`.
   - Update `a` to be the current `b`, and update `b` to be the computed remainder.
   - When `b` becomes `0`, `a` contains the greatest common divisor.
4. **Defensive Formatting:** Return the absolute value of `a` to guarantee a non-negative GCD.

### Detailed Code Analysis

- **Lines 3-4 (`int mx=Integer.MIN_VALUE; int mn=Integer.MAX_VALUE;`):**
  We initialize our tracking variables to extreme boundaries. This is a standard, robust programming pattern that ensures the very first element processed in the loop will successfully overwrite both variables.
  
- **Lines 5-8 (`for(int x:nums){...}`):**
  This is an enhanced `for` loop (for-each). It avoids manual index management and eliminates boundary check overhead at the bytecode level. Inside the loop, `Math.max` and `Math.min` utilize highly optimized CPU-level intrinsic branching to update `mx` and `mn` efficiently.
  
- **Line 9 (`return gcd(mx,mn);`):**
  Once the loop completes, `mx` holds the absolute maximum element, and `mn` holds the absolute minimum element. These are passed as parameters `a` and `b` respectively to the `gcd` method.
  
- **Lines 11-17 (`public int gcd(int a,int b){...}`):**
  This helper method implements the **Iterative Euclidean Algorithm**:
  - `while(b!=0)`: This loop continues to shrink the values. The modulo operator `%` acts as a rapid subtraction agent.
  - `int temp=b; b=a%b; a=temp;`: These three lines perform a swap-and-reduce. The current divisor `b` becomes the next dividend, and the remainder of the division `a % b` becomes the next divisor.
  - `return Math.abs(a)`: Conventionally, the GCD of two numbers is defined as a non-negative integer. Although the LeetCode constraints specify positive integers (`2 <= nums[i] <= 1000`), using `Math.abs` is an excellent defensive engineering practice, making this helper utility robust and reusable for negative integers.

### Code
```java
class Solution {
    public int findGCD(int[] nums) {
        int mx=Integer.MIN_VALUE;
        int mn=Integer.MAX_VALUE;
        for(int x:nums){
            mx=Math.max(mx,x);
            mn=Math.min(mn,x);
        }
        return gcd(mx,mn);
    }
    public int gcd(int a,int b){
        while(b!=0){
            int temp=b;
            b=a%b;
            a=temp;
        }return Math.abs(a);
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N + \log(\min(mx, mn)))$
  - Scanning the array of size $N$ to find `mx` and `mn` takes linear time $\mathcal{O}(N)$.
  - The Euclidean algorithm reduces the inputs logarithmically. In the absolute worst-case scenario (successive Fibonacci numbers), the GCD loop runs in logarithmic time proportional to the value of the smaller number, which is $\mathcal{O}(\log(\min(mx, mn)))$. Given constraints ($nums[i] \le 1000$), the GCD step executes in less than $10$ iterations, acting virtually as $\mathcal{O}(1)$.
- **Space Complexity:** $\mathcal{O}(1)$
  - No auxiliary data structures are allocated. The memory footprint consists of a constant number of primitive variables allocated on the execution stack.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How would you optimize this solution if the array size $N$ was extremely large (e.g., billions of items) and distributed across a cluster?
**Answer:**
We would implement a MapReduce or distributed pipeline approach:
- **Map Stage:** Divide the massive array into chunks and distribute them across multiple nodes. Each node performs a local single-pass scan to find its local `min` and `max`.
- **Reduce Stage:** Aggregate the local minimums and maximums across nodes to compute the global `min` and `max`.
- **Final Step:** Run the Euclidean `gcd` function on a single node using the final global `min` and `max`. This limits network transfer cost to just a few integer parameters.

### 2. Is there any risk of integer overflow during the modulo computation in your `gcd` function?
**Answer:**
No, there is no risk of integer overflow. The modulo operator `%` yields a remainder that is strictly strictly smaller than the divisor `b` (i.e., $a \% b < b$). Because the values continuously shrink toward zero, the numbers never grow, making overflow mathematically impossible during the reduction loop.