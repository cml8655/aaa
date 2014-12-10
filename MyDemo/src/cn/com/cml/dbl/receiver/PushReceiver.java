package cn.com.cml.dbl.receiver;

import cn.bmob.push.PushConstants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PushReceiver extends BroadcastReceiver {

	private static final String TAG = "PushReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

			Log.d(TAG,
					"接到消息推送："
							+ intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
		}
	}

}
