package cn.com.cml.dbl.receiver;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import cn.bmob.v3.BmobUser;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.NetworkUtils;

public class NetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

			if (!NetworkUtils.isNetworkActive(context)) {

				// 显示在用户界面
				if (null != BmobUser.getCurrentUser(context)) {
					DialogUtil.toast(context, R.string.network_error2);
				}

			}

		}
	}

}
