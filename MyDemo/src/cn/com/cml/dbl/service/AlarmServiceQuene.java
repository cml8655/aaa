package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import cn.com.cml.dbl.WindowAlarmActvity_;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

@EService
public class AlarmServiceQuene extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private Handler handler = new Handler();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("PushReceiver", "onCreate启动警报服务。。。");
	}

	private Runnable alaramTask = new Runnable() {

		@Override
		public void run() {  
			RingtoneService_.intent(getApplicationContext()).start();

			WindowAlarmActvity_.intent(getApplicationContext())
					.flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();

			// WindowAlarmService_.intent(getApplicationContext()).start();
			handler.postDelayed(alaramTask, 1000);
		}
	};

	@SystemService
	PowerManager m;

	WakeLock lock;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d("PushReceiver", "启动警报服务。。。");

		handler.post(alaramTask);

		lock = m.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.SCREEN_DIM_WAKE_LOCK, "pow bright");
		lock.acquire();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		lock.release();
		handler.removeCallbacks(alaramTask);
		handler = null;
		RingtoneService_.intent(getApplicationContext()).stop();
		WindowAlarmService_.intent(getApplicationContext()).stop();
		super.onDestroy();
	}

}
