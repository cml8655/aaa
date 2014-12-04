package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import android.app.ActionBar;
import android.app.Activity;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {
	
	@AfterViews
	protected void initConfig() {
		
		final ActionBar bar=getActionBar();
		
		bar.setTitle(R.string.login_title);
		bar.setDisplayShowTitleEnabled(true);
	}
}
