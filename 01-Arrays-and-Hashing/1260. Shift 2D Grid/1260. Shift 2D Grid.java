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