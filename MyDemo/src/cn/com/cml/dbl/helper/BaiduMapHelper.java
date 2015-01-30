package cn.com.cml.dbl.helper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.ui.MapviewTipView;
import cn.com.cml.dbl.ui.MapviewTipView_;
import cn.com.cml.dbl.util.DialogUtil;

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
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

public class BaiduMapHelper extends MapHelper implements BDLocationListener {

	private boolean isFirstLocate;// 第一次定位
	private LocationClient baiduClient;
	private BaiduMap baiduMap;
	private BDLocation lastLocation;
	private Context context;

	public BaiduMapHelper(SupportMapFragment mapFragment, Context context) {
		super(mapFragment);
		this.context = context;
	}

	@Override
	public void initMap(int scanInterval) {
		// 定位功能初始化
		baiduClient = new LocationClient(context);

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
	public void addWindowInfo(LatLng lat, int icon, int text) {

		// 设置提示
		MapviewTipView tip = MapviewTipView_.build(context);

		tip.bind(icon, text);

		// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
		InfoWindow mInfoWindow = new InfoWindow(tip, lat, -47);

		// 显示InfoWindow
		baiduMap.showInfoWindow(mInfoWindow);
	}

	@Override
	public void drawLine(LatLng start, LatLng end) {

		// 创建两点的连线
		List<LatLng> pts = new ArrayList<LatLng>();

		pts.add(start);
		pts.add(end);

		// 构建用户绘制多边形的Option对象
		OverlayOptions polygonOption = new PolylineOptions().points(pts).color(
				Color.BLUE);

		// 在地图上添加多边形Option，用于显示
		baiduMap.addOverlay(polygonOption);

	}

	@Override
	public void onReceiveLocation(BDLocation location) {

		boolean isValid = checkLocationResult(location);

		if (null != this.locationStatusListener) {
			locationStatusListener.onLocationReceive(isValid, location);
		}

		if (!isValid) {
			DialogUtil.showTip(context, "定位失败");
			return;
		}

		lastLocation = location;

		if (!isFirstLocate) {
			isFirstLocate = true;

			MyLocationData myData = new MyLocationData.Builder()
					.accuracy(location.getRadius()).direction(0)
					.latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();

			baiduMap.setMyLocationData(myData);
		}

	}

	@Override
	public void onReceivePoi(BDLocation location) {

	}

	private boolean checkLocationResult(BDLocation location) {

		switch (location.getLocType()) {
		case BDLocation.TypeGpsLocation:// 61 ： GPS定位结果
		case BDLocation.TypeNetWorkLocation:// 表示网络定位结果
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

	@Override
	public void requestLocation() {
		baiduClient.requestLocation();
	}

}
