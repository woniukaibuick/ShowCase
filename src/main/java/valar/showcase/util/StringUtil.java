package valar.showcase.util;

import java.util.Collection;

public class StringUtil {
	
	public static boolean isEmpty(String str) {
		return str==null || str.isEmpty();
	}
	
	public static boolean isEmpty(Collection c) {
		return c==null || c.isEmpty();
	}

}
