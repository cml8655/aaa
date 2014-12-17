package cn.com.cml.dbl.ui;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.listener.FindListener;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.model.BindMessageModel;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.service.AlarmServiceQuene_;
import cn.com.cml.dbl.service.RingtoneService_;
import cn.com.cml.dbl.service.WindowAlarmService_;
import cn.com.cml.dbl.util.CommonUtils;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.PrefUtil_;

@EViewGroup(R.layout.view_window_alarm)
public class WindowAlarmView extends LinearLayout implements OnClickListener {

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

	@AfterViews
	void init() {
		sendButton.setOnClickListener(this);
	}

	public WindowAlarmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WindowAlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WindowAlarmView(Context context) {
		super(context);
	}

	@Override
	public void onClick(View v) {

		final String pass = passView.getText().toString();
		final String username = pref.commandFromUsername().get();

		if (TextUtils.isEmpty(pass)) {
			inputTipView.setText(getContext()
					.getString(R.string.empty_password));
			return;
		}

		// 没有网络，进行本地查询
		if (!CommonUtils.isNetworkConnected(getContext())) {
			checkLocalStorage(username, pass);
			return;
		}

		// 有网络，进行网络数据加载
		apiClient.bindPassQuery(username, pass, new FindListener<MobileBind>() {

			@Override
			public void onSuccess(List<MobileBind> result) {
				if (result.size() == 0) {
					inputTipView.setText(getContext().getString(
							R.string.password_error));
				} else {
					// 密码正确
					stopAlarm();
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				checkLocalStorage(username, pass);
			}
		});

	}

	private void stopAlarm() {
		inputManager.hideSoftInputFromWindow(getWindowToken(), 0);
		RingtoneService_.intent(getContext()).stop();
		WindowAlarmService_.intent(getContext()).stop();
		AlarmServiceQuene_.intent(getContext()).stop();
	}

	private void checkLocalStorage(String username, String pass) {

		boolean localExists = BindMessageModel.checkExists(pref
				.commandFromUsername().get(), pass);

		if (localExists) {
			stopAlarm();
		} else {
			inputTipView.setText(getContext()
					.getString(R.string.password_error));
		}
	}

}
