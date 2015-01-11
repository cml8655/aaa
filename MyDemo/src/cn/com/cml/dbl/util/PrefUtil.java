package cn.com.cml.dbl.util;

import java.util.prefs.Preferences;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface PrefUtil {
	/**
	 * 关机警报
	 */
	@DefaultBoolean(value = true)
	boolean shoutdownAlarm();

	@DefaultBoolean(value = true)
	boolean smsAlaram();

	@DefaultBoolean(value = true)
	boolean smsLocation();

	/**
	 * 记住密码
	 */
	boolean rememberPass();

	String password();

	/** 代码前缀 */
	String prefix();

	/** 震动，播放铃声 */
	String noisyCommand();

	/** 停止震动，播放铃声指令 */
	String quietCommand();

	/** 定位指令 */
	String locationCommand();

	/** 定位信息返回间隔时间，单位（s） */
	int locationInterval();

	/** 手机号码卡变化后接收号码 */
	String simChangeReceiver();

	/** 手机铃声音量大小 */
	int ringtoneVolume();

	/** 媒体音量大小 */
	int mediaVolume();

	/** 欢迎版本 */
	String introduceVersion();

	String username();

	String nickName();

	String commandFromUsername();

}
