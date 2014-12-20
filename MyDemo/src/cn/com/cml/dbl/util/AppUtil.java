package cn.com.cml.dbl.util;

import java.util.List;

import cn.com.cml.dbl.WindowAlarmActvity_;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.FragmentActivity;

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

	public static boolean isOnTop(Class<? extends FragmentActivity> clazz,
			ActivityManager acManager) {

		List<RunningTaskInfo> tasks = acManager.getRunningTasks(1);

		RunningTaskInfo task = tasks.get(0);

		return task.topActivity.getClassName().equals(clazz.getName());

	}
}
