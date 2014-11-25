package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;

import android.support.v4.app.Fragment;
import android.widget.Toast;
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.PetApplication;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.model.LocationModel;
import cn.com.cml.dbl.ui.MobileMonitorToolsView_;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;

@EFragment(R.layout.fragment_mobile_monitor)
public class MobileMonitorFragment extends Fragment implements
		BDLocationListener {

	@FragmentById(R.id.map_fragment)
	SupportMapFragment mapFragment;

	private LocationClient baiduClient;

	private BaiduMap map;
	private MapView mapView;

	@AfterViews
	public void initConfig() {
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

	}

	@Override
	public void onResume() {

		if (null != baiduClient && !baiduClient.isStarted()) {
			baiduClient.start();
		}
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
			} else {// 重新显示到最前端中
				baiduClient.start();
			}
		}
	}

	@Override
	public void onReceiveLocation(BDLocation location) {

		// 将数据保存到后台服务器
		LocationModel model = new LocationModel();

		model.setLatitude(location.getLatitude());
		model.setLongitude(location.getLongitude());
		model.setImei(PetApplication.deviceId);
		model.setRadius(location.getRadius());
		model.setLocType(location.getLocType());

		model.save(this.getActivity());

		Toast.makeText(
				getActivity(),
				"定位到了：" + location.getLatitude() + ","
						+ location.getLongitude(), Toast.LENGTH_LONG).show();

		MyLocationData myData = new MyLocationData.Builder()
				.accuracy(location.getRadius()).direction(100)
				.latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();

		map.setMyLocationData(myData);
	}

	@Override
	public void onReceivePoi(BDLocation location) {

	}

}
