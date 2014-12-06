package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.widget.EditText;
import android.widget.Toast;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.util.CommonUtils;

@EActivity(R.layout.activity_login)
@OptionsMenu(R.menu.login)
public class LoginActivity extends BaseActivity {

	@ViewById(R.id.input_username)
	EditText usernameView;

	@ViewById(R.id.input_password)
	EditText passwordView;

	@AfterViews
	protected void initConfig() {
		setCustomTitle(R.string.login_title);
	}

	@Click(R.id.btn_login)
	void loginClick() {

		User user = new User();

		user.setUsername(usernameView.getText().toString());
		user.setPassword(CommonUtils.encodeSHA1(passwordView.getText()
				.toString()));

		user.login(getApplicationContext(), this);
	}

	@OptionsItem(R.id.menu_register)
	public void onRegisterMenuClick() {
		Toast.makeText(getApplicationContext(), "点击注册", Toast.LENGTH_LONG)
				.show();
		RegisterActivity_.intent(this).start();
	}

	@Override
	public void onFailure(int arg0, String arg1) {
		Toast.makeText(this, "登录失败：" + arg1, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSuccess() {
		MainActivity_.intent(this).start();
		finish();
	}
}
