package cn.com.cml.dbl;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

@EService
public class HeadsetService extends Service {

	@SystemService
	AudioManager audioManager;

	private BroadcastReceiver headsetReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d("HeadsetReceiver", "注册监听");

		headsetReceiver = new HeadsetReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_HEADSET_PLUG);

		registerReceiver(headsetReceiver, filter);

		audioManager.registerMediaButtonEventReceiver(new ComponentName(
				getPackageName(), HeadsetReceiver.class.getName()));
	}
}
