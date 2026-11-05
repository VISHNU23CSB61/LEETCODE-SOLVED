from array import array
import numpy as np

class Solution:
    def findTheDistanceValue(self, arr1: List[int], arr2: List[int], d: int) -> int:
        if len(arr1) + len(arr2) < 100:  # small input: numpy's fixed cost outweighs the work
            arr2.sort()
            count, n2 = 0, len(arr2)
            for a in arr1:
                i = bisect_left(arr2, a - d)
                if i == n2 or arr2[i] > a + d:
                    count += 1
            return count
        b = np.sort(np.frombuffer(array('i', arr2 + [10**6]), np.int32))  # sentinel: some b is always >= a - d
        a = np.frombuffer(array('i', arr1), np.int32)
        return int(np.count_nonzero(b[np.searchsorted(b, a - d)] > a + d))