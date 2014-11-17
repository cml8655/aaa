package cn.com.cml.dbl.view;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.PopupWindowUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDoubleClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
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

	private MobileLocationChangeListener mobileLocationChangeListener;

	private BaiduMap map;

	private GeoCoder coorSearch = GeoCoder.newInstance();

	private float mobileRadius = -1;

	@AfterViews
	public void initLocationComponent() {

		mobileLocationChangeListener = new MobileLocationChangeListener();

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
		// 最大放大倍数
		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(15);
		map.setMapStatus(u);

		BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);

		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
		MyLocationConfiguration config = new MyLocationConfiguration(
				LocationMode.FOLLOWING, true, mCurrentMarker);

		map.setMyLocationConfigeration(config);

		initListener();

		// 定位功能初始化
		baiduClient = new LocationClient(getActivity());

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(30000);// 设置发起定位请求的间隔时间为1000ms
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

	private void initListener() {
		map.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng point) {
				updateMapState();
			}

			public boolean onMapPoiClick(MapPoi poi) {
				return false;
			}
		});
		map.setOnMapLongClickListener(new OnMapLongClickListener() {
			public void onMapLongClick(LatLng point) {
				updateMapState();
			}
		});
		map.setOnMapDoubleClickListener(new OnMapDoubleClickListener() {
			public void onMapDoubleClick(LatLng point) {
				updateMapState();
			}
		});
		map.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
			public void onMapStatusChangeStart(MapStatus status) {
				updateMapState();
			}

			public void onMapStatusChangeFinish(MapStatus status) {
				updateMapState();
			}

			public void onMapStatusChange(MapStatus status) {
				updateMapState();
			}
		});
	}

	/**
	 * 更新地图状态显示面板
	 */
	private void updateMapState() {
		MapStatus ms = map.getMapStatus();
		String state = String.format("zoom=%.1f rotate=%d overlook=%d",
				ms.zoom, (int) ms.rotate, (int) ms.overlook);
		// myLocationView.setText(state);
	}

	@Override
	public void onResume() {

		if (null != baiduClient && !baiduClient.isStarted()) {
			baiduClient.start();
		}

		mapView.onResume();

		registerMobileLocationListener();

		super.onResume();

	}

	private void registerMobileLocationListener() {
		getActivity().registerReceiver(
				mobileLocationChangeListener,
				new IntentFilter(
						MobileLocationChangeListener.ACTION_LOCATION_CHANGE));
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
				getActivity().unregisterReceiver(mobileLocationChangeListener);
				
			} else {// 重新显示到最前端中
				
				mapView.onResume();
				baiduClient.start();
				registerMobileLocationListener();
			}
		}
	}

	@Override
	public void onDestroy() {
		mapView.onDestroy();
		super.onDestroy();
	}

	@Click(R.id.map_search_btn)
	public void onSearchClick() {
		windowUtil.toggleRouteSelectWindow(mapViewContainer);
	}

	class BaiDuLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			map.clear();

			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(0).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();

			// 设置定位数据
			map.setMyLocationData(locData);

			mobileRadius = location.getRadius();
			coorSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(new LatLng(location.getLatitude(), location
							.getLongitude())));

		}

		@Override
		public void onReceivePoi(BDLocation location) {
			Toast.makeText(getActivity(),
					"onReceivePoi：" + location.getAddrStr(), Toast.LENGTH_SHORT)
					.show();

		}
	}

	/**
	 * 手机定位广播接收器
	 * 
	 * @author 陈孟琳
	 *
	 *         2014年11月17日
	 */
	class MobileLocationChangeListener extends BroadcastReceiver {

		public static final String ACTION_LOCATION_CHANGE = "cn.com.cml.dbl.view.MobileLocationChangeListener.location.change";

		@Override
		public void onReceive(Context context, Intent intent) {

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

			// 添加手机位置
			map.addOverlay(new MarkerOptions()
			// .position(result.getLocation())
					.position(new LatLng(31.255336, 121.591384)).icon(
							BitmapDescriptorFactory
									.fromResource(R.drawable.icon_marka)));

			map.setMapStatus(MapStatusUpdateFactory.newLatLng(result
					.getLocation()));

			Button button = new Button(getActivity());
			button.setBackgroundColor(Color.GREEN);
			button.setText("手机位置："
					+ (mobileRadius == -1 ? "" : "精确度：" + mobileRadius + "米"));

			// 定义用于显示该InfoWindow的坐标点
			LatLng pt = new LatLng(31.255336, 121.591384);
			// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
			InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);

			int distance = (int) DistanceUtil.getDistance(new LatLng(31.255336,
					121.591384), result.getLocation());

			// 显示InfoWindow
			map.showInfoWindow(mInfoWindow);

			myLocationView.setText("我的位置：" + result.getAddress() + ",距离约："
					+ distance + "米");
		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
		}
	}
}
