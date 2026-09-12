1
2public class Solution {
3
4    public class Cell {
5        int row;
6        int col;
7        int height;
8        public Cell(int row, int col, int height) {
9            this.row = row;
10            this.col = col;
11            this.height = height;
12        }
13    }
14
15    public int trapRainWater(int[][] heights) {
16        if (heights == null || heights.length == 0 || heights[0].length == 0)
17            return 0;
18
19        PriorityQueue<Cell> queue = new PriorityQueue<>(1, new Comparator<Cell>(){
20            public int compare(Cell a, Cell b) {
21                return a.height - b.height;
22            }
23        });
24        
25        int m = heights.length;
26        int n = heights[0].length;
27        boolean[][] visited = new boolean[m][n];
28
29        // Initially, add all the Cells which are on borders to the queue.
30        for (int i = 0; i < m; i++) {
31            visited[i][0] = true;
32            visited[i][n - 1] = true;
33            queue.offer(new Cell(i, 0, heights[i][0]));
34            queue.offer(new Cell(i, n - 1, heights[i][n - 1]));
35        }
36
37        for (int i = 0; i < n; i++) {
38            visited[0][i] = true;
39            visited[m - 1][i] = true;
40            queue.offer(new Cell(0, i, heights[0][i]));
41            queue.offer(new Cell(m - 1, i, heights[m - 1][i]));
42        }
43
44        // from the borders, pick the shortest cell visited and check its neighbors:
45        // if the neighbor is shorter, collect the water it can trap and update its height as its height plus the water trapped
46       // add all its neighbors to the queue.
47        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
48        int res = 0;
49        while (!queue.isEmpty()) {
50            Cell cell = queue.poll();
51            for (int[] dir : dirs) {
52                int row = cell.row + dir[0];
53                int col = cell.col + dir[1];
54                if (row >= 0 && row < m && col >= 0 && col < n && !visited[row][col]) {
55                    visited[row][col] = true;
56                    res += Math.max(0, cell.height - heights[row][col]);
57                    queue.offer(new Cell(row, col, Math.max(heights[row][col], cell.height)));
58                }
59            }
60        }
61        
62        return res;
63    }
64}