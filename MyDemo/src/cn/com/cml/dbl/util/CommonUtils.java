package cn.com.cml.dbl.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CommonUtils {

	private static final String SHA1 = "SHA-1";

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
