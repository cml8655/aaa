package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import android.util.Log;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

	@AfterViews
	protected void initConfig() {
		
		Log.d(TAG, "RegisterActivity-->initConfig");
		
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	

		setCustomTitle(R.string.login_register);
	}

	@OptionsItem(android.R.id.home)
	public void onKeyBackClick() {
		onBackPressed();
	}
}
