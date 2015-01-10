package cn.com.cml.dbl.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.com.cml.dbl.service.GlobalService;

/**
 * 启动短信监听服务
 *
 * 2014年12月18日
 */
public class GlobalServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 启动服务
		Intent service = new Intent(context, GlobalService.class);
		context.startService(service);

		Log.d("GlobalServiceReceiver", intent.getAction());
	}

}
