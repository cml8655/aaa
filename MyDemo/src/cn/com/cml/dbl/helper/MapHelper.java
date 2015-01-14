package cn.com.cml.dbl.helper;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

public abstract class MapHelper {

	public interface LocationStatusListener {
		void onLocationReceive(boolean isValid,BDLocation location);
	}

	protected LocationStatusListener locationStatusListener;

	protected SupportMapFragment mapFragment;

	public MapHelper(SupportMapFragment mapFragment) {
		this.mapFragment = mapFragment;
	}

	/**
	 * 初始化地图信息
	 */
	public abstract void initMap(int scanInterval);

	public abstract void startLocationMonitor();

	public abstract void stopLocationMonitor();

	public abstract void clear();

	public abstract void addMarker(LatLng lat);

	public abstract void addWindowInfo(LatLng lat);

	public abstract void animateTo(LatLng lat);
	
	public abstract BDLocation getLastLocation();

	public abstract void drawLine(LatLng start, LatLng end);
	
	public void setLocationStatusListener(
			LocationStatusListener locationStatusListener) {
		this.locationStatusListener = locationStatusListener;
	}

}
