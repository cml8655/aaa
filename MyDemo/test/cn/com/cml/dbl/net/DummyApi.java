package cn.com.cml.dbl.net;

import cn.com.cml.dbl.model.BaseModel;
import cn.com.cml.dbl.model.LocationModel;

public class DummyApi {

	public static LocationModel mobileLocation(double latitude, double longitude) {

		LocationModel model = new LocationModel();

		model.setState(BaseModel.State.OK);
		model.setGetTime(String.valueOf(System.currentTimeMillis()));
		model.setImei("ddd");
		model.setLatitude(latitude);
		model.setLongitude(longitude);
		model.setRadius(23);

		return model;
	}

}
