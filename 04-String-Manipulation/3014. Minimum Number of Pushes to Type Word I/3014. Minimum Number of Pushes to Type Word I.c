class Solution(object):
    def minimumPushes(self, word):
        n=len(word)
        if n<=8:
            return n
        elif n>8 and n<=16:
            x=n-8
            if x<=8:
                return 8+x*2
        elif n>16 and n<=24:
            x3=n-16
            return 24+x3*3
        elif n>24 and n<=26:
            x4=n-24
            return 48+x4*4

