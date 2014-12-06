package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.widget.EditText;
import android.widget.Toast;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.util.CommonUtils;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

	@ViewById(R.id.input_username)
	EditText usernameView;

	@ViewById(R.id.input_password)
	EditText passwordView;

	@ViewById(R.id.input_repassword)
	EditText repasswordView;

	@AfterViews
	protected void initConfig() {
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		setCustomTitle(R.string.login_register);
	}

	@Click(R.id.btn_register)
	void registerClick() {
		// TODO 进行校验
		String username = usernameView.getText().toString();
		String password = passwordView.getText().toString();

		User user = new User();

		user.setUsername(username);
		user.setEmail(username);
		user.setPassword(CommonUtils.encodeSHA1(password));

		user.signUp(this, this);
	}

	@OptionsItem(android.R.id.home)
	public void onKeyBackClick() {
		onBackPressed();
	}

	@Override
	public void onFailure(int arg0, String arg1) {
		Toast.makeText(this, "注册失败" + arg1, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSuccess() {
		Toast.makeText(this, "注册成共", Toast.LENGTH_LONG).show();
	}
}
