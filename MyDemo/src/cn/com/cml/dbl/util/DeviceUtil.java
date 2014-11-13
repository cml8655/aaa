package cn.com.cml.dbl.util;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

import android.os.Build;
import android.telephony.TelephonyManager;

@EBean
public class DeviceUtil {

	@SystemService
	TelephonyManager phoneManager;

	public String deviceImei() {
		return phoneManager.getDeviceId();
	}

	public String phoneNumber() {
		return phoneManager.getLine1Number();
	}

	public String devideMac() {
		// we make this look like a valid IMEI
		return "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10
				+ Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
				+ Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
				+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
				+ Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
				+ Build.TAGS.length() % 10 + Build.TYPE.length() % 10
				+ Build.USER.length() % 10; // 13 digits
	}
}
