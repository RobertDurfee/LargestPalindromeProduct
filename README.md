# LargestPalindromeProduct
A Java implementation of a general solution to Project Euler problem 4.

### Problem

Problem given on Project Euler: 
```
A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit 
numbers is 9009 = 91 × 99.

Find the largest palindrome made from the product of two 3-digit numbers.
```

I decided to make it more interesting by generalizing it to two `n`-digit numbers. It quickly became known that cycling through all products was too time consuming. Therefore, a more intelligent solution must be devised.

### Method 1
```Java
public static long LargestPalindromeProduct(int length) {

    long left = 1, right;

    for (int i = 0; i < length; i++)
        left *= 10;

    right = --left;

    final long initialRight = right;

    while (left >= 0) {

        while (right >= left) {

            if (IsPalindrome(right * left))
                return right * left;

            right--;

        }

        right = initialRight;
        left--;

    }

    return -1;

}
```
This method makes several oversights and incorrect assumptions. Firstly, in the worst-case, it will calculate every product up to `10^n - 1` rather than only `n`-digit products (from `10^(n - 1)` to `10^n - 1`). This doesn't really matter, though, as the lower bound will never be reached. Still, it is lazy practice. Also, this makes the assumption that the calculated products are always decreasing. Originally, I thought this were the case. However, the products quickly overlap each cycle. Therefore, the output palindromic product may not be the largest. Shown below:

![Approximation 1 Output](https://github.com/RobertDurfee/SequentialProducts/raw/master/Plots/ProductOutputApproximation1.png)

### Method 2
```Java
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
```
After studying the sorted output of products, I noticed that there was a distinct pattern that could be easily replicated. I think the pattern is best explained by examining the code above. This pattern works great until the 10^n product is calculated. Then the pattern falls apart and calculated products are no longer decreasing. Shown below.

![Approximation 2 Output](https://github.com/RobertDurfee/SequentialProducts/raw/master/Plots/ProductOutputApproximation2.png)

The iteration maximum was determined by realizing that the number of products is a permutation of `n` and 10 (the number of possible digit values) and that each iteration computes that number of products. Using the summation of constants identity, the number of iterations could be determined. This is not very important, however, as this maximum should never be reached.

### Method 3
```Java
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
```
This method takes advantage of calculus and linear approximations using differentials. We have a smooth function `z = x * y` and we want to iterate through each integer product by decreasing x and/or y by an integer value. The differential equation to use is `dz = x * dy + y * dx`. We can decrease the number of iterations by a factor of two by realizing that the graph of `z = x * y` is symmetrical along the `x = y` line. Unfortunately, the product output is exactly the same as Method 2. This is because the linear iteration across each input to the differential equation is not suitable to generate sucessive products as the intersection of `z = x * y` function and the plane `z = c` where `c` is a constant is not approximately linear as the `x`'s and `y`'s become smaller. Also, this method requires more computations therefore it is much slower.

### Method 4
```Java
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
```
This method is exploiting exactly the same pattern as in Method 2. It is a little easier to follow, however, as standard structures are used. This method was discovered by looking at a binary tree of inputs to the product where a pattern was visible. The output was exactly the same as Method 2 and Method 3. It is also about as fast as Method 2. Therefore, it is clear that the pattern breaks down.

### Method 5
```Java
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
```
This is the first method that is guaranteed to output the largest palindrome. By more closely examining the graph from Method 1, it can be seen that eventually, when the iteration's first product is less than the largest palindrome calculated, it is clear that no other product generated after that will be greater. This is significantly faster than an approach where every product must be calculated, but as `n` increases, the outputs products begin to have a slope approaching 0. As a result, more products must be calculated after the largest palindrome is found as n increases. This becomes quite noticeable right away even when `n = 9`.

### Conclusion

If a reliable method for computing sucessive products can be determined, then this problem can be computed in less time. This problem is being explored [here](https://github.com/RobertDurfee/SequentialProducts).
