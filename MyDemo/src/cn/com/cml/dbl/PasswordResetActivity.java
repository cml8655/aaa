package cn.com.cml.dbl;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ResetPasswordListener;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.ValidationUtil;

@EActivity(R.layout.activity_resetpassword)
public class PasswordResetActivity extends BaseActivity {

	private static final int RETRY_INTERVAL = 60000;

	@ViewById(R.id.input_email)
	EditText emailView;

	@ViewById(R.id.btn_reset)
	Button sendBtn;

	private CountDownTimer retryTimer = new CountDownTimer(RETRY_INTERVAL, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			sendBtn.setText(getString(R.string.reset_retry_countdown,
					millisUntilFinished / 1000));
		}

		@Override
		public void onFinish() {
			sendBtn.setClickable(true);
			sendBtn.setText(getString(R.string.reset_pass_send));
			sendBtn.setBackgroundColor(getResources().getColor(
					android.R.color.holo_blue_light));
		}
	};

	@AfterViews
	void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_user,
				R.string.login_request);
		dialog.setCancelable(false);

		setCustomTitle(R.string.reset_pass_title);
		emailView.setText("865517964@qq.com");
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Click(R.id.btn_reset)
	void sendEmail() {

		String email = emailView.getText().toString();

		if (!ValidationUtil.isEmail(email)) {

			DialogUtil.showTip(getApplicationContext(),
					getString(R.string.email_incorrect));
			return;
		}

		dialog.show(getSupportFragmentManager(), "reset");

		BmobQuery<User> query = new BmobQuery<User>();

		query.setCachePolicy(CachePolicy.NETWORK_ONLY);
		query.addWhereEqualTo("username", email);
		query.setLimit(1);

		query.findObjects(this, new FindListener<User>() {

			@Override
			public void onError(int arg0, String msg) {
				dialog.dismissAllowingStateLoss();
				Log.d(TAG, "onError:" + msg);

			}

			@Override
			public void onSuccess(List<User> users) {

				dialog.dismissAllowingStateLoss();

				if (users.size() == 0) {

					DialogUtil.toast(getApplicationContext(),
							R.string.user_not_found);
					return;
				}

				BmobUser.resetPassword(getApplicationContext(), users.get(0)
						.getUsername(), new ResetPasswordListener() {

					@Override
					public void onSuccess() {

						DialogUtil.toast(getApplicationContext(),
								R.string.email_send_success);

						sendBtn.setClickable(false);
						sendBtn.setBackgroundColor(Color.GRAY);

						retryTimer.start();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						DialogUtil.toast(getApplicationContext(),
								R.string.email_send_fail);
					}
				});

			}
		});

	}

	@OptionsItem(android.R.id.home)
	public void onKeyBackClick() {
		onBackPressed();
	}

}
