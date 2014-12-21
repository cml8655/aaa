package cn.com.cml.dbl.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.PushListener;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.contant.Constant.Command;
import cn.com.cml.dbl.listener.BaseFindListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.ValidationUtil;

/**
 * 手机警报
 */
@EFragment(R.layout.fragment_alarm)
public class AlarmFragment extends Fragment {

	private static final String TAG = "AlarmFragment";

	public static final String ACTION_RING = "cn.com.cml.dbl.view.AlarmFragment.alarm.ring";

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

	private CountDownTimer countTimer;

	private BroadcastReceiver alarmStateReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Activity ac = getActivity();
			if (ac != null && !ac.isFinishing()) {

				final String action = intent.getAction();

				if (ACTION_RING.equals(action)) {
					countTimer.cancel();
					buttonStateChanged(R.color.cancel, cancelAlarm);
					DialogUtil.toast(context, R.string.alarm_ring);
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		IntentFilter filter = new IntentFilter(ACTION_RING);
		getActivity().registerReceiver(alarmStateReceiver, filter);
	}

	@Override
	public void onDestroy() {

		getActivity().unregisterReceiver(alarmStateReceiver);
		super.onDestroy();
	}

	@AfterViews
	public void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_spin5,
				R.string.data_requesting);
		dialog.setCancelable(false);

	}

	@Click(R.id.alarm_send_btn)
	public void onControlClicked() {

		final String controlBtnText = controlButton.getText().toString();

		final String pass = passView.getText().toString();

		if (TextUtils.isEmpty(pass)) {

			DialogUtil
					.showTip(getActivity(), getString(R.string.bind_password));
			return;
		}

		dialog.show(getFragmentManager(), "alarm");

		// 查询用户手机绑定情况
		apiClient.bindDeviceQuery(new BaseFindListener<MobileBind>(
				getActivity()) {

			@Override
			public void onFinish() {
				super.onFinish();
				dialog.dismiss();
			}

			@Override
			public void onSuccess(List<MobileBind> result) {

				// TODO 没有绑定手机，跳到绑定界面
				if (result.size() == 0) {
					Log.d(TAG, "没有找到绑定手机");
					return;
				}

				MobileBind bindDevice = result.get(0);

				// 密码错误
				if (!ValidationUtil.equals(pass, bindDevice.getBindPassword())) {

					DialogUtil.showTip(getActivity(),
							getString(R.string.password_error));

					return;
				}

				// 启动警报
				if (sendAlarm.equals(controlBtnText)) {

					Command alaramCommand = Command.JINGBAO_ENUM;
					alaramCommand.setBindPass(pass);
					alaramCommand.setFrom(BmobUser
							.getCurrentUser(getActivity()).getUsername());

					apiClient.sendPushCommand(alaramCommand,
							bindDevice.getImei(), new PushListener() {

								@Override
								public void onSuccess() {
									Log.d(TAG, "alaramCommand发送onSuccess"
											+ Thread.currentThread().getId());
									buttonStateChanged(
											R.color.default_color,
											getString(R.string.alarm_wait_feedback));
									startCountDown();
								}

								@Override
								public void onFailure(int arg0, String errorMsg) {
									Log.d(TAG, "发送失败：" + errorMsg);
								}
							});

					return;
				}

				// 取消警报
				if (cancelAlarm.equals(controlBtnText)) {

					Command alarmStopCommand = Command.JINGBAO_STOP_ENUM;
					alarmStopCommand.setBindPass(pass);
					alarmStopCommand.setFrom(BmobUser.getCurrentUser(
							getActivity()).getUsername());

					apiClient.sendPushCommand(alarmStopCommand,
							bindDevice.getImei(), new PushListener() {

								@Override
								public void onSuccess() {
									Log.d(TAG, "alarmStopCommand发送onSuccess");
									buttonStateChanged(R.color.default_color,
											sendAlarm);
								}

								@Override
								public void onFailure(int arg0, String errorMsg) {
									Log.d(TAG, "alarmStopCommand发送失败："
											+ errorMsg);
									// TODO 提示发送失败
								}
							});
				}

			}
		});

	}

	private void startCountDown() {

		countTimer = new CountDownTimer(Constant.JINBAO_EXPIRES, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {

				Activity ac = getActivity();

				if (ac != null && !ac.isFinishing()) {
					controlButton.setText(getString(
							R.string.alarm_wait_feedback,
							millisUntilFinished / 1000));
				}

			}

			@Override
			public void onFinish() {

				Activity ac = getActivity();
				if (ac != null && !ac.isFinishing()) {
					buttonStateChanged(R.color.default_color, sendAlarm);
				}

			}
		};

		countTimer.start();
	}

	private void buttonStateChanged(int bg, String text) {
		controlButton.setBackgroundColor(getResources().getColor(bg));
		controlButton.setText(text);
	}
}
