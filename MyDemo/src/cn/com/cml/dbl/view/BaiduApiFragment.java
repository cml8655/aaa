package cn.com.cml.dbl.view;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.model.LocationModel;
import cn.com.cml.dbl.ui.MapviewTipView;
import cn.com.cml.dbl.ui.MapviewTipView_;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.PopupWindowUtil;

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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;

@EFragment(R.layout.baiduapi_fragment)
public class BaiduApiFragment extends Fragment {

	private static final String TAG = "BaiduApiFragment";

	private LocationClient baiduClient;

	@ViewById(R.id.mylocation)
	TextView myLocationView;

	@ViewById(R.id.mapView)
	MapView mapView;

	@Bean
	PopupWindowUtil windowUtil;

	@ViewById(R.id.map_view_container)
	RelativeLayout mapViewContainer;

	private BaiduMap map;

	private GeoCoder coorSearch = GeoCoder.newInstance();

	private LocationModel mobileLocation;// 手机所在位置

	private DialogFragment dialog;

	private boolean isFirstLocateMobile = true;// 第一次定位到手机

	@AfterViews
	public void initLocationComponent() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_spin5,
				R.string.locate_user);
		dialog.show(getFragmentManager(), "location");

		// 设置反地理编码监听器
		coorSearch.setOnGetGeoCodeResultListener(new GeoGetResultListener());

		// 开始下载百度地图，上海地区：
		MKOfflineMap offlinMap = new MKOfflineMap();

		offlinMap.init(new MKOfflineMapListener() {
			/**
			 * type - 事件类型: MKOfflineMap.TYPE_NEW_OFFLINE,
			 * MKOfflineMap.TYPE_DOWNLOAD_UPDATE, MKOfflineMap.TYPE_VER_UPDATE.
			 * state - 事件状态: 当type为TYPE_NEW_OFFLINE时，表示新安装的离线地图数目.
			 * 当type为TYPE_DOWNLOAD_UPDATE时，表示更新的城市ID.
			 */
			@Override
			public void onGetOfflineMapState(int type, int state) {

				String str = null;

				if (type == MKOfflineMap.TYPE_NEW_OFFLINE) {
					str = "新安装地图数目：" + state;
				} else if (MKOfflineMap.TYPE_DOWNLOAD_UPDATE == type) {
					str = "更新城市ID:";
				} else {
					str = "未解释：" + type + ":" + state;
				}

				Log.d(TAG, "onGetOfflineMapState:" + str);
			}
		});

		// boolean offineSH = offlinMap.start(289);

		// int imoprtMaps = offlinMap.importOfflineData(false);

		// ArrayList<MKOLSearchRecord> records = offlinMap.getHotCityList();
		//
		// for (MKOLSearchRecord record : records) {
		// Log.d("===", record.cityName + ":" + record.cityID + ","
		// + record.size);
		// }

		// 百度地图初始化
		map = mapView.getMap();
		// 普通地图
		map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		map.setTrafficEnabled(true);
		map.setMyLocationEnabled(true);
		mapView.setScrollbarFadingEnabled(true);
		mapView.showScaleControl(true);
		mapView.showZoomControls(true);

		UiSettings setting = map.getUiSettings();
		setting.setAllGesturesEnabled(true);

		// 最大放大倍数
		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
		map.setMapStatus(u);

		map.setBuildingsEnabled(true);
		// BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
		// .fromResource(R.drawable.icon_marka);
		//
		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
		// MyLocationConfiguration config = new MyLocationConfiguration(
		// LocationMode.FOLLOWING, true, mCurrentMarker);
		//
		// map.setMyLocationConfigeration(config);

		// 定位功能初始化
		baiduClient = new LocationClient(getActivity());

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(3000);// 设置发起定位请求的间隔时间为1000ms
		option.setOpenGps(true);
		option.setTimeOut(20000);// 20s延迟
		// option.setAddrType("detail");
		// option.setServiceName("myserver");
		option.setPriority(LocationClientOption.GpsFirst);
		Log.d(TAG, "	option.getAddrType():detail:" + option.getAddrType());
		// option.setIsNeedAddress(true);

		// baiduClient.setForBaiduMap(true);
		baiduClient.setLocOption(option);
		baiduClient.registerLocationListener(new BaiDuLocationListener());
		// baiduClient.start();

	}

	@Override
	public void onResume() {

		if (null != baiduClient && !baiduClient.isStarted()) {
			baiduClient.start();
		}

		mapView.onResume();

		super.onResume();

	}

	/**
	 * 当调用show/hide时，fragment生命周期不继续走动了
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (baiduClient != null) {

			if (hidden) {// 不在最前端界面显示

				baiduClient.stop();
				mapView.onPause();
			} else {// 重新显示到最前端中

				mapView.onResume();
				baiduClient.start();
			}
		}
	}

	@Background
	protected void loadMobileLocation() {
		LocationModel location = new LocationModel();
		location.setLatitude(31.245951);
		location.setLongitude(121.51377);

		this.mobileLocation = location;
		repaintMap(location, baiduClient.getLastKnownLocation());

		if (isFirstLocateMobile) {// 第一次定位，移动到特定位置
			isFirstLocateMobile = false;
			transitMapAt(location.getLatitude(), location.getLongitude());
		}

	}

	@UiThread
	protected void transitMapAt(double latitude, double longitude) {
		// 定位我的位置
		LatLng lat = new LatLng(latitude, longitude);

		// 移动地图位置到我的位置中
		MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(lat);
		map.animateMapStatus(update);
	}

	@UiThread
	protected void repaintMap(LocationModel mobileLocation,
			BDLocation userLocation) {

		map.clear();

		// 添加 坐标提示信息
		String tip = getActivity().getString(R.string.maptip_none);

		// 添加用户位置
		if (userLocation != null) {
			// 添加用户信息
			LatLng lat = new LatLng(userLocation.getLatitude(),
					userLocation.getLongitude());

			BitmapDescriptor icon = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marka);
			map.addOverlay(new MarkerOptions().position(lat).icon(icon));

			// 显示大概范围
			showLocationRadius(userLocation.getLatitude(),
					userLocation.getLongitude(), (int) userLocation.getRadius());

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
		map.addOverlay(groundOverlay);
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
	public void onDestroy() {
		mapView.onDestroy();
		super.onDestroy();
	}

	@Click(R.id.dumy_search_btn)
	public void onDumyClick() {
		loadMobileLocation();
	}

	@Click(R.id.map_search_btn)
	public void onSearchClick() {
		windowUtil.toggleRouteSelectWindow(mapViewContainer);
	}

	class BaiDuLocationListener implements BDLocationListener {

		private boolean isFirst = true;

		@Override
		public void onReceiveLocation(BDLocation location) {

			repaintMap(mobileLocation, location);

			if (isFirst) {

				transitMapAt(location.getLatitude(), location.getLongitude());

				dialog.dismiss();
			}

			isFirst = false;

			// 反编码
			// mobileRadius = location.getRadius();
			// coorSearch.reverseGeoCode(new ReverseGeoCodeOption()
			// .location(new LatLng(location.getLatitude(), location
			// .getLongitude())));

		}

		@Override
		public void onReceivePoi(BDLocation location) {
		}
	}

	class GeoGetResultListener implements OnGetGeoCoderResultListener {

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

			Log.i(TAG, "onGetReverseGeoCodeResult:" + result.getAddress());

			// 添加定位线
			List<LatLng> points = new ArrayList<LatLng>();

			points.add(result.getLocation());
			points.add(new LatLng(31.255336, 121.591384));

			OverlayOptions ooPolyline = new PolylineOptions().width(5)
					.color(Color.BLUE).points(points);

			map.addOverlay(ooPolyline);

			map.setMapStatus(MapStatusUpdateFactory.newLatLng(result
					.getLocation()));

			int distance = (int) DistanceUtil.getDistance(new LatLng(31.255336,
					121.591384), result.getLocation());

			myLocationView.setText("我的位置：" + result.getAddress() + ",距离约："
					+ distance + "米");
		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
		}
	}
}
