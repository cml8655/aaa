package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import android.app.ActionBar;
import android.app.Activity;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends Activity {

	@AfterViews
	protected void initConfig() {

		final ActionBar bar = this.getActionBar();

		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
	}

	@OptionsItem(android.R.id.home)
	public void onKeyBackClick() {
		onBackPressed();
	}
}
