package cn.com.cml.dbl.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

@EBean
public class NetworkStatusUtil {

	@SystemService
	WifiManager wifiManager;

	@SystemService
	ConnectivityManager conn;

	private TextView tipView;
	

	public void openNet(View v) {
		
		
		StringBuffer text = new StringBuffer();

		// wifiManager.disconnect();

		// wifiManager.enableNetwork(netId, disableOthers)

		int wifiState = wifiManager.getWifiState();

		if (wifiState != WifiManager.WIFI_STATE_ENABLED) {
			wifiManager.setWifiEnabled(true);
			wifiManager.reconnect();
		}

		List<ScanResult> scanResults = wifiManager.getScanResults();

		for (ScanResult sc : scanResults) {
			text.append("===" + sc.BSSID + "," + sc.level + "\n");
		}

		text.append("===" + wifiManager.isWifiEnabled() + "\n");

		NetworkInfo[] infos = conn.getAllNetworkInfo();

		for (NetworkInfo info : infos) {
			text.append(info.getReason() + "," + info.getExtraInfo() + ","
					+ info.getType() + "," + info.getTypeName() + ","
					+ info.getState().name() + info.isConnected() + "\n");
		}

		tipView.setText(text.toString());

	}

	public void closeNet(View v) {
		setMobileDataStatus(this, true);
	}

	public boolean getMobileDataStatus()

	{

		ConnectivityManager cm;
		cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		Class cmClass = cm.getClass();
		Class[] argClasses = null;
		Object[] argObject = null;
		Boolean isOpen = false;
		try {

			Method method = cmClass.getMethod("getMobileDataEnabled",
					argClasses);

			isOpen = (Boolean) method.invoke(cm, argObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isOpen;

	}

	// 移动数据开启和关闭
	public void setMobileDataStatus(Context context, boolean enabled)

	{

		ConnectivityManager conMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// ConnectivityManager类

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
			conMgrClass = Class.forName(conMgr.getClass().getName());
			// 取得ConnectivityManager类中的对象Mservice
			iConMgrField = conMgrClass.getDeclaredField("mService");
			// 设置mService可访问
			iConMgrField.setAccessible(true);
			// 取得mService的实例化类IConnectivityManager
			iConMgr = iConMgrField.get(conMgr);
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

		} catch (NoSuchMethodException e)

		{
			e.printStackTrace();
		}

		catch (IllegalArgumentException e) {

			e.printStackTrace();
		}

		catch (IllegalAccessException e) {

			e.printStackTrace();
		}

		catch (InvocationTargetException e)

		{

			e.printStackTrace();

		}

	}
}