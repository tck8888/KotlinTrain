package com.tck.kotlintrain.juc.cas;

/**
 * @author tck88
 * @date 2022-04-25
 */
public class FinalString {
    public static void main(String[] args) {
        String a = "tck2";
        final String b = "tck";
        String d = "tck";
        String c = b + 2;
        String e = d + 2;
        System.out.println(a == c);
        System.out.println(a == e);
    }
}
