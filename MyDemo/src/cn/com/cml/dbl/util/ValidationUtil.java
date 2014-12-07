package cn.com.cml.dbl.util;

import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 文本校验
 * 
 * @author Administrator
 * 
 */
public class ValidationUtil {

	public static final String EMAIL_REGULAR = "\\w+@\\w+\\.\\w+";

	public static boolean equals(String s1, String s2) {

		if (TextUtils.isEmpty(s1)) {
			return TextUtils.isEmpty(s2);
		}
		
		return s1.equals(s2);
	}

	public static boolean isLength(String str, int len) {

		if (null == str) {
			return false;
		}

		return str.trim().length() == len;
	}

	public static boolean isGELength(String str, int len) {

		if (null == str) {
			return false;
		}

		return str.trim().length() >= len;
	}

	public static boolean hasCharacter(String str) {

		if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim())) {
			return false;
		}

		return true;
	}

	public static boolean isEmail(String str) {

		if (TextUtils.isEmpty(str)) {
			return false;
		}

		return Pattern.compile(EMAIL_REGULAR).matcher(str).find();
	}

}
