package cn.com.cml.pets.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.cml.pets.R;
import cn.com.cml.pets.model.SmsModel;
import cn.com.cml.pets.service.SMSHandler;
import cn.com.cml.pets.service.SmsContentObserver;

@EFragment(R.layout.fragment_msg)
public class MessageFragment extends Fragment {

	@ViewById(R.id.show_msg)
	TextView showMsg;

	private SmsManager smsManager;

	private SmsReciver smsReceiver;
	private IntentFilter smsFilter;

	@AfterViews
	protected void initConfig() {
		smsManager = SmsManager.getDefault();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().getContentResolver().registerContentObserver(
				SmsModel.SMS_CONTENT_URI,
				true,
				new SmsContentObserver(getActivity(), new SMSHandler(
						getActivity())));

		Log.e("fff", "注册监听");
	}

	@Override
	public void onResume() {

		super.onResume();

		if (null == smsReceiver) {
			smsReceiver = new SmsReciver();
			smsFilter = new IntentFilter(SmsReciver.ACTION_RECEIVE_MSG);
			smsFilter.addAction(SmsReciver.ACTION_SEND_MSG);
		}

		getActivity().registerReceiver(smsReceiver, smsFilter);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (null != smsReceiver) {
			getActivity().unregisterReceiver(smsReceiver);
		}
	}

	@Click(R.id.send_msg)
	public void sendMsg(View v) {

		Intent intent = new Intent(SmsReciver.ACTION_SEND_MSG);

		PendingIntent sendIntent = PendingIntent.getBroadcast(getActivity(), 1,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Intent resultIntent = new Intent(SmsReciver.ACTION_DELIVER_MSG);

		PendingIntent resultPendingIntent = PendingIntent.getBroadcast(
				getActivity(), 1, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		smsManager.sendTextMessage("5556", null, "test of message", sendIntent,
				resultPendingIntent);

		showMsg.setText("showMsg");
	}

	@Click(R.id.receive_msg)
	public void receiveMsg(View v) {
		showMsg.setText("receiveMsg");
	}

	class SmsReciver extends BroadcastReceiver {

		public static final String ACTION_RECEIVE_MSG = "android.provider.Telephony.SMS_RECEIVED";
		public static final String ACTION_SEND_MSG = "com.cml.sendMsg";
		public static final String ACTION_DELIVER_MSG = "com.cml.sendMsg.DELIVER";

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(ACTION_RECEIVE_MSG)) {
				SmsManager sms = SmsManager.getDefault();
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					Object[] pdus = (Object[]) bundle.get("pdus");
					SmsMessage[] messages = new SmsMessage[pdus.length];
					for (int i = 0; i < pdus.length; i++)
						messages[i] = SmsMessage
								.createFromPdu((byte[]) pdus[i]);
					for (int i = 0; i < messages.length; i++) {
						SmsMessage message = messages[i];
						String msg = message.getMessageBody();
						String to = message.getOriginatingAddress();

						Toast.makeText(context, "接收到短信：" + msg + ",to:" + to,
								Toast.LENGTH_LONG).show();

						// if (msg.toLowerCase().startsWith(queryString))
						// {
						// String out = msg.substring(queryString.length());
						// sms.sendTextMessage(to, null, out, null, null);
						// }
					}
				}
				return;
			}

			String msg = "==" + getResultCode();

			switch (getResultCode()) {

			case Activity.RESULT_OK:
				msg = "操作成功！";
				break;

			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				msg = "失败RESULT_ERROR_GENERIC_FAILURE";
				break;

			case SmsManager.RESULT_ERROR_RADIO_OFF:
				msg = "失败RESULT_ERROR_RADIO_OFF";
				break;

			case SmsManager.RESULT_ERROR_NULL_PDU:
				msg = "失败RESULT_ERROR_NULL_PDU";
				break;
			}

			Toast.makeText(context, msg + ",action:" + intent.getAction(),
					Toast.LENGTH_LONG).show();

		}

	}

}
