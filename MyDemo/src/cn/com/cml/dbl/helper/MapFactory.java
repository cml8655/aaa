package cn.com.cml.dbl.helper;

import android.content.Context;

import com.baidu.mapapi.map.MapView;

public class MapFactory {
	public MapHelper createBaiduMapHelper(MapView mapview, Context context) {
		return new BaiduMapHelper(mapview, context);
	}
}
