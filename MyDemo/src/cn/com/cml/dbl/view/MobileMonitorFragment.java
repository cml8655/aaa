package cn.com.cml.dbl.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.PushListener;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant.Command;
import cn.com.cml.dbl.helper.LocationHelper;
import cn.com.cml.dbl.helper.MapFactory;
import cn.com.cml.dbl.helper.MapHelper;
import cn.com.cml.dbl.helper.MapHelper.LocationStatusListener;
import cn.com.cml.dbl.listener.BaseFindListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.model.LocationModel;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;

@EFragment(R.layout.fragment_mobile_monitor)
public class MobileMonitorFragment extends BaseFragment implements
		OnItemClickListener, LocationStatusListener {

	private static final String TAG = "MobileMonitorFragment";

	public static final String ACTION_LOCATION_RESULT = "cn.com.cml.dbl.view.MobileMonitorFragment.ACTION_LOCATION_RESULT";
	public static final String EXTRA_LOCATION_RESULT = "MobileMonitorFragment.EXTRA_LOCATION_RESULT";
	private static final int LOCATE_INTERVAL = 10000;
	
	@FragmentById(R.id.map_fragment)
	SupportMapFragment mapFragment;

	private MapHelper mapHelper;
	private LocationHelper locationHelper;
	@Bean
	ApiRequestServiceClient apiClient;

	private DialogFragment dialog;

	// 手机定位返回数据
	private BroadcastReceiver mobileLocationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (!intent.hasExtra(EXTRA_LOCATION_RESULT)) {
				return;
			}

			String locationStr = intent.getStringExtra(EXTRA_LOCATION_RESULT);

			LocationModel model = new Gson().fromJson(locationStr,
					LocationModel.class);

			BDLocation bdLocation = new BDLocation();

			bdLocation.setLatitude(model.getLatitude());
			bdLocation.setLongitude(model.getLongitude());
			bdLocation.setLocType(model.getLocType());
			bdLocation.setRadius(model.getRadius());

			locationHelper.setMobileLocation(bdLocation);
			mapHelper.animateTo(new LatLng(model.getLatitude(), model
					.getLongitude()));
		}
	};

	@AfterViews
	public void initConfig() {

		Log.d(TAG, "MobileMonitorFragment==》initConfig");

		mapHelper = new MapFactory().createBaiduMapHelper(mapFragment);
		mapHelper.initMap(LOCATE_INTERVAL);// 10s定位一次
		mapHelper.setLocationStatusListener(this);

		locationHelper = new LocationHelper(this);

		dialog = DialogUtil.dataLoadingDialog(R.string.locate_user);
		dialog.show(getFragmentManager(), "location");

		// 远程密码输入
		// DialogUtil.remotePassInputDialog(R.string.alarm_cancel, 1,
		// this).show(
		// getFragmentManager(), "ddd");

		// TODO 用户输入远程密码后进行定位功能开启
		// sendLocationCommand();

	}

	@Click(R.id.map_menu_setting)
	public void settingClick(View v) {

		BDLocation userLocation = locationHelper.getUserLocation();
		BDLocation mobileLocation = locationHelper.getMobileLocation();

		if (null != userLocation && null != mobileLocation) {
			LatLng start = new LatLng(userLocation.getLatitude(),
					userLocation.getLongitude());
			LatLng end = new LatLng(mobileLocation.getLatitude(),
					mobileLocation.getLongitude());
			mapHelper.drawLine(start, end);
			int distance = (int) DistanceUtil.getDistance(start, end);
			String distanceStr = getActivity().getString(
					R.string.monitor_distance, distance);
			showNiftyTip(distanceStr,R.id.mobile_monitor_tip_container);
		}

	}

	@Click(R.id.map_menu_user)
	public void userClick(View v) {

		BDLocation userLocation = locationHelper.getUserLocation();

		if (null != userLocation) {

			LatLng lat = new LatLng(userLocation.getLatitude(),
					userLocation.getLongitude());

			mapHelper.animateTo(lat);
			mapHelper.addWindowInfo(lat, R.string.icon_spin5,
					R.string.monitor_my_location);
			locationHelper.reverseUserLocationCoder();
		}
	}

	@Click(R.id.map_menu_mobile)
	public void mobileClick(View v) {

		// TODO 去除
		locationHelper.setMobileLocation(new BDLocation(121.51377, 31.245951,
				11));
		BDLocation mobileLocation = locationHelper.getMobileLocation();

		if (null != mobileLocation) {

			LatLng lat = new LatLng(mobileLocation.getLatitude(),
					mobileLocation.getLongitude());

			mapHelper.animateTo(lat);
			mapHelper.addWindowInfo(lat, R.string.icon_spin5,
					R.string.monitor_mobile_location);
			locationHelper.reverseMobileLocationCoder();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		IntentFilter filter = new IntentFilter(ACTION_LOCATION_RESULT);
		getActivity().registerReceiver(mobileLocationReceiver, filter);
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mobileLocationReceiver);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		mapHelper.startLocationMonitor();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapHelper.stopLocationMonitor();
	}

	/**
	 * 当调用show/hide时，fragment生命周期不继续走动了
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (hidden) {// 不在最前端界面显示
			mapHelper.stopLocationMonitor();
		} else {// 重新显示到最前端中
			mapHelper.startLocationMonitor();
		}
	}

	/**
	 * 指定圆心，半径，显示大概范围
	 * 
	 * @param latitude
	 * @param longitude
	 * @param radius
	 */
	private void showLocationRadius(double latitude, double longitude,
			int radius) {

		// OverlayOptions circle = new CircleOptions()
		// .center(new LatLng(latitude, longitude)).radius(radius)
		// .stroke(new Stroke(5, Color.RED)).fillColor(Color.GREEN);

		OverlayOptions groundOverlay = new GroundOverlayOptions()
				.dimensions(radius)
				// 设置 ground 覆盖物的宽度，单位：米， 高度按图片宽高比例
				.position(new LatLng(latitude, longitude))
				.transparency(0.3f)
				.image(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_geo));

		// OverlayOptions dotOverlay = new DotOptions()
		// .center(new LatLng(latitude, longitude)).color(Color.GREEN)
		// .radius(radius);
		// map.addOverlay(groundOverlay);
	}

	private void sendLocationCommand() {

		// 查询用户手机绑定情况
		apiClient.bindDeviceQuery(new BaseFindListener<MobileBind>(
				getActivity()) {

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onSuccess(List<MobileBind> result) {

				// TODO 没有绑定手机，跳到绑定界面
				if (result.size() == 0) {
					Log.d(TAG, "没有找到绑定手机");
					return;
				}

				MobileBind bindDevice = result.get(0);

				// TODO 输入远程密码
				// // 密码错误
				// if (!ValidationUtil.equals(pass,
				// bindDevice.getBindPassword())) {
				//
				// DialogUtil.showTip(getActivity(),
				// getString(R.string.password_error));
				//
				// return;
				// }

				// 启动警报

				Command locationCommand = Command.DINGWEI_ENUM;
				locationCommand.setBindPass("123");
				locationCommand.setFrom(BmobUser.getCurrentUser(getActivity())
						.getUsername());

				apiClient.sendPushCommand(locationCommand,
						bindDevice.getImei(), new PushListener() {

							@Override
							public void onSuccess() {
								Log.d(TAG, "alaramCommand发送onSuccess");
							}

							@Override
							public void onFailure(int arg0, String errorMsg) {
								Log.d(TAG, "发送失败：" + errorMsg);
							}
						});
			}
		});
	}

	@Override
	public void onClick(DialogInterface dialog, long id, int requestId) {

	}

	@Override
	public void onLocationReceive(boolean isValid, BDLocation location) {
		if (isValid) {
			locationHelper.setUserLocation(location);
		}
		if (!dialog.isHidden()) {
			dialog.dismiss();
		}

	}

}
