package cn.com.cml.dbl.service;

import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import cn.com.cml.dbl.util.PrefUtil_;
import cn.com.cml.dbl.util.VolumeUtil;

/**
 * 震动，并且播放默认手机铃声提示
 * 
 * @author 陈孟琳
 * 
 *         2014年11月13日
 */
@EService
public class RingtoneService extends Service {

	public static final String ACTION_NOISY = "cn.com.cml.pets.service.RingtoneService.noisy";

	private long[] vibratorPattern = { 1000, 3000, 1000, 3000, 1000 };

	private boolean isVirbrating;
	private boolean isRinging;

	@SystemService
	Vibrator vibrator;

	@Bean
	VolumeUtil volumeUtil;

	@Pref
	PrefUtil_ prefUtil;

	private Timer volumeTimer;

	private Ringtone mRingtone;

	@Override
	public void onCreate() {
		super.onCreate();
		mRingtone = this.getDefaultRingtone();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// 已经在播放了，直接返回
		if (isRinging || isVirbrating) {
			return super.onStartCommand(intent, flags, startId);
		}

		Log.d("RingtoneService", "ringtoneService 播放了！");

		// 存储原有音量大小
		backupVolume();

		volumeUtil.noisy();

		// 播放铃声
		if (null != mRingtone && !mRingtone.isPlaying()) {
			mRingtone.play();
			isRinging = true;
		}

		// 开始震动
		if (null != vibrator && vibrator.hasVibrator()) {
			vibrator.vibrate(vibratorPattern, 0);
			isVirbrating = true;
		}

		if (null == volumeTimer) {
			volumeTimer = new Timer();
			volumeTimer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					volumeUtil.noisy();
				}
			}, 0, 500);
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {

		Log.d("RingtoneService", "ringtoneService 销毁了！");

		volumeTimer.cancel();
		volumeTimer = null;

		// 还原手机音量大小
		restoreVolume();

		// 关闭铃声播放
		if (isRinging) {
			mRingtone.stop();
			isRinging = false;
		}

		if (isVirbrating) {
			vibrator.cancel();
			isVirbrating = false;
		}

		super.onDestroy();
	}

	/** 记录音量原有设置 */
	private void backupVolume() {

		int ringtoneValue = volumeUtil.getRingVolume();
		int mediaValue = volumeUtil.getMediaVolume();

		prefUtil.edit().ringtoneVolume().put(ringtoneValue).mediaVolume()
				.put(mediaValue).apply();

	}

	private void restoreVolume() {

		// 手机铃声
		volumeUtil.setStreamVolume(prefUtil.ringtoneVolume().get(),
				AudioManager.STREAM_RING);
		// 媒体音量
		volumeUtil.setStreamVolume(prefUtil.mediaVolume().get(),
				AudioManager.STREAM_MUSIC);

	}

	private Ringtone getDefaultRingtone() {

		Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_RINGTONE);

		return RingtoneManager.getRingtone(this, ringtoneUri);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
