class Solution {
    public boolean isPerfectSquare(int num) {
        //Pointers for Binary Search
        int left = 1, right = num;
        //Binary Search
        while (left <= right) {
            int mid = (left + right)/2;
            // Necessary to prevent innacurate results for large numbers
            long midSquare = (long) mid * mid;
            //We found our perfect square, return true
            if (midSquare == num) return true;
            //We did not find our target, adjust the pointers
            else if (midSquare > num) right = mid - 1;
            else {
                left = mid + 1;
            }
        }
        //We went through the whole loop and never found a perfect square, so there is none
        return false;
    }
}