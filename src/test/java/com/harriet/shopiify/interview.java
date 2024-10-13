package com.harriet.shopiify;

import com.jayway.jsonpath.internal.function.numeric.Sum;
import org.hibernate.result.Output;
import org.xmlunit.builder.Input;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class interview {

    public static void main(String args[]) {
        solution(new int[]{1, 2});
    }


    private static void solution(int[] nums) {
       int[] res = {};

        System.out.println(Arrays.toString(res));
    }

//    eg. "12345674", "378282246310005", "79927398714"
//
//    Given a large number we need to find if it satisfies luhn's algorithm
//    the algorithm states that the last digit in the input is the checksum.
//    Double every alternate digit starting with the last and sum its digits.
//    Sum up all the digits excluding the checksum and Calculate (10 - (Sum mod 10)) mod 10.
//            if its value matches the last digit then the number passes the luhn's algorithm.

}
//    eg. "12345674", "378282246310005", "79927398714"
//
//    Given a large number we need to find if it satisfies luhn's algorithm
//    the algorithm states that the last digit in the input is the checksum.

//    Double every alternate digit starting with the last and sum its digits
//    Sum up all the digits excluding the checksum and Calculate (10 - (Sum mod 10)) mod 10.
//            if its value matches the last digit then the number passes the luhn's algorithm.

//    boolean res
//3782 =digit input
//        checksum = last digit aka 2
//        3584 = newdigit
//        => sum all digits 3+5+8= 16 ///
//        (10-(6)) mod 10= 4
//        compare 4 with check sum => false