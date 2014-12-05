package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import android.widget.Toast;

@EActivity(R.layout.activity_login)
@OptionsMenu(R.menu.login)
public class LoginActivity extends BaseActivity {

	@AfterViews
	protected void initConfig() {
		setCustomTitle(R.string.login_title);
	}

	@OptionsItem(R.id.menu_register)
	@Click(R.id.menu_item_login)
	public void onRegisterMenuClick() {
		Toast.makeText(getApplicationContext(), "点击注册", Toast.LENGTH_LONG)
				.show();
		RegisterActivity_.intent(this).start();
	}
}
