package com.example.lodge.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    static final String PASSWORDPATTERN="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";


    public static boolean isPasswordValid(String password)
    {
        Pattern pattern=Pattern.compile(PASSWORDPATTERN);
        Matcher matcher=pattern.matcher(password);
        return matcher.matches();
    }
}
