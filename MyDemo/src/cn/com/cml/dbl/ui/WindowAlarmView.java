package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.model.BindMessageModel;
import cn.com.cml.dbl.service.RingtoneService_;
import cn.com.cml.dbl.service.WindowAlarmService_;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.PrefUtil_;

@EViewGroup(R.layout.view_window_alarm)
public class WindowAlarmView extends LinearLayout implements OnClickListener {

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

		String pass = passView.getText().toString();

		if (TextUtils.isEmpty(pass)) {
			inputTipView.setText(getContext()
					.getString(R.string.empty_password));
			return;
		}

		boolean localExists = BindMessageModel.checkExists(pref
				.commandFromUsername().get(), pass);

		// TODO 本地不存在，请求服务器数据
		// TODO 检查网络情况

		if (localExists) {
			stopAlarm();
		} else {
			inputTipView.setText(getContext()
					.getString(R.string.password_error));
		}

	}

	private void stopAlarm() {
		RingtoneService_.intent(getContext()).stop();
		WindowAlarmService_.intent(getContext()).stop();
	}

}
