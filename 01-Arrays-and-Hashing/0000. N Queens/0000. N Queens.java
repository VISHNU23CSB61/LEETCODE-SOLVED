1class Solution {
2    public List<List<String>> solveNQueens(int n) {
3        List<List<String>> result=new ArrayList<>();
4        int[] queen=new int[n];
5        Set<Integer> cols=new HashSet<>();
6        Set<Integer> ldia=new HashSet<>();
7        Set<Integer> rdia=new HashSet<>();
8        backtrack(n,0,queen,cols,ldia,rdia,result);
9        return result;
10    }
11
12    private void backtrack(int n,int row,int[] queen,Set<Integer> cols,Set<Integer> ldia,Set<Integer> rdia,List<List<String>> result){
13        if(row==n){
14            result.add(generateboard(n,queen));
15            return;
16        }
17        for(int col=0;col<n;col++){
18            if(cols.contains(col)||ldia.contains(row+col)||rdia.contains(row-col)){
19                continue;
20            }
21            queen[row]=col;
22            cols.add(col);
23            ldia.add(row+col);
24            rdia.add(row-col);
25
26            backtrack(n,row+1,queen,cols,ldia,rdia,result);
27
28            cols.remove(col);
29            ldia.remove(row+col);
30            rdia.remove(row-col);
31
32        }
33    }
34    private List<String> generateboard(int n,int[] queen){
35        List<String> board=new ArrayList<>();
36        for(int i=0;i<n;i++){
37            char[] row=new char[n];
38            Arrays.fill(row,'.');
39            row[queen[i]]='Q';
40            board.add(new String(row));
41        }return board;
42    }
43}