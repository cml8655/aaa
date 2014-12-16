package cn.com.cml.dbl.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.renderscript.Program.TextureType;
import android.text.TextUtils;
import android.util.Log;

public class CommonUtils {

	private static final String SHA1 = "SHA-1";

	public static final String FORMAT_YMD = "yyyyMMdd";
	public static final String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	public static String formatDate(Date date, String format) {

		SimpleDateFormat formater = new SimpleDateFormat(format);

		return formater.format(date);
	}

	public static Date parseDate(String date, String format, Date defaultDate) {

		if (TextUtils.isEmpty(date)) {
			return defaultDate;
		}

		try {

			SimpleDateFormat formater = new SimpleDateFormat(format);

			return formater.parse(date);
		} catch (ParseException e) {
			Log.e("CommonUtils.parseDate", "ParseException", e);
		}

		return defaultDate;

	}

	public static boolean isDateBefore(Date date, int before) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.DAY_OF_MONTH, -before);

		return date.compareTo(calendar.getTime()) == 0;

	}

	public static boolean isNetworkConnected(Context context) {

		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
		}
		return false;
	}

	/**
	 * sha1散列加密
	 * 
	 * @param strSrc
	 * @return
	 */
	public static String encodeSHA1(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance(SHA1);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

}
