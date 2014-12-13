package cn.com.cml.dbl.receiver;

import static cn.com.cml.dbl.contant.Constant.JINBAO;
import static cn.com.cml.dbl.contant.Constant.JINGBAO_STOP;
import static cn.com.cml.dbl.contant.Constant.DINGWEI;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import cn.bmob.push.PushConstants;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.model.PushModel;
import cn.com.cml.dbl.service.RingtoneService_;

import com.google.gson.Gson;

public class PushReceiver extends BroadcastReceiver {

	private static final String TAG = "PushReceiver";
	private static final String PUSH_ROOT = "alert";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

			try {

				String message = intent
						.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);

				Log.d(TAG, "接到消息推送：" + message);

				if (!TextUtils.isEmpty(message)) {

					JSONObject json = new JSONObject(message);

					if (json.has(PUSH_ROOT)) {

						Gson gson = new Gson();

						PushModel model = gson.fromJson(
								json.getString(PUSH_ROOT), PushModel.class);

						// 判断是否超时
						boolean isAvaiable = System.currentTimeMillis() <= model
								.getEndTime();

						if (!isAvaiable) {
							return;
						}

						if (JINBAO.equals(model.getCommand())) {

							RingtoneService_.intent(context).start();

						} else if (JINGBAO_STOP.equals(model.getCommand())) {

							RingtoneService_.intent(context).stop();

						} else if (DINGWEI.endsWith(model.getCommand())) {
							// TODO 定位功能启动
						}

					}

				}

			} catch (Exception e) {
				Log.e(TAG, "信息转换失败", e);
			}

		}
	}

}
