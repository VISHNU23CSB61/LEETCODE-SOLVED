1class Solution {
2    public int[] maxSlidingWindow(int[] nums, int k) {
3        Deque<Integer> d=new ArrayDeque<>();
4        int n=nums.length;
5        int[] res=new int[n-k+1];
6        for(int i=0;i<k;i++){
7            while(!d.isEmpty() && nums[d.peekLast()]<=nums[i]){
8                d.pollLast();
9            }d.offerLast(i);
10        }
11        res[0]=nums[d.peekFirst()];
12
13        for(int i=k;i<n;i++){
14            if(d.peekFirst()<=i-k){
15                d.pollFirst();
16            }
17             while(!d.isEmpty() && nums[d.peekLast()]<=nums[i]){
18                d.pollLast();
19            }d.offerLast(i);
20            res[i-k+1]=nums[d.peekFirst()];
21
22        }return res;
23    }
24}