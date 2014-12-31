package cn.com.cml.dbl.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

	public static boolean isNetwork(Context context, int type) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = cm.getActiveNetworkInfo();

		if (null != info) {
			return info.getType() == type;
		}

		return false;
	}

	public static boolean isNetworkActive(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = cm.getActiveNetworkInfo();

		if (null != info) {
			return info.isConnected() && info.isAvailable();
		}

		return false;
	}
}
