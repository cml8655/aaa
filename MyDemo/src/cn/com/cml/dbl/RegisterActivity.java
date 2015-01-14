package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.ValidationUtil;
import cn.com.cml.dbl.view.WebViewFragment_;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

	@Bean
	ApiRequestServiceClient apiClient;

	@ViewById(R.id.input_username)
	EditText usernameView;

	@ViewById(R.id.input_password)
	EditText passwordView;

	@ViewById(R.id.input_repassword)
	EditText repasswordView;

	@AfterViews
	protected void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_user,
				R.string.register_tip);
		dialog.setCancelable(false);

		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		// usernameView.setText("865517964@qq.com");
		// passwordView.setText("123456");

		setCustomTitle(R.string.login_register);
	}

	@Click(R.id.register_agreement)
	void agreementClick() {
		Bundle bundle = new Bundle();
		bundle.putString("mLoadUrl", Constant.Url.URL_AGREEMENT);
		ModalActivity_.intent(this).fragmentTitle(R.string.agreement)
				.extraArguments(bundle).container(WebViewFragment_.class)
				.start();
	}

	@Click(R.id.btn_register)
	void registerClick() {

		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();
		String repassword = repasswordView.getText().toString();

		StringBuffer error = inputRegularCheck(username, password, repassword);

		if (error.length() > 0) {

			DialogUtil.showTip(this, error.toString());

			return;
		}

		dialog.show(getSupportFragmentManager(), "register");

		apiClient.signUp(username, password, new SaveListener() {

			@Override
			public void onSuccess() {
				dialog.dismiss();
				DialogUtil.toast(getApplicationContext(),
						R.string.register_success);
				finish();
			}

			@Override
			public void onFailure(int errorCode, String msg) {

				dialog.dismiss();

				if (errorCode == 202) {
					DialogUtil.showTip(getApplicationContext(),
							getString(R.string.register_user_repeat));
				} else {
					DialogUtil.showTip(getApplicationContext(), msg);
				}

			}
		});

	}

	@Override
	protected void onDestroy() {

		if (null != dialog && dialog.isVisible()) {

			dialog.dismissAllowingStateLoss();
		}
		super.onDestroy();
	}

	private StringBuffer inputRegularCheck(String username, String password,
			String repassword) {
		StringBuffer error = new StringBuffer();

		if (TextUtils.isEmpty(username)) {
			error.append(getString(R.string.empty_username)).append("\n");
		} else if (!ValidationUtil.isEmail(username)) {
			error.append(getString(R.string.email_incorrect)).append("\n");
		}

		if (TextUtils.isEmpty(password)) {
			error.append(getString(R.string.empty_password)).append("\n");
		}

		if (!ValidationUtil.equals(password, repassword)) {
			error.append(getString(R.string.password_not_equals));
		}
		return error;
	}

	@OptionsItem(android.R.id.home)
	public void onKeyBackClick() {
		onBackPressed();
	}

}
