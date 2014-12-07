package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.PrefUtil_;
import cn.com.cml.dbl.util.ValidationUtil;

@EActivity(R.layout.activity_login)
@OptionsMenu(R.menu.login)
public class LoginActivity extends BaseActivity {

	@Bean
	ApiRequestServiceClient apiClient;

	@Pref
	PrefUtil_ prefUtil;

	@ViewById(R.id.input_username)
	EditText usernameView;

	@ViewById(R.id.input_password)
	EditText passwordView;

	@AfterViews
	protected void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_user,
				R.string.login_request);
		dialog.setCancelable(false);

		setCustomTitle(R.string.login_title);

		String username = prefUtil.username().get();

		if (null != username) {
			usernameView.setText(username);
		}

	}

	@Click(R.id.btn_login)
	void loginClick() {

		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();

		StringBuffer error = checkInputRegular(username, password);

		if (error.length() > 0) {

			DialogUtil.showTip(this, error.toString());

			return;
		}

		Log.d(TAG, "validate:" + error.toString());

		dialog.show(getSupportFragmentManager(), "login");

		apiClient.login(username, password, new SaveListener() {

			@Override
			public void onSuccess() {

				dialog.dismiss();

				User user = BmobUser.getCurrentUser(getApplicationContext(),
						User.class);

				prefUtil.edit().username().put(user.getUsername()).apply();

				apiClient.modifyLastLoginTime();
				//
				// MainActivity_.intent(getApplicationContext())
				// .flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();

				ImportanceActivity_.intent(LoginActivity.this).start();

				finish();
			}

			@Override
			public void onFailure(int errorCode, String msg) {

				dialog.dismissAllowingStateLoss();

				if (errorCode == 101) {
					DialogUtil.showTip(getApplicationContext(),
							getString(R.string.login_fail));
				}
			}
		});

	}

	private StringBuffer checkInputRegular(String username, String password) {
		StringBuffer error = new StringBuffer();

		if (TextUtils.isEmpty(username)) {
			error.append(getString(R.string.empty_username)).append("\n");
		} else if (!ValidationUtil.isEmail(username)) {
			error.append(getString(R.string.email_incorrect)).append("\n");
		}

		if (TextUtils.isEmpty(password)) {
			error.append(getString(R.string.empty_password));
		}
		return error;
	}

	@Click(R.id.tv_findpass)
	void findPass() {
		PasswordResetActivity_.intent(this).start();
	}

	@OptionsItem(R.id.menu_register)
	public void onRegisterMenuClick() {
		RegisterActivity_.intent(this).start();
	}

}
