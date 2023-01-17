package com.example.inventory.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    static final String PASSWORDPATTERN="^(?=^.{8,20}$)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&+=]).*$";
    static final String SHORTNAMEPATTERN="^.{3,}$";

    public static boolean isPasswordValid(String password)
    {
        Pattern pattern=Pattern.compile(PASSWORDPATTERN);
        Matcher matcher=pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isShortNameValid(String shorName) {
        Pattern pattern=Pattern.compile(SHORTNAMEPATTERN);
        Matcher matcher=pattern.matcher(shorName);
        return matcher.matches();
    }
}
