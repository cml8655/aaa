package cn.com.cml.dbl.helper;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.view.BaseFragment;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

public class LocationHelper {

	private LocationHandler locationHandler = new LocationHandler();

	private static final String KEY_USER = "key_user";
	private static final String KEY_MOBILE = "key_mobile";

	private BaseFragment baseFragment;

	private LocationHandler.LocationHandlerModel userLocationModel;
	private LocationHandler.LocationHandlerModel mobileLocationModel;

	public LocationHelper(BaseFragment baseFragment) {

		this.baseFragment = baseFragment;

		userLocationModel = initLocationModel(R.string.icon_user);
		locationHandler.storeModel(KEY_USER, userLocationModel);

		mobileLocationModel = initLocationModel(R.string.icon_mobile);
		locationHandler.storeModel(KEY_MOBILE, mobileLocationModel);
	}

	private LocationHandler.LocationHandlerModel initLocationModel(
			final int iconRes) {

		final LocationHandler.LocationHandlerModel model = new LocationHandler.LocationHandlerModel();
		model.cacheLocation = new LocationStorage();
		model.listener = new LocationReverseListener(baseFragment) {

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

				if (result.error != SearchResult.ERRORNO.NO_ERROR) {
					super.showError(iconRes);
				}

				if (null != result) {

					String address = result.getAddress();
					int radius = (int) model.location.getRadius();

					super.showAddressResult(address, radius);

					model.cacheLocation.cacheAddress(address);
					model.cacheLocation.setmLocation(model.location);
				}

			}

		};
		return model;
	}

	private boolean reverseLocationCoder(String key) {

		LocationHandler.LocationHandlerModel model = locationHandler
				.retrieveModel(key);

		BDLocation location = model.location;

		if (null == location) {
			return false;
		}

		// 位置移动了，重新定位
		if (model.cacheLocation.isMove(location)) {

			ReverseGeoCodeOption option = new ReverseGeoCodeOption();
			option.location(new LatLng(location.getLatitude(), location
					.getLongitude()));

			GeoCoder coder = GeoCoder.newInstance();
			coder.setOnGetGeoCodeResultListener(model.listener);

			return coder.reverseGeoCode(option);
		}

		// 位置没有移动，不进行重新定位
		LocationStorage cacheLocation = model.cacheLocation;

		model.listener.showAddressResult(cacheLocation.cacheAddress,
				(int) model.location.getRadius());

		return true;

	}

	public boolean reverseUserLocationCoder() {
		return reverseLocationCoder(KEY_USER);
	}

	public boolean reverseMobileLocationCoder() {
		return reverseLocationCoder(KEY_MOBILE);
	}

	public void setUserLocation(BDLocation userLocation) {
		userLocationModel.location = userLocation;
	}

	public void setMobileLocation(BDLocation mobileLocation) {
		mobileLocationModel.location = mobileLocation;
	}

	public BDLocation getUserLocation() {
		return userLocationModel.location;
	}

	public BDLocation getMobileLocation() {
		return mobileLocationModel.location;
	}

	private class LocationStorage {

		private BDLocation mLocation;
		private String cacheAddress;

		public boolean isMove(BDLocation location) {

			if (mLocation == null) {
				return true;
			}

			boolean equals = (mLocation.getLatitude() == location.getLatitude())
					&& (location.getLongitude() == mLocation.getLongitude());

			return !equals;
		}

		public void cacheAddress(String address) {
			this.cacheAddress = address;
		}

		public void setmLocation(BDLocation mLocation) {
			this.mLocation = mLocation;
		}

	}

	private static class LocationHandler {

		public static class LocationHandlerModel {
			public BDLocation location;
			public LocationReverseListener listener;
			public LocationStorage cacheLocation;
		}

		private Map<String, LocationHandlerModel> map = new HashMap<String, LocationHandlerModel>();

		public void storeModel(String key, LocationHandlerModel model) {
			map.put(key, model);
		}

		public LocationHandlerModel retrieveModel(String key) {
			return map.get(key);
		}
	}

	private abstract class LocationReverseListener implements
			OnGetGeoCoderResultListener {

		private BaseFragment baseFragment;

		public LocationReverseListener(BaseFragment baseFragment) {
			this.baseFragment = baseFragment;
		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {

		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

		}

		public void showAddressResult(String address, int radius) {

			Activity ac = baseFragment.getActivity();

			if (null == ac || ac.isFinishing()) {
				return;
			}

			baseFragment.showNiftyTip(ac.getString(
					R.string.monitor_location_result, address, radius));
		}

		public void showError(int icon) {

			Activity ac = baseFragment.getActivity();

			if (null == ac || ac.isFinishing()) {
				return;
			}

			baseFragment.showNiftyTip(
					ac.getString(R.string.monitor_location_error), icon,
					R.id.mobile_monitor_tip_container);

		}
	}
}
