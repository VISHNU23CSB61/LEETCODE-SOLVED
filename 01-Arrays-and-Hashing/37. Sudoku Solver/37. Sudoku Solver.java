1class Solution {
2    public void solveSudoku(char[][] board) {
3        solver(board, 0, 0);
4    }
5    private boolean solver(char[][] board, int row, int col){
6        if(row == 9){
7            return true;
8        }
9
10        int nextRow = row;
11        int nextCol = col + 1;
12        if(nextCol == 9){
13            nextRow = row + 1;
14            nextCol = 0;
15        }
16
17        if(board[row][col] != '.'){
18            return solver(board, nextRow, nextCol);
19        }
20
21        for(char i = 1; i <= 9; i++){
22            if(safe(board, row, col, i)){
23                board[row][col] = (char) ('0' + i);
24
25                if(solver(board, nextRow, nextCol)){
26                    return true;
27                }
28
29                board[row][col] = '.';
30            }
31        }
32        return false;
33    }
34
35    private boolean safe(char[][] board, int row, int col, int val){
36        for(int i = 0; i < 9; i++){
37            if(board[row][i] == (char) ('0' + val)){
38                return false;
39            }
40        }
41
42        for(int i = 0; i < 9; i++){
43            if(board[i][col] == (char) ('0' + val)){
44                return false;
45            }
46        }
47
48        int sr = (row / 3) * 3;
49        int sc = (col / 3) * 3;
50
51        for(int i = sr; i < sr + 3; i++){
52            for(int j = sc; j < sc + 3; j++){
53                if(board[i][j] == (char) ('0' + val)){
54                    return false;
55                }
56            }
57        }
58
59        return true;
60    }
61}