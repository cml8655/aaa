package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DialogUtil;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * 手机警报
 */
@EFragment(R.layout.fragment_alarm)
public class AlarmFragment extends Fragment {

	@ViewById(R.id.bind_pass_input)
	EditText passView;

	@ViewById(R.id.alarm_send_btn)
	Button controlButton;

	private DialogFragment dialog;

	@StringRes(R.string.alarm_send)
	String sendAlarm;

	@StringRes(R.string.alarm_cancel)
	String cancelAlarm;

	@AfterViews
	public void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_spin5,
				R.string.data_requesting);
		dialog.setCancelable(false);
		
		// TODO 判断用户是否有绑定其他手机
		// dialog.show(getFragmentManager(), "alarm");

	}

	@Click(R.id.alarm_send_btn)
	public void onControlClicked() {

		String controlBtnText = controlButton.getText().toString();

		// 发送
		if (sendAlarm.equals(controlBtnText)) {

			dialog.show(getFragmentManager(), "alarm");

			String pass = passView.getText().toString();

			if (TextUtils.isEmpty(pass)) {

				dialog.dismiss();
				DialogUtil.showTip(getActivity(),
						getString(R.string.bind_password));
				return;
			}

			// TODO 密码验证

			buttonStateChanged(R.color.cancel, cancelAlarm);

			dialog.dismiss();

			return;
		}

		if (cancelAlarm.equals(controlBtnText)) {

			buttonStateChanged(R.color.default_color, sendAlarm);
		}

	}

	private void buttonStateChanged(int bg, String text) {
		controlButton.setBackgroundColor(getResources().getColor(bg));
		controlButton.setText(text);
	}
}
