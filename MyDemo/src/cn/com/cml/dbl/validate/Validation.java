package cn.com.cml.dbl.validate;

public interface Validation {

	boolean isEmail(String email);

	boolean isEmpty(String text);

	boolean hasCharactor(String text);

	boolean isEquals(String t1, String t2);
	
}
