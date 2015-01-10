package cn.com.cml.dbl.service;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import cn.com.cml.dbl.model.SmsModel;

public class SMSHandler extends Handler {

	public static final String TAG = "SMSHandler";

	private Context mContext;

	public SMSHandler(Context context) {
		super();
		this.mContext = context;
	}

	public void handleMessage(Message message) {

		Log.i(TAG, "handleMessage: " + message);

		SmsModel item = (SmsModel) message.obj;

		Uri uri = ContentUris.withAppendedId(SmsModel.SMS_CONTENT_URI,
				item.getId());

		mContext.getContentResolver().delete(uri, null, null);

		Toast.makeText(mContext, "删除咯" + item.getBody() + "," + item.getId(),
				Toast.LENGTH_LONG).show();
	}

}