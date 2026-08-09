class Solution {
public:
    vector<int> dp;

    int solve(vector<int>& piles, int s) {
        int i = s >> 8;
        int m = (s >> 1) & 127;
        int player = s & 1;

        int n = piles.size();

        if (i >= n)
            return 0;

        if (dp[s] != -1)
            return dp[s];

        int ans = 0;

        if (player) {
            int result = INT_MIN;

            for (int j = 1; j <= min(m << 1, n - i); j++) {
                ans += piles[i + j - 1];
                result = max(result, ans + solve(piles, ((i + j) << 8) | (max(m, j) << 1) | 0));
            }

            return dp[s] = result;
        } else {
            int result = INT_MAX;
            for (int j = 1; j <= min(m << 1, n - i); j++) 
                result = min(result, solve(piles, ((i + j) << 8) | (max(m, j) << 1) | 1));
            
            return dp[s] = result;
        }
    }

    int stoneGameII(vector<int>& piles) {
        int n = piles.size();
        dp.assign(30000, -1);

        return solve(piles, (0 << 8) | (1 << 1) | 1);
    }
};