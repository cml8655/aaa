package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.com.cml.dbl.ModalActivity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.ui.IndicatorItems;
import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.PrefUtil_;

@EFragment(R.layout.fragment_setting)
public class SettingFragment extends BaseFragment {

	private static final String TAG = "SettingFragment";

	@ViewById(R.id.setting_shoutdown_alarm)
	IndicatorItems shoutdownAlarmItem;

	@ViewById(R.id.setting_remember_pass)
	IndicatorItems rememberPassItemView;

	@ViewById(R.id.setting_sms_alarm)
	IndicatorItems smsAlarmView;

	@ViewById(R.id.setting_sms_location)
	IndicatorItems smsLocationView;

	@ViewById(R.id.setting_version)
	IndicatorItems versionView;

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
		// 关机警报
		shoutdownAlarmItem.setSwitchChecked(prefUtil.shoutdownAlarm().get());
		shoutdownAlarmItem.setOnChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
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

		// 短信警报
		smsAlarmView.setSwitchChecked(prefUtil.smsAlaram().get());
		smsAlarmView.setOnChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				prefUtil.edit().smsAlaram().put(isChecked).apply();
			}
		});

		// 短信定位
		smsLocationView.setSwitchChecked(prefUtil.smsLocation().get());
		smsLocationView.setOnChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				prefUtil.edit().smsLocation().put(isChecked).apply();
			}
		});

		String version = AppUtil.version(getActivity());

		versionView
				.setMessageText(getString(R.string.setting_version, version));
	}

	@Click(R.id.setting_about_us)
	protected void aboutUsClicked() {

		String url = Constant.Url.URL_ABOUT_US;

		changeContainer(WebViewFragment_.builder().mLoadUrl(url).build(),
				R.string.about_us);
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

	@Click(R.id.setting_version_container)
	protected void versionUpdate() {
		BmobUpdateAgent.forceUpdate(getActivity());
	}

}
