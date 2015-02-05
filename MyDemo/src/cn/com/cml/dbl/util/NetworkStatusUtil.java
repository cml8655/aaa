package cn.com.cml.dbl.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

public class NetworkStatusUtil {

	public static void openWifiIfClosed(Context context) {

		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		int wifiState = wifiManager.getWifiState();

		if (wifiState != WifiManager.WIFI_STATE_ENABLED) {
			wifiManager.setWifiEnabled(true);
		}
	}

	public static boolean getMobileDataStatus(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		@SuppressWarnings("rawtypes")
		Class cmClass = cm.getClass();
		@SuppressWarnings("rawtypes")
		Class[] argClasses = null;
		Object[] argObject = null;
		Boolean isOpen = false;
		try {

			@SuppressWarnings("unchecked")
			Method method = cmClass.getMethod("getMobileDataEnabled",
					argClasses);

			isOpen = (Boolean) method.invoke(cm, argObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isOpen;
	}

	// 移动数据开启和关闭
	public static  void setMobileDataStatus(Context context, boolean enabled) {
		
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		Class<?> conMgrClass = null;

		// ConnectivityManager类中的字段
		Field iConMgrField = null;
		// IConnectivityManager类的引用
		Object iConMgr = null;
		// IConnectivityManager类
		Class<?> iConMgrClass = null;
		// setMobileDataEnabled方法
		Method setMobileDataEnabledMethod = null;
		try {

			// 取得ConnectivityManager类
			conMgrClass = Class.forName(cm.getClass().getName());
			// 取得ConnectivityManager类中的对象Mservice
			iConMgrField = conMgrClass.getDeclaredField("mService");
			// 设置mService可访问
			iConMgrField.setAccessible(true);
			// 取得mService的实例化类IConnectivityManager
			iConMgr = iConMgrField.get(cm);
			// 取得IConnectivityManager类
			iConMgrClass = Class.forName(iConMgr.getClass().getName());

			// 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
			setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);

			// 设置setMobileDataEnabled方法是否可访问
			setMobileDataEnabledMethod.setAccessible(true);
			// 调用setMobileDataEnabled方法
			setMobileDataEnabledMethod.invoke(iConMgr, enabled);

		}

		catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (NoSuchFieldException e) {

			e.printStackTrace();
		}

		catch (SecurityException e) {
			e.printStackTrace();

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		catch (InvocationTargetException e) {

			e.printStackTrace();

		}

	}
}