public static long LargestPalindromeProduct(int length) {

    long initialLeft = 1, initialRight;

    for (int i = 0; i < length; i++)
        initialLeft *= 10;

    initialRight = --initialLeft;

    LinkedList<Long> Left = new LinkedList<Long>();
    LinkedList<Long> Right = new LinkedList<Long>();

    Left.add(initialLeft);
    Right.add(initialRight);

    while (Left.peek() > 0 && Right.peek() > 0) {

        long initialSize = Left.size();

        for (int i = 0; i < initialSize; i++) {

            long left = Left.remove();
            long right = Right.remove();

            if (left != right && i == 0) {

                if (IsPalindrome(left * (right - 1)))
                    return (left * (right - 1));

                Left.add(left);
                Right.add(right - 1);

                if (IsPalindrome(right * (left - 1)))
                    return (right * (left - 1));

                Left.add(left - 1);
                Right.add(right);

            } else {

                if (IsPalindrome(right * (left - 1)))
                    return (right * (left - 1));

                Left.add(left - 1);
                Right.add(right);

            }

        }
    }

    return -1;

}
