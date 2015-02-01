package cn.com.cml.dbl.helper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

@EBean
public class AlarmHelper implements OnLoadCompleteListener {

	private static final int PRIORITY = 100;
	@RootContext
	Context context;

	private SoundPool soundPool;
	private int soundId;
	private boolean isPlay;

	public void play(int sourceId) {
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		soundId = soundPool.load(context, sourceId, PRIORITY);
		soundPool.setLoop(soundId, -1);
		soundPool.setOnLoadCompleteListener(this);
	}

	public void release() {
		if (null != soundPool) {
			soundPool.autoPause();
			soundPool.release();
			soundPool = null;
		}
	}

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		if (status == 0) {
			int playStatus = soundPool.play(soundId, 1.0f, 1.0f, PRIORITY, -1,
					1.0f);
			isPlay = playStatus != 0;
		}
	}

	public boolean isPlay() {
		return isPlay;
	}

}
