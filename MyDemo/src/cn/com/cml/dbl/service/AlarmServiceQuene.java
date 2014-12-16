package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

@EService
public class AlarmServiceQuene extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private static Handler handler = new Handler();
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("PushReceiver", "onCreate启动警报服务。。。");
	}

	private Runnable alaramTask = new Runnable() {

		@Override
		public void run() {
			RingtoneService_.intent(getApplicationContext()).start();
			WindowAlarmService_.intent(getApplicationContext()).start();
			handler.postDelayed(alaramTask, 1000);
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.d("PushReceiver", "启动警报服务。。。");

		handler.post(alaramTask);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacks(alaramTask);
		handler = null;
		RingtoneService_.intent(getApplicationContext()).stop();
		WindowAlarmService_.intent(getApplicationContext()).stop();
		super.onDestroy();
	}

}
