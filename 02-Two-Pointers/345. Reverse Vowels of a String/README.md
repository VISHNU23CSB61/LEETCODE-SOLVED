<h2><a href="https://leetcode.com/problems/reverse-vowels-of-a-string">345. Reverse Vowels of a String</a></h2>

<p>Given a string <code>s</code>, reverse only all the vowels in the string and return it.</p>

<p>The vowels are <code>'a'</code>, <code>'e'</code>, <code>'i'</code>, <code>'o'</code>, and <code>'u'</code>, and they can appear in both lower and upper cases, more than once.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "IceCreAm"</span></p>

<p><strong>Output:</strong> <span class="example-io">"AceCreIm"</span></p>

<p><strong>Explanation:</strong></p>

<p>The vowels in <code>s</code> are <code>['I', 'e', 'e', 'A']</code>. On reversing the vowels, s becomes <code>"AceCreIm"</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "leetcode"</span></p>

<p><strong>Output:</strong> <span class="example-io">"leotcede"</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 3 * 10<sup>5</sup></code></li>
	<li><code>s</code> consist of <strong>printable ASCII</strong> characters.</li>
</ul>


---

# 🛍️ Reverse-Vowels-of-a-String | Explained

## Approach 1: Two-Pointer In-Place Swap
### Intuition
Think of two people standing at opposite ends of a long conveyor belt carrying labeled boxes. Their goal is to swap only the boxes labeled with vowels (`a, e, i, o, u`, case-insensitive) while leaving every other box untouched.

The person on the left walks forward until they find a vowel box. The person on the right walks backward until they also find a vowel box. Once both have stopped at a vowel, they pick up their respective boxes and swap their positions. They then take one step inward and repeat the process until they meet in the middle. Because both pointers only advance inward and process each character at most a constant number of times, this guarantees an efficient linear scan.

### Algorithm Visualized

```mermaid
flowchart TD
    A[Start: Initialize start = 0, end = n - 1] --> B{start < end?}
    B -- No --> H[Convert char array back to String & Return]
    B -- Yes --> C[Advance start pointer until word[start] is a vowel or start >= end]
    C --> D[Decrement end pointer until word[end] is a vowel or start >= end]
    D --> E{start < end?}
    E -- Yes --> F[Swap word[start] and word[end]]
    F --> G[start++, end--]
    E -- No --> G
    G --> B
```

### Approach
1. **Convert to Mutable Array**: Since Java strings are immutable, convert `s` into a character array (`char[] word`) to perform in-place character swaps.
2. **Initialize Two Pointers**: Place `start` at index `0` and `end` at index `s.length() - 1`.
3. **Define Vowel Set**: Use a reference string `"aeiouAEIOU"` containing all valid uppercase and lowercase vowels.
4. **Scan and Skip Consonants**:
   - Advance the `start` pointer forward as long as `start < end` and the character at `start` is not a vowel (`vowels.indexOf(...) == -1`).
   - Decrement the `end` pointer backward as long as `start < end` and the character at `end` is not a vowel.
5. **Swap**: When both pointers stop at vowels, swap `word[start]` and `word[end]`.
6. **Advance Pointers**: Move both pointers inward (`start++`, `end--`) to continue scanning the rest of the array.
7. **Reconstruct String**: Once the pointers cross (`start >= end`), instantiate a new `String` from the modified character array and return it.

### Detailed Code Analysis

1. **Array Conversion & Pointer Initialization**:
   ```java
   char[] word = s.toCharArray();
   int start = 0;
   int end = s.length() - 1;
   String vowels = "aeiouAEIOU";
   ```
   - `s.toCharArray()` allocates a new character array of size $N$. This allows $O(1)$ read and write operations.
   - `start` and `end` track the left and right boundaries.
   - `vowels` acts as a lookup collection for membership testing.

2. **Outer Loop**:
   ```java
   while (start < end) {
   ```
   - The loop continues until the two pointers meet or cross, ensuring every element is examined without redundant passes.

3. **Skipping Non-Vowels**:
   ```java
   while (start < end && vowels.indexOf(word[start]) == -1) {
       start++;
   }
   while (start < end && vowels.indexOf(word[end]) == -1) {
       end--;
   }
   ```
   - `vowels.indexOf(word[start]) == -1` checks if the character at `start` is absent from `"aeiouAEIOU"`.
   - The inner guard condition `start < end` prevents the pointers from overshooting each other or causing `ArrayIndexOutOfBoundsException` when consecutive consonants appear.

4. **Swapping Elements**:
   ```java
   char temp = word[start];
   word[start] = word[end];
   word[end] = temp;
   
   start++;
   end--;
   ```
   - Standard three-step swap using a temporary variable `temp`.
   - `start++` and `end--` are critical to avoid an infinite loop and to advance past the characters that were just swapped.

5. **Result Reconstruction**:
   ```java
   String answer = new String(word);
   return answer;
   ```
   - Converts the modified `char[]` back into a new immutable `String` object.

### Code
```java
class Solution {
    public String reverseVowels(String s) {
        char[] word = s.toCharArray();
        int start = 0;
        int end = s.length() - 1;
        String vowels = "aeiouAEIOU";
        
        while (start < end) {
            // Move start pointer until it points to a vowel
            while (start < end && vowels.indexOf(word[start]) == -1) {
                start++;
            }
            
            // Move end pointer until it points to a vowel
            while (start < end && vowels.indexOf(word[end]) == -1) {
                end--;
            }
            
            // Swap the vowels
            char temp = word[start];
            word[start] = word[end];
            word[end] = temp;
            
            // Move the pointers towards each other
            start++;
            end--;
        }
        
        String answer = new String(word);
        return answer;
    }
}
```

### Complexity
- **Time:** $O(N)$, where $N$ is the length of the string `s`. Each character is visited at most twice (once by the pointer increment/decrement and once during the swap). The vowel lookup via `vowels.indexOf(...)` operates on a fixed string of length 10, which runs in $O(1)$ constant time.
- **Space:** $O(N)$ auxiliary space. In Java, strings are immutable, so allocating the `char[] word` array of length $N$ is required to perform in-place swaps.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **How can you optimize the vowel lookup for maximum execution speed in Java?**
   - Instead of calling `vowels.indexOf(c)` which iterates through the 10-character string on each check, you can use a lookup array or a custom helper method with a `switch` statement or a lookup table like `boolean[128] isVowel`. Direct array indexing (`isVowel[word[start]]`) or a `switch` compiles to a direct jump table / bytecode instruction, reducing function call and iteration overhead.

2. **What if the input contains multi-byte Unicode characters or accented vowels (e.g., `é`, `ü`)?**
   - The current `char[]` and `indexOf` approach assumes UTF-16 code units and standard ASCII vowels. For Unicode support, the string would need to be processed via Unicode code points (`s.codePoints()`) or normalized using `java.text.Normalizer` to handle combining diacritics before running the two-pointer swap.