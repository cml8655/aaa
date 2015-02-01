package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.text.TextUtils;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import cn.com.cml.dbl.listener.BaseSaveListener;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.service.LocalStorageService_;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.PrefUtil_;
import cn.com.cml.dbl.util.ValidationUtil;

@EActivity(R.layout.activity_bind_device)
public class DeviceBindActivity extends BaseActivity {

	@Bean
	ApiRequestServiceClient apiClient;

	@Pref
	PrefUtil_ prefUtil;

	@ViewById(R.id.input_bind_pass)
	EditText passView;

	@ViewById(R.id.input_bind_repass)
	EditText repassView;

	@AfterViews
	protected void initConfig() {
		dialog = DialogUtil.dataLoadingDialog();
		dialog.setCancelable(false);

		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setCustomTitle(R.string.system_bind_device);
	}

	@Click(R.id.btn_bind)
	protected void bindClicked() {

		final String pass = passView.getText().toString();
		final String repass = repassView.getText().toString();

		StringBuffer error = new StringBuffer();

		if (TextUtils.isEmpty(pass)) {
			error.append(getString(R.string.empty_password)).append("\n");
		} else if (!ValidationUtil.equals(pass, repass)) {
			error.append(getString(R.string.password_not_equals));
		}

		if (error.length() > 0) {
			DialogUtil.showTip(this, error.toString());
			return;
		}

		dialog.show(getSupportFragmentManager(), "bind_device");

		apiClient.bindDevice(repass, new BaseSaveListener(
				getApplicationContext(), dialog) {

			@Override
			public void onSuccess() {

				super.onSuccess();

				// TODO 提示用户密码保护的重要性
				DialogUtil.toast(getApplicationContext(),
						R.string.bind_device_success);

				prefUtil.edit().isBindDevice().put(true).apply();

				// save data to local
				String username = BmobUser.getCurrentUser(
						DeviceBindActivity.this).getUsername();

				LocalStorageService_.intent(DeviceBindActivity.this)
						.saveBindPass(username, pass).start();

				MainActivity_.intent(DeviceBindActivity.this).start();

				finish();
			}
		});

	}

	@OptionsItem(android.R.id.home)
	public void onKeyBackClick() {
		onBackPressed();
	}

}
