package cn.com.cml.dbl.listener;

import org.androidannotations.annotations.EReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 全局广播监听器，监听手机启动,短信 监听
 * 
 * @author Administrator
 * 
 */
@EReceiver
public class GlobalBaseListener extends BroadcastReceiver {
	public GlobalBaseListener() {

		Log.e("GlobalBaseListener", "收GlobalBaseListener短信");
	}

	private static final String ACTION_SMS = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.e("GlobalBaseListener", "收到短信");

		// if (null == intent) {
		// return;
		// }
		//
		// final String action = intent.getAction();

		Toast.makeText(context, "收到短信了！", Toast.LENGTH_LONG).show();

	}

}
