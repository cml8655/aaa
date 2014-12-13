package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.listener.PushListener;
import cn.com.cml.dbl.PetApplication;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.model.PushModel;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.service.RingtoneService_;
import cn.com.cml.dbl.util.DialogUtil;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

/**
 * 手机警报
 */
@EFragment(R.layout.fragment_alarm)
public class AlarmFragment extends Fragment {

	private static final String TAG = "AlarmFragment";

	@Bean
	ApiRequestServiceClient apiClient;

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
			
			// TODO 转换成用户绑定的imei
			apiClient.sendPushCommand(Constant.Command.JINGBAO_ENUM, PetApplication.deviceId,
					new PushListener() {

						@Override
						public void onSuccess() {
							Log.d(TAG, "发送onSuccess");
						}

						@Override
						public void onFailure(int arg0, String errorMsg) {
							Log.d(TAG, "发送失败：" + errorMsg);
						}
					});

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

			RingtoneService_.intent(getActivity()).stop();
			buttonStateChanged(R.color.default_color, sendAlarm);
		}

	}

	private void buttonStateChanged(int bg, String text) {
		controlButton.setBackgroundColor(getResources().getColor(bg));
		controlButton.setText(text);
	}
}
