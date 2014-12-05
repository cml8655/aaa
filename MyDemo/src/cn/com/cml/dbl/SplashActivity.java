package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.PrefUtil_;
import android.app.Activity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

	@ViewById(R.id.splash_logo)
	ImageView logoView;

	@Pref
	PrefUtil_ pref;

	@AfterViews
	protected void initConfig() {

		logoView.startAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.splash_anim));

		if (!pref.introduceVersion().exists()) {
			// TODO 跳到欢迎界面
		}

		String version = pref.introduceVersion().get();

		String currentVersion = AppUtil.version(getApplicationContext());

		if (!version.equalsIgnoreCase(currentVersion)) {
			// TODO 升级后第一次进来
		}

		// TODO 跳到登录界面

		 closeActivity();
	}

	@UiThread(delay = 300)
	protected void closeActivity() {
		// MainActivity_.intent(this).start();
		LoginActivity_.intent(this).start();
		finish();
	}

}
