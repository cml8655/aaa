package cn.com.cml.dbl;

import org.androidannotations.annotations.EApplication;

import android.app.Application;
import cn.bmob.v3.Bmob;
import cn.com.cml.dbl.util.DeviceUtil;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;

@EApplication
public class PetApplication extends Application {

	public static String deviceId;

	@Override
	public void onCreate() {
		super.onCreate();
		Bmob.initialize(this, "8edefd1dfad9502b4a3be158d357ca30");
		ActiveAndroid.initialize(this, false);
		SDKInitializer.initialize(this);

		deviceId = DeviceUtil.deviceImei(this);
	}

}
