package com.oclet.utils;

public class StringMaskerUtils {
    //no point bringing in another library just for the empty string!
    public static final String EMPTY_STRING = "";
    public static String maskPhoneNumber(String phoneNumber) {
        return phoneNumber == null ? EMPTY_STRING : phoneNumber.replaceAll(".(?=.{4})", "#");
    }
}
