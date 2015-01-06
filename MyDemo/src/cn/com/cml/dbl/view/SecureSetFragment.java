package cn.com.cml.dbl.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.AppUtil;

@EFragment(R.layout.fragment_secure)
public class SecureSetFragment extends BaseFragment {

	@ViewById(R.id.secure_miui)
	View miuiButton;

	@ViewById(R.id.secure_app_360)
	View app360Button;

	@ViewById(R.id.secure_app_baidu)
	View appBaiduButton;

	@ViewById(R.id.secure_app_tencent)
	View appTencentButton;

	@ViewById(R.id.secure_app_jinshan)
	View appJinshanButton;

	@ViewById(R.id.secure_app_liebao)
	View appLiebaoButton;

	@ViewById(R.id.secure_app_liebao_master)
	View appLiebaoMasterButton;

	@AfterViews
	protected void afterViews() {

		if (AppUtil.isMIUI(getActivity())) {
			miuiButton.setVisibility(View.VISIBLE);
		}

		List<String> installedApps = AppUtil
				.getInstalledPackages(getActivity());

		// 360
		if (installedApps.contains(AppUtil.APP_360_MOBILE)) {
			app360Button.setVisibility(View.VISIBLE);
		}

		// 百度卫士
		if (installedApps.contains(AppUtil.APP_BAIDU_MOBILE)) {
			appBaiduButton.setVisibility(View.VISIBLE);
		}
		// QQ手机管家
		if (installedApps.contains(AppUtil.APP_TENCENT_MOBILE)) {
			appTencentButton.setVisibility(View.VISIBLE);
		}
		// 金山
		if (installedApps.contains(AppUtil.APP_JINSHAN_MOBILE)) {
			appJinshanButton.setVisibility(View.VISIBLE);
		}
		// 猎豹
		if (installedApps.contains(AppUtil.APP_LIEBAO_MOBILE)) {
			appLiebaoButton.setVisibility(View.VISIBLE);
		}
		// 猎豹大师
		if (installedApps.contains(AppUtil.APP_LIEBAO_CLEAN_MASTER)) {
			appLiebaoButton.setVisibility(View.VISIBLE);
		}

	}

	@Click({ R.id.secure_app_360, R.id.secure_app_baidu,
			R.id.secure_app_jinshan, R.id.secure_app_liebao,
			R.id.secure_app_liebao_master, R.id.secure_app_tencent,
			R.id.secure_miui })
	public void onClicked(View v) {
		// TODO 进行清理引导
	}
}
