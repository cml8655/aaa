package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.helper.NotificationHelper;
import cn.com.cml.dbl.util.AppUtil;

@EFragment(R.layout.fragment_setting)
public class SettingFragment extends Fragment {

	@ViewById(R.id.setting_miui)
	TextView miuiSettingView;

	@Bean
	NotificationHelper notifyHelper;

	@AfterViews
	public void afterViews() {

		// 设置小米权限
		if (AppUtil.isMIUI(getActivity())) {
			miuiSettingView.setVisibility(View.VISIBLE);
		}

	}

	@Click(R.id.setting_miui)
	protected void miuiSettingClicked() {
		AppUtil.setAppPriority(getActivity());
	}

	@Click(R.id.setting_status_bar)
	protected void statusBarSettingClicked() {

		notifyHelper.addOrUpdateNotification(null, null);

	}

}
