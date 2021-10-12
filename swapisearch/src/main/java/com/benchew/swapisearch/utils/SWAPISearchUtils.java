package com.benchew.swapisearch.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SWAPISearchUtils {
    public static String urlEncode(String aString) {
        try {
            return URLEncoder.encode(aString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
