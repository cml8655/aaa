package cn.com.cml.dbl.helper;

import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.model.LatLng;

public abstract class MapService {

	/**
	 * 初始化地图以及监听
	 * 
	 * @param listener
	 */
	public abstract void initMap(BDLocationListener listener);

	public abstract void startLocationMonitor();

	public abstract void stopLocationMonitor();

	public abstract void clear();

	public abstract void addMarker(LatLng lat);

	public abstract void addWindowInfo(LatLng lat);

	public abstract void drawLine(LatLng start, LatLng end);

	public abstract void onResume();

	public abstract void onStop();

	public abstract void onDestory();

	public abstract void onStart();

	public abstract void onReStart();

}
