package cn.com.cml.dbl.util;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

@EBean
public class RingtoneUtil {

	@RootContext
	Context context;

	private Ringtone mRingtone;

	public void initDefaultRingtone() {

		Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context,
				RingtoneManager.TYPE_RINGTONE);

		mRingtone = RingtoneManager.getRingtone(context, ringtoneUri);

	}

	public void playRingtone() {

		if (null != mRingtone && !mRingtone.isPlaying()) {
			mRingtone.play();
		}

	}

	public void stopRingtone() {

		if (null != mRingtone && mRingtone.isPlaying()) {
			mRingtone.stop();
		}

	}
}
