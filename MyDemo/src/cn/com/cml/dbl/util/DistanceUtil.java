package cn.com.cml.dbl.util;

import cn.com.cml.dbl.model.LocationModel;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;

public class DistanceUtil {
	public static int calculateDistance(BDLocation location,
			LocationModel userLocation) {

		LatLng mobileLat = new LatLng(location.getLatitude(),
				location.getLongitude());
		LatLng userLat = new LatLng(userLocation.getLatitude(),
				userLocation.getLongitude());

		return (int) com.baidu.mapapi.utils.DistanceUtil.getDistance(mobileLat,
				userLat);
	}
}
