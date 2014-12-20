package cn.com.cml.dbl;

import java.nio.channels.AlreadyConnectedException;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.listener.FindListener;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.model.BindMessageModel;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.service.AlarmServiceQuene;
import cn.com.cml.dbl.service.AlarmServiceQuene_;
import cn.com.cml.dbl.service.PushService_;
import cn.com.cml.dbl.util.CommonUtils;
import cn.com.cml.dbl.util.PrefUtil_;

@EActivity(R.layout.view_window_alarm)
public class WindowAlarmActvity extends FragmentActivity implements
		OnClickListener {

	private static final String TAG = "WindowAlarmActvity";

	@Bean
	ApiRequestServiceClient apiClient;

	@SystemService
	InputMethodManager inputManager;

	@ViewById(R.id.input_tip_tv)
	TextView inputTipView;

	@ViewById(R.id.pass_input)
	EditText passView;

	@ViewById(R.id.send_btn)
	Button sendButton;

	@Pref
	PrefUtil_ pref;

	@Extra
	int alarmType;

	@Override
	protected void onCreate(Bundle savedConstant) {

		super.onCreate(savedConstant);

		int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;

		getWindow().addFlags(flags);

	}

	@AfterViews
	void init() {
		sendButton.setOnClickListener(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);

		if (intent.hasExtra(AlarmServiceQuene.EXTRA_TYPE)) {
			alarmType = intent.getIntExtra(AlarmServiceQuene.EXTRA_TYPE, -1);
		}
	}

	@Override
	public void onClick(View v) {

		final String pass = passView.getText().toString();
		final String username = pref.commandFromUsername().get();

		// TODO 发布后需要去除
		if (pass.equals("111")) {
			stopAlarm();
			return;
		}

		Log.d(TAG, "解除警报：" + pass + "," + username + "," + alarmType);

		if (TextUtils.isEmpty(pass)) {
			inputTipView.setText(getString(R.string.empty_password));
			return;
		}

		// 短信发送
		if (alarmType == Constant.Alarm.TYPE_SMS) {

			if (BindMessageModel.checkExists(pass)) {
				stopAlarm();
			}
			return;
		}

		// 没有网络，进行本地查询
		if (!CommonUtils.isNetworkConnected(this)) {
			checkLocalStorage(username, pass);
			return;
		}

		Log.d(TAG, "解除警报，有网络");

		// 有网络，进行网络数据加载
		apiClient.bindPassQuery(username, pass, new FindListener<MobileBind>() {

			@Override
			public void onSuccess(List<MobileBind> result) {
				if (result.size() == 0) {
					inputTipView.setText(getString(R.string.password_error));
				} else {
					// 密码正确
					stopAlarm();
				}
			}

			@Override
			public void onError(int code, String msg) {
				checkLocalStorage(username, pass);
				Log.d(TAG, "解除警报，网络查询错误！" + msg + "," + code);
			}
		});

	}

	private void stopAlarm() {

		AlarmServiceQuene_.intent(this).stop();
		finish();
	}

	private void checkLocalStorage(String username, String pass) {

		boolean localExists = BindMessageModel.checkExists(pref
				.commandFromUsername().get(), pass);

		if (localExists) {
			stopAlarm();
		} else {
			inputTipView.setText(getString(R.string.password_error));
		}
	}

}
