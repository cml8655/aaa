package cn.com.cml.dbl.receiver;

import static cn.com.cml.dbl.contant.Constant.DINGWEI;
import static cn.com.cml.dbl.contant.Constant.JINBAO;
import static cn.com.cml.dbl.contant.Constant.JINGBAO_RING;
import static cn.com.cml.dbl.contant.Constant.JINGBAO_STOP;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import cn.bmob.push.PushConstants;
import cn.com.cml.dbl.listener.GlobalBaseListener_;
import cn.com.cml.dbl.model.BindMessageModel;
import cn.com.cml.dbl.model.PushModel;
import cn.com.cml.dbl.service.AlarmServiceQuene_;
import cn.com.cml.dbl.service.GlobalService;
import cn.com.cml.dbl.service.RingtoneService_;
import cn.com.cml.dbl.service.WindowAlarmService_;
import cn.com.cml.dbl.util.PrefUtil_;

import com.google.gson.Gson;

@EReceiver
public class PushReceiver extends BroadcastReceiver {

	private static final String TAG = "PushReceiver";
	private static final String PUSH_ROOT = "alert";

	@Pref
	PrefUtil_ pref;

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

						pref.edit().commandFromUsername()
								.put(model.getFromUserName()).apply();

						if (JINGBAO_RING.equals(model.getCommand())) {
							// TODO 发送信息提示用户，警报送达
						} else if (JINBAO.equals(model.getCommand())) {

							onJingBao(model, context);

						} else if (JINGBAO_STOP.equals(model.getCommand())) {

							onJingBaoStop(model, context);

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

	private void onJingBao(PushModel model, Context context) {

		boolean exist = BindMessageModel.checkExists(model.getFromUserName(),
				model.getBindPass());

		Log.d(TAG, "onJingBao,localExists:" + exist);

		if (exist) {
			// RingtoneService_.intent(context).start();
			// WindowAlarmService_.intent(context).start();
			AlarmServiceQuene_.intent(context).start();
		}

	}

	private void onJingBaoStop(PushModel model, Context context) {

		boolean exist = BindMessageModel.checkExists(model.getFromUserName(),
				model.getBindPass());

		Log.d(TAG, "onJingBao,localExists:" + exist);

		if (exist) {
			AlarmServiceQuene_.intent(context).stop();
			WindowAlarmService_.intent(context).stop();
			RingtoneService_.intent(context).stop();
		}

	}

}
