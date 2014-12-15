package cn.com.cml.dbl.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {

	private static final int SERVICE_LIMIT = 50;

	public static String version(Context context) {

		try {

			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);

			return info.versionName;

		} catch (NameNotFoundException e) {
		}

		return null;

	}

	public static boolean serviceRunning(Class<? extends Service> service,
			Context context) {

		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<RunningServiceInfo> infos = manager
				.getRunningServices(SERVICE_LIMIT);

		for (RunningServiceInfo info : infos) {

			if (info.service.getClassName().equals(service.getName())) {

				return info.started;
			}

		}

		return false;
	}
}
