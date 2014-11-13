package cn.com.cml.pets.view;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.cml.pets.R;

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
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

@EFragment(R.layout.baiduapi_fragment)
public class BaiduApiFragment extends Fragment {

	private LocationClient baiduClient;

	@ViewById(R.id.mapView)
	MapView mapView;

	@ViewById(R.id.map_msg)
	TextView mapMsgView;

	private BaiduMap map;

	@AfterViews
	public void initLocationComponent() {

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

				Log.d("===", "onGetOfflineMapState:" + str);
			}
		});

		boolean offineSH = offlinMap.start(289);

		int imoprtMaps = offlinMap.importOfflineData(false);

		Log.d("===", "下载上海：" + offineSH + ",导入包的个数：" + imoprtMaps);

		ArrayList<MKOLSearchRecord> records = offlinMap.getHotCityList();

		for (MKOLSearchRecord record : records) {
			Log.d("===", record.cityName + ":" + record.cityID + ","
					+ record.size);
		}

		// 百度地图初始化
		map = mapView.getMap();
		// 普通地图
		map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		map.setTrafficEnabled(true);
		map.setMyLocationEnabled(true);

		initListener();

		// 定位功能初始化
		baiduClient = new LocationClient(getActivity());

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(30000);// 设置发起定位请求的间隔时间为1000ms
		option.setOpenGps(true);
		option.setTimeOut(20000);// 20s延迟
		option.setAddrType("detail");
		option.setServiceName("myserver");
		option.setPriority(LocationClientOption.GpsFirst);
		Log.d("===", "	option.getAddrType():detail:" + option.getAddrType());
		// option.setIsNeedAddress(true);

		baiduClient.setForBaiduMap(true);
		baiduClient.setLocOption(option);
		baiduClient.registerLocationListener(new BaiDuLocationListener());
		baiduClient.start();

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
		mapMsgView.setText(state);
	}

	@Override
	public void onResume() {

		if (null != baiduClient && !baiduClient.isStarted()) {
			baiduClient.start();
		}
		mapView.onResume();

		super.onResume();
	}

	@Override
	public void onPause() {

		if (null != baiduClient) {
			baiduClient.stop();
		}

		mapView.onPause();

		super.onPause();
	}

	@Override
	public void onDestroy() {
		mapView.onDestroy();
		super.onDestroy();
	}

	class BaiDuLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			// CoordinateConverter converter = new CoordinateConverter();
			// LatLng lat = converter
			// .from(CoordType.COMMON)
			// .coord(new LatLng(location.getLatitude(), location
			// .getLongitude())).convert();

			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(0).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			// 设置定位数据
			map.setMyLocationData(locData);
			// map.setBaiduHeatMapEnabled(true);
			map.setTrafficEnabled(true);

			// 最大放大倍数
			MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(15);

			map.setMapStatus(u);

			// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
			BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_gcoding);
			MyLocationConfiguration config = new MyLocationConfiguration(
					LocationMode.FOLLOWING, true, mCurrentMarker);
			// map.setMyLocationConfiguration();
			// 当不需要定位图层时关闭定位图层
			map.setMyLocationEnabled(true);
			map.setMyLocationConfigeration(config);

			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				// sb.append(location.getOperators());
			}
			sb.append(",location.mAddr:" + location.mAddr + "="
					+ location.mServerString);

			Log.i("BaiduLocationApiDem",
					sb.toString() + ",,,to:" + location.toJsonString() + ",,,"
							+ location.getCity());

			GeoCoder coder = GeoCoder.newInstance();

			ReverseGeoCodeOption reverseCode = new ReverseGeoCodeOption();
			ReverseGeoCodeOption result = reverseCode.location(new LatLng(
					location.getLatitude(), location.getLongitude()));
			coder.reverseGeoCode(result);
			coder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

				@Override
				public void onGetReverseGeoCodeResult(
						ReverseGeoCodeResult result) {
					Log.i("===",
							"onGetReverseGeoCodeResult:" + result.getAddress());
					Toast.makeText(getActivity(),
							"详细地址：" + result.getAddress(), Toast.LENGTH_SHORT)
							.show();
					baiduClient.stop();
				}

				@Override
				public void onGetGeoCodeResult(GeoCodeResult result) {
					Log.i("===", "onGetGeoCodeResult:" + result.getAddress());

				}
			});

			if (getActivity() != null) {
				Toast.makeText(getActivity(), "定位到了：" + sb.toString(),
						Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onReceivePoi(BDLocation arg0) {

		}
	}
}
