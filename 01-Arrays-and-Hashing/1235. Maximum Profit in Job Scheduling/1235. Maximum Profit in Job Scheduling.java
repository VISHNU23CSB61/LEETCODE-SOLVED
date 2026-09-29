1class Solution {
2    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
3        int n=startTime.length;
4        int[][] jobs=new int[n][3];
5        for(int i=0;i<n;i++){
6            jobs[i]=new int[]{startTime[i],endTime[i],profit[i]};
7        }
8
9        Arrays.sort(jobs,(a,b)->a[1]-b[1]);
10        TreeMap<Integer,Integer> dp = new TreeMap<>();
11        dp.put(0,0);
12
13        for(int[] job:jobs){
14            int val=job[2]+dp.floorEntry(job[0]).getValue();
15            if(val>dp.lastEntry().getValue()){
16                dp.put(job[1],val);
17            }
18        }return dp.lastEntry().getValue();
19
20    }
21}