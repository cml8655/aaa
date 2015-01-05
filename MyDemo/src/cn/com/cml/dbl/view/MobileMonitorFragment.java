package cn.com.cml.dbl.view;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.UiThread;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.PushListener;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant.Command;
import cn.com.cml.dbl.helper.MapMenuHelper;
import cn.com.cml.dbl.helper.MapMenuHelper.MenuType;
import cn.com.cml.dbl.helper.MapMenuHelper.OnMenuClickListener;
import cn.com.cml.dbl.helper.ReverseCoderHelper;
import cn.com.cml.dbl.listener.BaseFindListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.model.LocationModel;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.service.LocationMonitorService_;
import cn.com.cml.dbl.ui.MapviewTipView;
import cn.com.cml.dbl.ui.MapviewTipView_;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.ValidationUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;

@EFragment(R.layout.fragment_mobile_monitor)
public class MobileMonitorFragment extends BaseFragment implements
		BDLocationListener, OnItemClickListener {

	private static final String TAG = "MobileMonitorFragment";

	public static final String ACTION_LOCATION_RESULT = "cn.com.cml.dbl.view.MobileMonitorFragment.ACTION_LOCATION_RESULT";
	public static final String EXTRA_LOCATION_RESULT = "MobileMonitorFragment.EXTRA_LOCATION_RESULT";

	@FragmentById(R.id.map_fragment)
	SupportMapFragment mapFragment;

	@Bean
	MapMenuHelper mapMenuHelper;

	@Bean
	ReverseCoderHelper reverseHelper;

	@Bean
	ApiRequestServiceClient apiClient;

	private LocationClient baiduClient;

	private BaiduMap map;
	private MapView mapView;

	private DialogFragment dialog;
	private boolean isFirst;

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

			reverseHelper.setMobileLocation(bdLocation);
			reverseHelper.reverseMobileLocationCoder(mobileLocationReverse);
		}
	};

	private OnGetGeoCoderResultListener mobileLocationReverse = new OnGetGeoCoderResultListener() {

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

			if (result.error != SearchResult.ERRORNO.NO_ERROR) {
				showNiftyTip(getString(R.string.monitor_location_error));
				return;
			}

			Activity ac = getActivity();

			if (null != result && null != ac && !ac.isFinishing()) {

				String address = result.getAddress();
				int radius = (int) reverseHelper.getMobileLocation()
						.getRadius();
				showNiftyTip(
						getString(R.string.monitor_location_result, address,
								radius), R.id.mobile_monitor_tip_container);
			}

		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {

		}
	};

	private OnGetGeoCoderResultListener userlocationReverse = new OnGetGeoCoderResultListener() {

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

			if (result.error != SearchResult.ERRORNO.NO_ERROR) {
				showNiftyTip(getString(R.string.monitor_location_error),
						R.id.mobile_monitor_tip_container);
				return;
			}

			Activity ac = getActivity();

			if (null != result && null != ac && !ac.isFinishing()) {
				String address = result.getAddress();
				int radius = (int) reverseHelper.getUserLocation().getRadius();
				showNiftyTip(
						getString(R.string.monitor_location_result, address,
								radius), R.id.mobile_monitor_tip_container);
			}

		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
		}
	};

	private OnMenuClickListener menuItemClickListener = new OnMenuClickListener() {

		@Override
		public void onClick(View v, MenuType menuType) {

			if (map == null) {
				return;
			}

			switch (menuType) {

			case TYPE_SETTING:
				showNiftyTip("TYPE_MOBILE上海市地方：。。。。",
						R.id.mobile_monitor_tip_container);
				break;

			case TYPE_USER:

				// 根据坐标获取用户位置
				reverseHelper.reverseUserLocationCoder(userlocationReverse);

				BDLocation myLocation = reverseHelper.getUserLocation();

				if (null != myLocation) {
					map.animateMapStatus(MapStatusUpdateFactory
							.newLatLng(new LatLng(myLocation.getLatitude(),
									myLocation.getLongitude())));
				}

				break;

			case TYPE_MOBILE:
				map.animateMapStatus(MapStatusUpdateFactory
						.newLatLng(new LatLng(31.245951, 121.51377)));
				showNiftyTip("TYPE_MOBILE上海市地方：。。。。",
						R.id.mobile_monitor_tip_container);

				break;

			}

		}
	};

	@AfterViews
	public void initConfig() {

		Log.d(TAG, "MobileMonitorFragment==》initConfig");

		dialog =DialogUtil.dataLoadingDialog(R.string.locate_user); 
		dialog.show(getFragmentManager(), "location");

		DialogUtil.remotePassInputDialog(R.string.alarm_cancel, 1, this).show(
				getFragmentManager(), "ddd");

		// 设置菜单点击事件
		mapMenuHelper.bindListener(menuItemClickListener);

		// 百度地图初始化
		map = mapFragment.getBaiduMap();
		map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		map.setTrafficEnabled(true);
		map.setMyLocationEnabled(true);
		map.setBuildingsEnabled(true);

		// 放大地图
		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
		map.setMapStatus(u);

		mapView = mapFragment.getMapView();
		mapView.setScrollbarFadingEnabled(true);
		mapView.showScaleControl(true);
		mapView.showZoomControls(true);

		// 定位功能初始化
		baiduClient = new LocationClient(getActivity());

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(3000);// 设置发起定位请求的间隔时间为1000ms
		option.setOpenGps(true);
		option.setTimeOut(20000);// 20s延迟
		option.setPriority(LocationClientOption.GpsFirst);

		baiduClient.setLocOption(option);
		baiduClient.registerLocationListener(this);

		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
		MyLocationConfiguration config = new MyLocationConfiguration(
				LocationMode.FOLLOWING, true, null);

		map.setMyLocationConfigeration(config);

		// TODO 用户输入远程密码后进行定位功能开启
		sendLocationCommand();

	}

	@Override
	public void onResume() {

		if (null != baiduClient && !baiduClient.isStarted()) {
			baiduClient.start();
		}
		super.onResume();

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
	public void onPause() {

		if (null != baiduClient && baiduClient.isStarted()) {
			baiduClient.stop();
		}
		super.onPause();
	}

	/**
	 * 当调用show/hide时，fragment生命周期不继续走动了
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (baiduClient != null) {
			if (hidden) {// 不在最前端界面显示
				if (null != baiduClient && baiduClient.isStarted()) {
					mapView.onPause();
					baiduClient.stop();
				}
			} else {// 重新显示到最前端中
				if (null != baiduClient && !baiduClient.isStarted()) {
					mapView.onResume();
					baiduClient.start();
				}
			}
		}
	}

	@UiThread
	protected void repaintMap(LocationModel mobileLocation,
			BDLocation userLocation) {

		map.clear();

		// 添加 坐标提示信息
		String tip = getActivity().getString(R.string.maptip_none);

		// 添加用户位置
		if (userLocation != null) {

			// MyLocationData myData = new MyLocationData.Builder()
			// .accuracy(userLocation.getRadius()).direction(100)
			// .latitude(userLocation.getLatitude())
			// .longitude(userLocation.getLongitude()).build();
			//
			// map.setMyLocationData(myData);
			// // 添加用户信息
			LatLng lat = new LatLng(userLocation.getLatitude(),
					userLocation.getLongitude());

			BitmapDescriptor icon = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marka);

			map.addOverlay(new MarkerOptions().position(lat).icon(icon));

			// 显示大概范围
			// showLocationRadius(userLocation.getLatitude(),
			// userLocation.getLongitude(), (int) userLocation.getRadius());

		}

		// 手机和用户都定位到了，连接两点
		if (userLocation != null && mobileLocation != null) {

			int distance = cn.com.cml.dbl.util.DistanceUtil.calculateDistance(
					userLocation, mobileLocation);

			tip = getActivity().getString(R.string.maptip, distance);

			// 创建两点的连线
			List<LatLng> pts = new ArrayList<LatLng>();

			pts.add(new LatLng(mobileLocation.getLatitude(), mobileLocation
					.getLongitude()));
			pts.add(new LatLng(userLocation.getLatitude(), userLocation
					.getLongitude()));

			// 构建用户绘制多边形的Option对象
			OverlayOptions polygonOption = new PolylineOptions().points(pts)
					.color(Color.BLUE);
			// 在地图上添加多边形Option，用于显示
			map.addOverlay(polygonOption);
		}

		if (mobileLocation != null) {
			// 添加用户信息
			LatLng lat = new LatLng(mobileLocation.getLatitude(),
					mobileLocation.getLongitude());

			BitmapDescriptor icon = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marka);

			map.addOverlay(new MarkerOptions().position(lat).icon(icon));

			// 显示大概范围
			showLocationRadius(mobileLocation.getLatitude(),
					mobileLocation.getLongitude(),
					(int) mobileLocation.getRadius());

			// // 构造定位数据,设置手机定位数据
			// MyLocationData locData = new MyLocationData.Builder()
			// .accuracy(mobileLocation.getRadius()).direction(0)
			// .latitude(mobileLocation.getLatitude())
			// .longitude(mobileLocation.getLongitude()).build();
			//
			// // 设置定位数据
			// map.setMyLocationData(locData);

			// 添加提示信息
			addMapInfo(mobileLocation.getLatitude(),
					mobileLocation.getLongitude(), R.string.icon_spin5, tip);
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

	private void addMapInfo(double latitude, double longitude, int icon,
			String text) {

		// 设置提示
		MapviewTipView tip = MapviewTipView_.build(getActivity());

		tip.bind(icon, text);

		// 定义用于显示该InfoWindow的坐标点
		LatLng pt = new LatLng(latitude, longitude);
		// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
		InfoWindow mInfoWindow = new InfoWindow(tip, pt, -47);

		// 显示InfoWindow
		map.showInfoWindow(mInfoWindow);
	}

	@Override
	public void onReceiveLocation(BDLocation location) {

		if (getActivity() == null || !isValid(location)) {
			return;
		}

		reverseHelper.setUserLocation(location);

		if (!isFirst) {
			isFirst = true;
			dialog.dismiss();
		}
		LocationModel mobileLocation = new LocationModel();

		mobileLocation.setLatitude(31.245951);
		mobileLocation.setLongitude(121.51377);

		repaintMap(mobileLocation, location);

		// MyLocationData myData = new MyLocationData.Builder()
		// .accuracy(location.getRadius()).direction(100)
		// .latitude(location.getLatitude())
		// .longitude(location.getLongitude()).build();
		//
		// map.setMyLocationData(myData);
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

	@Override
	public void onReceivePoi(BDLocation location) {

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
		// TODO Auto-generated method stub

	}

}
