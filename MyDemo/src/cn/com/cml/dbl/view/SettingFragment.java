package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import cn.com.cml.dbl.ModalActivity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.ui.IndicatorItems;
import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.PrefUtil_;

@EFragment(R.layout.fragment_setting)
public class SettingFragment extends BaseFragment {

	private static final String TAG = "SettingFragment";

	@ViewById(R.id.setting_miui)
	TextView miuiSettingView;

	@ViewById(R.id.setting_alarm)
	IndicatorItems alarmItemView;

	@ViewById(R.id.setting_remember_pass)
	IndicatorItems rememberPassItemView;

	@Pref
	PrefUtil_ prefUtil;

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (!hidden) {
			ModalActivity ac = (ModalActivity) getActivity();
			ac.setCustomTitle(R.string.menu_setting);
		}

	}

	@AfterViews
	public void afterViews() {

		// 设置小米权限
		if (AppUtil.isMIUI(getActivity())) {
			miuiSettingView.setVisibility(View.VISIBLE);
		}

		// 关机警报
		alarmItemView.setSwitchChecked(prefUtil.shoutdownAlarm().get());
		alarmItemView.setOnChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.d(TAG, "关机警报开关变化了：" + isChecked);
				prefUtil.edit().shoutdownAlarm().put(isChecked).apply();
			}
		});

		// 记住密码
		rememberPassItemView.setSwitchChecked(prefUtil.rememberPass().get());
		rememberPassItemView.setOnChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				prefUtil.edit().rememberPass().put(isChecked).apply();
			}
		});

		Log.d(TAG, "settingFragment afterview finish");

	}

	@Click(R.id.setting_miui)
	protected void miuiSettingClicked() {
		AppUtil.setAppPriority(getActivity());
	}

	@Click(R.id.setting_about_us)
	protected void aboutUsClicked() {

		changeContainer(
				WebViewFragment_.builder().mLoadUrl(Constant.Url.URL_ABOUT_US)
						.build(), R.string.about_us);
	}

	@Click(R.id.setting_help)
	protected void helpClicked() {

		changeContainer(
				WebViewFragment_.builder().mLoadUrl(Constant.Url.URL_HTLP)
						.build(), R.string.use_help);
	}

	@Click(R.id.setting_agreement)
	protected void agreementClicked() {

		changeContainer(
				WebViewFragment_.builder().mLoadUrl(Constant.Url.URL_AGREEMENT)
						.build(), R.string.agreement);
	}

}
