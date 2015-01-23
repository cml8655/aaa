package cn.com.cml.dbl;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Activity;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.bmob.v3.listener.FindListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.model.BindMessageModel;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.CommonUtils;
import cn.com.cml.dbl.util.PrefUtil_;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";

	@ViewById(R.id.splash_logo)
	ImageView logoView;

	@Pref
	PrefUtil_ pref;

	@Bean
	ApiRequestServiceClient apiClient;

	@AfterViews
	protected void initConfig() {

		logoView.startAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.splash_anim));

		if (!pref.introduceVersion().exists()) {
			// TODO 跳到欢迎界面
			IntroduceActivity_.intent(this).start();
			finish();
			return;
		}

		String version = pref.introduceVersion().get();

		String currentVersion = AppUtil.version(getApplicationContext());

		if (!version.equalsIgnoreCase(currentVersion)) {
			// TODO 升级后第一次进来
		}

		if (!CommonUtils.isNetworkConnected(getApplicationContext())) {
			closeActivity();
			return;
		}

		closeActivity();

		// 有网络，同步数据
		apiClient.bindCurrentDeviceQuery(new FindListener<MobileBind>() {

			@Override
			public void onError(int errorCode, String errorMsg) {
				closeActivity();
			}

			@Override
			public void onSuccess(List<MobileBind> result) {
				syncLocalData(result);
			}
		});

	}

	@Background
	protected void syncLocalData(List<MobileBind> result) {
		try {

			BindMessageModel.clear();

			for (MobileBind bind : result) {

				BindMessageModel model = new BindMessageModel();

				model.bindPass = bind.getBindPassword();
				model.username = bind.getUser().getUsername();

				model.save();
			}

			closeActivity();

		} catch (Exception e) {
			Log.e(TAG, "同步数据出错", e);
		}
	}

	@UiThread(delay = 300)
	protected void closeActivity() {
		// MainActivity_.intent(this).start();
		LoginActivity_.intent(this).start();
		finish();
	}

}
