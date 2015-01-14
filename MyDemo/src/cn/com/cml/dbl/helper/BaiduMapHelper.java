package cn.com.cml.dbl.helper;

import android.app.Activity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DialogUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

public class BaiduMapHelper extends MapHelper implements BDLocationListener {

	private boolean isFirstLocate;// 第一次定位
	private LocationClient baiduClient;
	private BaiduMap baiduMap;
	private BDLocation lastLocation;

	public BaiduMapHelper(SupportMapFragment mapFragment) {
		super(mapFragment);
	}

	@Override
	public void initMap(int scanInterval) {
		// 定位功能初始化
		baiduClient = new LocationClient(mapFragment.getActivity());

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(scanInterval);// 设置发起定位请求的间隔时间为scanIntervalms
		option.setOpenGps(true);
		option.setTimeOut(20000);// 20s延迟
		option.setPriority(LocationClientOption.GpsFirst);

		baiduClient.setLocOption(option);
		baiduClient.registerLocationListener(this);

		baiduMap = mapFragment.getBaiduMap();

		// 百度地图初始化
		baiduMap = mapFragment.getBaiduMap();
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		baiduMap.setTrafficEnabled(true);
		baiduMap.setMyLocationEnabled(true);
		baiduMap.setBuildingsEnabled(true);

		// 放大地图
		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
		baiduMap.setMapStatus(u);

		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
		MyLocationConfiguration config = new MyLocationConfiguration(
				LocationMode.FOLLOWING, true, null);

		baiduMap.setMyLocationConfigeration(config);
	}

	@Override
	public void startLocationMonitor() {
		if (null == baiduClient) {
			throw new IllegalStateException(
					"mapclient is null,did you forget call initMap method ");
		}
		if (!baiduClient.isStarted()) {
			baiduClient.start();
		}
	}

	@Override
	public void stopLocationMonitor() {
		if (null == baiduClient) {
			throw new IllegalStateException(
					"mapclient is null,did you forget call initMap method ");
		}
		if (baiduClient.isStarted()) {
			baiduClient.stop();
		}

	}

	@Override
	public void clear() {
		baiduMap.clear();
	}

	@Override
	public void addMarker(LatLng lat) {

		BitmapDescriptor icon = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_marka);

		baiduMap.addOverlay(new MarkerOptions().position(lat).icon(icon));

	}

	@Override
	public void addWindowInfo(LatLng lat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(LatLng start, LatLng end) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveLocation(BDLocation location) {

		Activity ac = mapFragment.getActivity();

		if (ac == null) {
			return;
		}

		boolean isValid = checkLocationResult(location);

		if (null != this.locationStatusListener) {
			locationStatusListener.onLocationReceive(isValid, location);
		}

		if (!isValid) {
			DialogUtil.showTip(ac, "定位失败");
			return;
		}

		lastLocation = location;

		if (!isFirstLocate) {
			isFirstLocate = true;
			
			LatLng mobileLocation = new LatLng(31.245951, 121.51377);

			this.addMarker(new LatLng(location.getLatitude(), location
					.getLongitude()));
			this.addMarker(mobileLocation);
			baiduMap.animateMapStatus(MapStatusUpdateFactory
					.newLatLng(new LatLng(location.getLatitude(), location
							.getLongitude())));
		}

		// MyLocationData myData = new MyLocationData.Builder()
		// .accuracy(location.getRadius()).direction(100)
		// .latitude(location.getLatitude())
		// .longitude(location.getLongitude()).build();
		//
		// map.setMyLocationData(myData);

	}

	@Override
	public void onReceivePoi(BDLocation location) {

	}

	private boolean checkLocationResult(BDLocation location) {

		switch (location.getLocType()) {
		case 61:// 61 ： GPS定位结果
		case 161:// 表示网络定位结果
			return true;
		}

		return false;
	}

	@Override
	public void animateTo(LatLng lat) {
		baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(lat));
	}

	@Override
	public BDLocation getLastLocation() {
		return lastLocation;
	}

}
