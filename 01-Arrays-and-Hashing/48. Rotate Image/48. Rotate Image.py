1class Solution:
2    def rotate(self, matrix: list[list[int]]) -> None:
3        
4        Do not return anything, modify matrix in-place instead.
5        
6        n=len(matrix)
7        for i in range(n):
8            for j in range(i+1,n):
9                matrix[i][j],matrix[j][i]=matrix[j][i],matrix[i][j]
10            matrix[i].reverse()
11        return matrix