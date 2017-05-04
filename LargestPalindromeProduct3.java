public static long LargestPalindromeProduct(int length) {

    long initialLeft = 1, initialRight;

    for (int i = 0; i < length; i++)
        initialLeft *= 10;

    initialRight = --initialLeft;

    for (long dProduct = 0; dProduct < initialLeft * initialRight; dProduct++)
        for (long dRight = (dProduct / initialLeft) / 2; dRight >= 0; dRight--) {

            double dLeft = (((double) dProduct - ((double) initialLeft * (double) dRight)) / (double) initialRight);

            if (dLeft % 1 == 0) {

                long left = initialLeft - (long) dLeft;
                long right = initialRight - dRight;

                long product = left * right;

                if (IsPalindrome(product))
                    return product;

            }

        }

    return -1;

}
