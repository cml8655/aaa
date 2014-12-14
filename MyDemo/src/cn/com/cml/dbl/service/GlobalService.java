package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EService;

import cn.com.cml.dbl.listener.GlobalBaseListener;
import cn.com.cml.dbl.model.SmsModel;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * app全局后台服务，用于启动短信的监听功能
 * 
 * @author 陈孟琳
 * 
 *         2014年11月13日
 */
@EService
public class GlobalService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();

		// 注册短信数据库监听
		getContentResolver().registerContentObserver(SmsModel.SMS_CONTENT_URI,
				true, new SmsContentObserver(this, new SMSHandler(this)));

		// IntentFilter filter = new IntentFilter(
		// "android.provider.Telephony.SMS_RECEIVED");

		// filter.setPriority(2147483647);

		// registerReceiver(new GlobalBaseListener(), filter);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		GlobalService_.intent(getApplicationContext()).start();
	}

}
