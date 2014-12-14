package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

@EService
public class AlarmServiceQuene extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private static Handler handler = new Handler();

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

		handler.post(alaramTask);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacks(alaramTask);
		handler = null;
		super.onDestroy();
	}

}
