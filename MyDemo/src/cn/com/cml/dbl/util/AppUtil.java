package cn.com.cml.dbl.util;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class AppUtil {

	private static final int SERVICE_LIMIT = 50;
	private static final String TAG = "AppUtil";

	/** 百度手机卫士 */
	public static final String APP_BAIDU_MOBILE = "cn.opda.a.phonoalbumshoushou";
	/** 360卫士 */
	public static final String APP_360_MOBILE = "com.qihoo360.mobilesafe";
	/** 腾讯手机管家: */
	public static final String APP_TENCENT_MOBILE = "com.tencent.qqpimsecure";
	/** 金山手机卫士 */
	public static final String APP_JINSHAN_MOBILE = "com.ijinshan.mguard";
	/** 猎豹安全大师 */
	public static final String APP_LIEBAO_MOBILE = "com.cleanmaster.security_cn";
	/** 猎豹清理大师 */
	public static final String APP_LIEBAO_CLEAN_MASTER = "com.cleanmaster.mguard_cn";

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

	public static boolean isMIUI(Context context) {

		boolean result = false;

		Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");

		i.setClassName("com.android.settings",
				"com.miui.securitycenter.permission.AppPermissionsEditor");

		if (isIntentAvailable(context, i)) {
			result = true;
		}
		return result;
	}

	public static boolean isIntentAvailable(Context context, Intent intent) {

		PackageManager packageManager = context.getPackageManager();

		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.GET_ACTIVITIES);

		return list.size() > 0;
	}

	public static void setAppPriority(Context context) {

		PackageManager pm = context.getPackageManager();

		PackageInfo info = null;
		try {
			info = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "setAppPriority", e);
		}
		Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
		i.setClassName("com.android.settings",
				"com.miui.securitycenter.permission.AppPermissionsEditor");
		i.putExtra("extra_package_uid", info.applicationInfo.uid);
		try {
			context.startActivity(i);
		} catch (Exception e) {
			Toast.makeText(context, "只有MIUI才可以设置哦", Toast.LENGTH_SHORT).show();
		}
	}

	public static List<String> getInstalledPackages(Context context) {

		List<String> packages = new ArrayList<String>();

		PackageManager pkManager = context.getPackageManager();

		List<ApplicationInfo> apps = pkManager.getInstalledApplications(0);

		for (ApplicationInfo info : apps) {
			packages.add(info.packageName);
		}

		return packages;
	}

	public static boolean getPrefValue(Context context, String key) {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		return prefs.getBoolean(key, false);

	}
}
