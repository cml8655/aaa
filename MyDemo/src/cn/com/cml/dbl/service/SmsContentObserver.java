package cn.com.cml.dbl.service;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.model.BindMessageModel;
import cn.com.cml.dbl.model.SmsModel;
import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.NetworkStatusUtil;
import cn.com.cml.dbl.util.NetworkUtils;

/**
 * 短信数据库变化监听
 * 
 * @author 陈孟琳
 * 
 *         2014年11月12日
 */
public class SmsContentObserver extends ContentObserver {

	private static final String TAG = "SmsContentObserver";

	public static final int CONTENT_CHANGE = 1002;

	private Handler handler;
	private Context context;

	private String[] projection = new String[] { SmsModel._ID, SmsModel.BODY };

	public SmsContentObserver(Context context, Handler handler) {
		super(handler);
		this.handler = handler;
		this.context = context;
	}

	@Override
	public void onChange(boolean selfChange) {

		Log.d(TAG, "收到新短信");

		if (selfChange || null == context || null == handler) {
			return;
		}

		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(
					SmsModel.SMS_CONTENT_URI, projection, null, null,
					SmsModel.DATE + " desc");

			if (null != cursor && cursor.moveToNext()) {

				int id = cursor.getInt(cursor.getColumnIndex(SmsModel._ID));
				String body = cursor.getString(cursor
						.getColumnIndex(SmsModel.BODY));

				Log.d(TAG, "收到新短信,内容：" + body);

				Constant.Command command = isCommand(body);

				if (null != command) {

					switch (command) {

					case JINGBAO_ENUM:

						// 短信警报功能开启
						if (AppUtil.getPrefValue(context, "smsAlaram")) {
							// 启动警报
							AlarmServiceQuene_.intent(context).start();
							//
							// Message msg = handler.obtainMessage();
							//
							// msg.what = CONTENT_CHANGE;
							// msg.obj = new SmsModel(body, id);
							//
							// handler.sendMessage(msg);
						}

						break;

					case JINGBAO_STOP_ENUM:

						RingtoneService_.intent(context).stop();
						// 启动桌面密码输入
						WindowAlarmService_.intent(context).stop();
						break;

					case DINGWEI_ENUM:

						// 允许开启短信定位
						if (AppUtil.getPrefValue(context, "smsLocation")) {

							// 网路没有开启
							if (!NetworkUtils.isNetworkActive(this.context)) {
								// 开启wifi
								NetworkStatusUtil.openWifiIfClosed(context);
								// 开启mobile网络
								boolean mobileNetEnable = NetworkStatusUtil
										.getMobileDataStatus(context);

								if (!mobileNetEnable) {
									NetworkStatusUtil.setMobileDataStatus(
											context, true);
								}
							}
						}

						break;

					default:
						break;
					}

				}

			}
			Log.e("fff", "has no message:");
		} catch (Exception e) {
			Log.e("fff", "has message:", e);
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
	}

	/**
	 * 将数据指令转换成哼Command对象
	 * 
	 * @param body
	 * @return 密码与本地数据库存储相同，返回对应的指令对象，else 返回null
	 */
	private Constant.Command isCommand(String body) {

		if (TextUtils.isEmpty(body) || body.endsWith(Constant.COMMAND_SPERATOR)) {
			return null;
		}

		int commandSpeatorIndex = body.indexOf(Constant.COMMAND_SPERATOR);

		if (commandSpeatorIndex == -1) {
			return null;
		}

		String pass = body.substring(0, commandSpeatorIndex);
		String commandStr = body.substring(commandSpeatorIndex + 1);

		Constant.Command command = Constant.Command.getByCommand(commandStr);

		if (null == command) {
			return null;
		}

		return BindMessageModel.checkExists(pass) ? command : null;
	}
}
