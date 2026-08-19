1class Solution {
2    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
3        Map<Integer,Integer> r=new HashMap<>();
4        for(int[] seat:reservedSeats){
5            int row=seat[0];
6            int col=seat[1];
7            if(col>=2 &&col<=9){
8                r.put(row,r.getOrDefault(row,0)|(1<<col));
9            }
10        }
11        int totalfam=(n-r.size())*2;
12        int leftMask = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
13        int MiddleMask =(1<<4)|(1<<5)|(1<<6)|(1<<7);
14        int rightMask=(1<<6)|(1<<7)|(1<<8)|(1<<9);
15
16        for(int mask:r.values()){
17            boolean leftf=(mask&leftMask)==0;
18            boolean rightf=(mask&rightMask)==0;
19            boolean middlef=(mask&MiddleMask)==0;
20            if (leftf && rightf) {
21                totalfam += 2;
22            } else if (leftf || rightf || middlef) {
23                totalfam += 1;
24            }
25        }
26        return totalfam;
27    }
28}