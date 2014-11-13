package cn.com.cml.pets.view;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import cn.com.cml.pets.R;
import cn.com.cml.pets.util.DeviceUtil;

@EFragment(R.layout.fragment_userinfo)
public class UserInfoFragment extends Fragment {

	@ViewById(R.id.device_show_tv)
	TextView msgShowView;

	@Bean
	DeviceUtil deviceUtil;

	@Click(R.id.load_imei)
	public void loadImei() {
		msgShowView.setText(deviceUtil.deviceImei());
	}

	@Click(R.id.load_mac)
	public void loadMAC() {
		msgShowView.setText(deviceUtil.devideMac());
	}

	@Click(R.id.load_imei_mac)
	public void LoadImeiMAc() {
		msgShowView.setText("imei-mac" + deviceUtil.phoneNumber());
	}

	@Click(R.id.toggle_gps)
	public void toggleGPS() {

		// Settings.Secure.setLocationProviderEnabled(getActivity()
		// .getContentResolver(), "gps", true);

		String value = Settings.Secure.getString(getActivity()
				.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		// value contains("gps")说明有gps已经打开
		//
		// msgShowView.setText("gps:" + value);
		//
		// Intent gpsIntent = new Intent();
		// gpsIntent.setClassName("com.android.settings",
		// "com.android.settings.widget.SettingsAppWidgetProvider");
		// gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		// gpsIntent.setData(Uri.parse("custom:3"));
		//
		// try {
		// PendingIntent.getBroadcast(getActivity(), 0, gpsIntent, 0).send();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// value = Settings.Secure.getString(getActivity().getContentResolver(),
		// Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		msgShowView.setText("gps2:" + value);
	}
}
