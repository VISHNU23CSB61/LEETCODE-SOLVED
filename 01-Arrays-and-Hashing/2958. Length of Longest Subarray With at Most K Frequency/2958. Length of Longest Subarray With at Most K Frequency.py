class Solution {

    public int maxSubarrayLength(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int max = 0;
        int right = 0;
        for(; right <= nums.length; right++){

            if(right == nums.length){
                max = Math.max(max, right - left);
                continue;
            }

            int num = nums[right];
            map.put(num, map.getOrDefault(num,0)+1);

            int fre = map.get(num);


            if(fre <= k) continue;

            max = Math.max(max, right - left);

            while(nums[left] != num){
                map.put(nums[left], map.get(nums[left])-1);
                left++;
            }
            left++;
            map.put(num, fre-1);
        }
        
        return max;
    }
}