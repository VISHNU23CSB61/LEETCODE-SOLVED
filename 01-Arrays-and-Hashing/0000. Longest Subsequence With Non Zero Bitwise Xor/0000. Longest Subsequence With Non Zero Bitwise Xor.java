int longestSubsequence(vector<int>& nums) {
    int tot = 0, len = nums.size();
    bool nonZero = false;

    for (int n : nums) {
        nonZero |= n > 0;      // Check if non-zero exists.
        tot ^= n;              // Compute the total XOR.
    }

    if (tot != 0) return len;  // Case 1: Total XOR is a non-zero.

    if (nonZero)
        return len - 1;        // Case 2a: Remove 1 non-zero element.

    return 0;                  // Case 2b: All elements are 0.
}