package cn.com.cml.dbl.view;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.SupportMapFragment;

public class MobileMonitorFragment extends SupportMapFragment {

	public static final MobileMonitorFragment newInstance() {

		return new MobileMonitorFragment();
	}


	@Override
	public void onViewCreated(View v, Bundle data) {
		super.onViewCreated(v, data);
		getBaiduMap().getUiSettings();
	}
}
