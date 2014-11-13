package cn.com.cml.pets;

import org.androidannotations.annotations.EApplication;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;

@EApplication
public class PetApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("fff", "application create!!!");
		ActiveAndroid.initialize(this, false);
		SDKInitializer.initialize(this);
	}

}
