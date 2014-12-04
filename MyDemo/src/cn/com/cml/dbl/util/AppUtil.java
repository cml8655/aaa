package cn.com.cml.dbl.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {

	public static String version(Context context) {

		try {

			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);

			return info.versionName;

		} catch (NameNotFoundException e) {
		}

		return null;

	}
}
