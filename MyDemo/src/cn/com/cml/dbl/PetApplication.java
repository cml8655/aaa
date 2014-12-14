package cn.com.cml.dbl;

import org.androidannotations.annotations.EApplication;

import android.app.Application;
import android.content.IntentFilter;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.com.cml.dbl.listener.GlobalBaseListener;
import cn.com.cml.dbl.mode.api.InstallationModel;
import cn.com.cml.dbl.model.SmsModel;
import cn.com.cml.dbl.service.SMSHandler;
import cn.com.cml.dbl.service.SmsContentObserver;
import cn.com.cml.dbl.util.DeviceUtil;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;

@EApplication
public class PetApplication extends Application {

	public static String deviceId;

	@Override
	public void onCreate() {

		super.onCreate();

		deviceId = DeviceUtil.deviceImei(this);

		Bmob.initialize(this, "8edefd1dfad9502b4a3be158d357ca30");

		InstallationModel installation = new InstallationModel(this);
		installation.setImei(deviceId);
		installation.save(this);

		BmobPush.startWork(getApplicationContext(),
				"8edefd1dfad9502b4a3be158d357ca30");
		// 初始化版本信息
		BmobUpdateAgent.initAppVersion(getApplicationContext());

		// 初始化数据库信息
		ActiveAndroid.initialize(this, false);

		// 百度地图
		SDKInitializer.initialize(this);

		getContentResolver().registerContentObserver(SmsModel.SMS_CONTENT_URI,
				true, new SmsContentObserver(this, new SMSHandler(this)));

		IntentFilter filter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(2147483647);

		this.registerReceiver(new GlobalBaseListener(), filter);
	}

}
