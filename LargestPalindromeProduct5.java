public static long LargestPalindromeProduct(int length) {

    long left = 1, right, lowerBound;

    for (int i = 0; i < length; i++)
        left *= 10;

    lowerBound = left / 10;

    right = --left;

    final long initialRight = right;

    long largestPalindrome = 0;

    while (left >= lowerBound) {

        if (largestPalindrome > (right * left))
            return largestPalindrome;

        while (right >= left) {

            if (IsPalindrome(right * left))
                largestPalindrome = right * left;

            right--;

        }

        right = initialRight;
        left--;

    }

    return -1;

}
