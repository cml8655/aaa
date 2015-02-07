package cn.com.cml.dbl.receiver;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.com.cml.dbl.helper.NotificationHelper;
import cn.com.cml.dbl.service.RingtoneService_;
import cn.com.cml.dbl.util.PrefUtil_;

@EReceiver
public class ShoutdownReceiver extends BroadcastReceiver {

	private static final String TAG = "ShoutdownReceiver";
	public static final String ACTION_SHOUT_CANCEL = "cn.com.cml.dbl.receiver.ShoutdownReceiver.ACTION_SHOUT_CANCEL";
	private static final String SYSTEM_DIALOG_REASON_KEY = "reason";

	@Pref
	PrefUtil_ pref;

	@SystemService
	KeyguardManager keyguardManager;

	@Override
	public void onReceive(final Context context, Intent intent) {

		Log.d(TAG, intent.getAction());

		final String action = intent.getAction();

		NotificationHelper notifyHelper = new NotificationHelper();

		if (ACTION_SHOUT_CANCEL.equals(action)) {// 取消关机警报

			RingtoneService_.intent(context).stop();
			notifyHelper.removeNotify(context);

		}

		// 没有开启关机警报
		if (!pref.shoutdownAlarm().get()) {
			return;
		}

		if (action.equals(Intent.ACTION_SHUTDOWN)) {

			RingtoneService_.intent(context).start();
			notifyHelper.addRingtoneStartNotify(context, ACTION_SHOUT_CANCEL);

		} else if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

			String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

			if (reason != null && reason.equals("globalactions")
					&& keyguardManager.inKeyguardRestrictedInputMode()) {
				RingtoneService_.intent(context).start();
				notifyHelper.addRingtoneStartNotify(context,
						ACTION_SHOUT_CANCEL);
			}
		}
	}

}
