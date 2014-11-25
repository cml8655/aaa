package cn.com.cml.dbl.view;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DeviceUtil;

@EFragment(R.layout.fragment_userinfo)
public class UserInfoFragment extends Fragment {

	@ViewById(R.id.device_show_tv)
	TextView msgShowView;


	@Click(R.id.load_imei)
	public void loadImei() {
	}

	@Click(R.id.load_mac)
	public void loadMAC() {
	}

	@Click(R.id.load_imei_mac)
	public void LoadImeiMAc() {
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
