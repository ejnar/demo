package com.nordea.demo;

public class Demo {

    private static String[] dictionary = new String[] {"base", "cat", "cater", "children", "basement"};


    public static String longestMatchingPrefix(String input) {

        String longest = null;
        int length = 0;
        for (int i=0; i < dictionary.length; i++){
            if(input.startsWith(dictionary[i]) && length < dictionary[i].length()){
                longest = dictionary[i];
                length = dictionary[i].length();
            }
        }

        return longest;
    }

    public static void main(String[] args) {
        testLongestMatchingPrefix();
    }

    public static void testLongestMatchingPrefix() { // all should be true
        System.out.println(longestMatchingPrefix("caterer").equals("cater"));
        System.out.println(longestMatchingPrefix("basemexy").equals("base"));
        System.out.println(longestMatchingPrefix("basement").equals("basement"));
        System.out.println(longestMatchingPrefix("basements").equals("basement"));
        System.out.println(longestMatchingPrefix("child") == null);
    }
}
