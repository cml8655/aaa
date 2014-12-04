package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import android.app.Activity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

	@AfterViews
	protected void initConfig() {
		// TODO 第一次启动判断
		// TODO 跳到登录页面
		closeActivity();
	}

	@UiThread(delay = 300)
	protected void closeActivity() {
		MainActivity_.intent(this).start();
		finish();
	}

}
