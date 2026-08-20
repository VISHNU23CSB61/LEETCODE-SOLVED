1class Solution {
2    public int[] resultArray(int[] nums) {
3        int[] arr1 = new int[nums.length];
4        int[] arr2 = new int[nums.length];
5        int size1 = 1;
6        int size2 = 1;
7        arr1[0] = nums[0];
8        arr2[0] = nums[1];
9        for (int i = 2; i < nums.length; i++) {
10            if (arr1[size1 - 1] > arr2[size2 - 1]) {
11                arr1[size1++] = nums[i];
12            } else {
13                arr2[size2++] = nums[i];
14            }
15        }
16        int[] result = new int[nums.length];
17        System.arraycopy(arr1, 0, result, 0, size1);
18        System.arraycopy(arr2, 0, result, size1, size2);
19        return result;
20    }
21}