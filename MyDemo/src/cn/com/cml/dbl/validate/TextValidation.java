package cn.com.cml.dbl.validate;

import android.text.TextUtils;

public class TextValidation implements Validation {

	@Override
	public boolean isEmail(String email) {
		return false;
	}

	@Override
	public boolean isEmpty(String text) {
		return TextUtils.isEmpty(text);
	}

	@Override
	public boolean hasCharactor(String text) {

		if (this.isEmpty(text)) {
			return false;
		}

		return !isEmpty(text.trim());
	}

	@Override
	public boolean isEquals(String t1, String t2) {

		if (isEmpty(t1) || isEmpty(t2)) {
			return false;
		}

		return t1.trim().equals(t2.trim());
	}

}
