package com.ktdsuniversity.edu.common.utils;

public class StringUtils {

	private StringUtils() {}
	
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	public static String emptyTo(String str, String defaultString) {
		if (isEmpty(str)) {
			return defaultString;
		}
		return str;
	}
}
