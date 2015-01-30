package cn.com.cml.dbl.helper;

import android.content.Context;

import com.baidu.mapapi.map.SupportMapFragment;

public class MapFactory {
	public MapHelper createBaiduMapHelper(SupportMapFragment mapFragment,Context context) {
		return new BaiduMapHelper(mapFragment,context);
	}
}
