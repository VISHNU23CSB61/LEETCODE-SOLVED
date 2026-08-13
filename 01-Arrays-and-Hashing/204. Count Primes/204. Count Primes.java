// Seive of Eratosthenes
// Time - O(nloglogn) is standard but some articles says with below optimization, i.e, i<=rootN
// complexity is O(sqrt(n)loglogn)
// Space - O(n)

/**
 * @param {number} n
 * @return {number}
 */
var countPrimes = function(n) {
    if (n <= 2) return 0;
    
    let count = n-2; // Initially we have n-2 primes as 1 and n are excluded
    const rootN = Math.floor(Math.sqrt(n));
    const isPrime = new Array(n).fill(true);
    
    for (let i=2; i<=rootN; i++)
        if (isPrime[i])
            for (let j=i*i; j<n; j+=i)
                if (isPrime[j]) {
                    isPrime[j] = false;
                    count--;
                }
    
    return count;
};