1class Solution {
2    public int canCompleteCircuit(int[] gas, int[] cost) {
3        int totalGas = 0, totalCost = 0;
4        for (int i = 0; i < gas.length; i++) {
5            totalGas += gas[i];
6            totalCost += cost[i];
7        }
8
9        if (totalGas < totalCost) {
10            return -1;
11        }
12
13        int currentGas = 0;
14        int start = 0;
15
16        for (int i = 0; i < gas.length; i++) {
17            currentGas += gas[i] - cost[i];
18            if (currentGas < 0) {
19                currentGas = 0;
20                start = i + 1;
21            }
22        }
23
24        return start;        
25    }
26}