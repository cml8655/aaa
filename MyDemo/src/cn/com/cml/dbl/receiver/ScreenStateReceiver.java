package cn.com.cml.dbl.receiver;

import org.androidannotations.annotations.EReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.com.cml.dbl.service.GlobalService;

@EReceiver
public class ScreenStateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())
				|| Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

			Intent intentService = new Intent(context, GlobalService.class);
			context.startService(intentService);
		}

	}

}
