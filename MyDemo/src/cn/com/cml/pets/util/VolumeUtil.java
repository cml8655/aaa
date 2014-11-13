package cn.com.cml.pets.util;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

import android.media.AudioManager;

@EBean
public class VolumeUtil {

	@SystemService
	AudioManager audioManager;

	public void setStreamVolume(int volumeValue, int streamType) {

		int max = this.getMaxVolume(streamType);

		if (volumeValue > max) {
			max = volumeValue;
		}

		audioManager.setStreamVolume(streamType, volumeValue,
				AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
	}

	/**
	 * 将媒体音量和手机铃声开到最大
	 */
	public void noisy() {

		int maxMedia = getMaxVolume(AudioManager.STREAM_MUSIC);
		int maxRingtone = getMaxVolume(AudioManager.STREAM_RING);

		this.setStreamVolume(maxMedia, AudioManager.STREAM_MUSIC);
		this.setStreamVolume(maxRingtone, AudioManager.STREAM_RING);
	}

	public int getMaxVolume(int streamType) {
		return audioManager.getStreamMaxVolume(streamType);
	}

	public int maxMediaVolume() {
		return this.getMaxVolume(AudioManager.STREAM_MUSIC);
	}

	public int getMediaVolume() {
		return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	public int getRingVolume() {
		return audioManager.getStreamVolume(AudioManager.STREAM_RING);
	}
}
