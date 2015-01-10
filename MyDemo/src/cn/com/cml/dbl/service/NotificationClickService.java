package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cn.bmob.v3.BmobUser;
import cn.com.cml.dbl.MainActivity_;
import cn.com.cml.dbl.SplashActivity_;

@EService
public class NotificationClickService extends Service {

	@SystemService
	ActivityManager acManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (BmobUser.getCurrentUser(getApplicationContext()) != null) {
			MainActivity_.intent(getApplicationContext())
					.flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
		} else {
			SplashActivity_.intent(getApplicationContext())
					.flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
		}

		stopSelf();

		return super.onStartCommand(intent, flags, startId);
	}

}
