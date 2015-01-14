package cn.com.cml.dbl.helper;

import com.baidu.mapapi.map.SupportMapFragment;

public class MapFactory {
	public MapHelper createBaiduMapHelper(SupportMapFragment mapFragment) {
		return new BaiduMapHelper(mapFragment);
	}
}
