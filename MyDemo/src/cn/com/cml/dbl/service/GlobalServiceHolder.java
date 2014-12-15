package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EService;

import cn.com.cml.dbl.util.AppUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class GlobalServiceHolder extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (!AppUtil.serviceRunning(GlobalService.class,
				getApplicationContext())) {

			Log.d("Service", "GlobalServiceHolder.onStartCommand,启动global");
			startGlobalService();
		}

		return super.onStartCommand(intent, START_STICKY, startId);
	}

	private void startGlobalService() {

		Intent intent = new Intent(getApplicationContext(), GlobalService.class);
		startService(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		startGlobalService();
	}

}
