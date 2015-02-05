package cn.com.cml.dbl.util;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface PrefUtil {
	/**
	 * 关机警报
	 */
	@DefaultBoolean(true)
	boolean shoutdownAlarm();

	@DefaultBoolean(true)
	boolean smsAlaram();

	@DefaultBoolean(true)
	boolean smsLocation();

	/**
	 * 记住密码
	 */
	boolean rememberPass();

	String username();

	String password();

	/** 手机铃声音量大小 */
	int ringtoneVolume();

	/** 媒体音量大小 */
	int mediaVolume();

	/** 欢迎版本 */
	String introduceVersion();

	String commandFromUsername();

	/**
	 * 是否绑定了此手机
	 * 
	 * @return
	 */
	@DefaultBoolean(false)
	boolean isBindDevice();

	/** 同步日期 */
	long syncDateMills();

}
