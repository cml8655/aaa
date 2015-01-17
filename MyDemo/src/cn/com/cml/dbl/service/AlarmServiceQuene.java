package cn.com.cml.dbl.service;

import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import cn.com.cml.dbl.WindowAlarmActvity_;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.util.AppUtil;

@EService
public class AlarmServiceQuene extends Service {

	public static final String EXTRA_TYPE = "MESSAGE_TYPE";

	@SystemService
	ActivityManager acManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private Timer timer = new Timer();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("PushReceiver", "onCreate启动警报服务。。。");
	}

	@SystemService
	PowerManager m;

	WakeLock lock;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (null != intent && intent.hasExtra(EXTRA_TYPE)) {

			int type = intent.getIntExtra(EXTRA_TYPE, -1);

			switch (type) {

			case Constant.Alarm.TYPE_COMMAND:
				timer.scheduleAtFixedRate(new AlarmTimerTask(type), 0, 1000);
				break;

			case Constant.Alarm.TYPE_SMS:

				break;

			default:
				break;
			}

			lock = m.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.SCREEN_DIM_WAKE_LOCK, "pow bright");
			lock.acquire();
		}

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		lock.release();
		timer.cancel();
		RingtoneService_.intent(getApplicationContext()).stop();
		WindowAlarmService_.intent(getApplicationContext()).stop();
		super.onDestroy();
	}

	// 进行任务轮训
	private class AlarmTimerTask extends TimerTask {

		private int type;

		private AlarmTimerTask(int type) {
			super();
			this.type = type;
		}

		@Override
		public void run() {

			if (!AppUtil.isOnTop(WindowAlarmActvity_.class, acManager)) {

				WindowAlarmActvity_.intent(getApplicationContext())
						.flags(Intent.FLAG_ACTIVITY_NEW_TASK).alarmType(type)
						.start();
			}

			if (!AppUtil.serviceRunning(RingtoneService_.class,
					getApplicationContext())) {
				RingtoneService_.intent(getApplicationContext()).start();
			}

		}

	}

}
