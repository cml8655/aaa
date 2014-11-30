package cn.com.cml.dbl.service;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.com.cml.dbl.model.SmsModel;

/**
 * 短信数据库变化监听
 * 
 * @author 陈孟琳
 * 
 *         2014年11月12日
 */
public class SmsContentObserver extends ContentObserver {

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

		if (selfChange || null == context || null == handler) {
			return;
		}

		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(
					SmsModel.SMS_CONTENT_URI, projection,
					SmsModel.BODY + " like '%11111%'", null,
					SmsModel.DATE + " desc");

			if (null != cursor && cursor.moveToNext()) {

				context.startService(new Intent(context, RingtoneService_.class));

				Message msg = handler.obtainMessage();
				msg.what = CONTENT_CHANGE;

				int id = cursor.getInt(cursor.getColumnIndex(SmsModel._ID));
				String body = cursor.getString(cursor
						.getColumnIndex(SmsModel.BODY));

				msg.obj = new SmsModel(body, id);
				handler.sendMessage(msg);

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

}
