public static long LargestPalindromeProduct(int length) {

    long left, right = 1;

    for (int i = 0; i < length; i++)
        right *= 10;

    left = right - 1;
    
    final long iterationMaximum = (long) ((Math.sqrt(2 * (right - 1) * right + 1) + 1) / 2);
    
    for (long iteration = 0; iteration < iterationMaximum; iteration++)
        for (int repetition = 0; repetition < 2; repetition++) {
            
            left -= iteration;
            right += (iteration - 1);

            if (IsPalindrome(left * right))
                return left * right;

            for (int i = 0; i < iteration; i++) {

                left++;
                right--;

                if (IsPalindrome(left * right))
                    return left * right;
            }
        }

    return -1;

}
