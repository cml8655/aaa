package cn.com.cml.dbl.service;

import java.util.TimerTask;

import org.androidannotations.annotations.EService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.model.LocationModel;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;

@EService
public class LocationMonitorService extends Service {

	private static final String TAG = "LocationMonitorService";

	public static final String EXTRA_IMEI = "cn.com.cml.dbl.service.LocationMonitorService.EXTRA_IMEI";

	private LocationClient locationClient;

	private Handler handler = new Handler();

	private String receiveDeviceImei;

	private TimerTask autoExitTask = new TimerTask() {

		@Override
		public void run() {
			stopSelf();
		}
	};

	private BDLocationListener locationListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (!isValid(location)) {
				return;
			}

			LocationModel model = new LocationModel();

			model.setLatitude(location.getLatitude());
			model.setLongitude(location.getLongitude());
			model.setLocType(location.getLocType());
			model.setRadius(location.getRadius());

			PushService_
					.intent(getApplicationContext())
					.pushLocationMonitorData(receiveDeviceImei,
							new Gson().toJson(model)).start();
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

		}

		private boolean isValid(BDLocation location) {

			switch (location.getLocType()) {

			case 66:// 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果\n
			case 65:// ： 定位缓存的结果。
			case 68:// 网络连接失败时，查找本地离线定位时对应的返回结果
			case 161:// 表示网络定位结果
				return true;
			}

			return false;
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "手机定位服务启动。。。");

		locationClient = new LocationClient(getApplicationContext());

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(3000);// 设置发起定位请求的间隔时间为1000ms
		option.setOpenGps(true);
		option.setTimeOut(20000);// 20s延迟
		option.setPriority(LocationClientOption.GpsFirst);

		locationClient.setLocOption(option);
		locationClient.registerLocationListener(locationListener);
	}

	@Override
	public void onDestroy() {

		locationClient.unRegisterLocationListener(locationListener);
		locationClient.stop();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		receiveDeviceImei = intent.getStringExtra(EXTRA_IMEI);

		Log.d(TAG, "手机定位服务启动。。。receiveDeviceImei:" + receiveDeviceImei);

		if (!locationClient.isStarted() && null != receiveDeviceImei) {
			locationClient.start();
		}

		handler.removeCallbacks(autoExitTask);
		handler.postDelayed(autoExitTask, Constant.LOCATION_CLIENT_EXPIRES);

		return super.onStartCommand(intent, flags, startId);
	}

}
