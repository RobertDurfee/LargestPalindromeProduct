public static boolean IsPalindrome(long value) {

    long reversed = 0;
    long oldValue = value;

    while (value > 0) {

        reversed = (reversed * 10) + value % 10;
        value /= 10;

    }

    return reversed == oldValue;
    
}
