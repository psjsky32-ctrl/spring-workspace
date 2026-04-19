package com.ktdsuniversity.edu.common.utils;

public class ObjectUtils {

	private ObjectUtils() {}
	
	public static boolean isNull(Object ... object) {
		for (Object obj : object) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotNull(Object ... object) {
		for (Object obj : object) {
			if (obj == null) {
				return false;
			}
		}
		return true;
	}
	
}
