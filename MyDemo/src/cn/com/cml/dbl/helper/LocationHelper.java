package cn.com.cml.dbl.helper;

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

	private BDLocation userLocation;
	private BDLocation mobileLocation;

	private String userLocationCache;
	private String mobileLocationCache;

	private BaseFragment baseFragment;

	public LocationHelper(BaseFragment baseFragment) {
		super();
		this.baseFragment = baseFragment;
	}

	private OnGetGeoCoderResultListener userlocationReverse = new OnGetGeoCoderResultListener() {

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

			Activity ac = baseFragment.getActivity();

			if (null == ac || ac.isFinishing()) {
				return;
			}

			if (result.error != SearchResult.ERRORNO.NO_ERROR) {
				baseFragment.showNiftyTip(
						ac.getString(R.string.monitor_location_error),
						R.id.mobile_monitor_tip_container);
				return;
			}

			if (null != result) {
				String address = result.getAddress();
				int radius = (int) userLocation.getRadius();
				baseFragment.showNiftyTip(ac.getString(
						R.string.monitor_location_result, address, radius),
						R.id.mobile_monitor_tip_container);
			}

		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
		}
	};

	private OnGetGeoCoderResultListener mobileLocationReverse = new OnGetGeoCoderResultListener() {

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

			Activity ac = baseFragment.getActivity();

			if (null == ac || ac.isFinishing()) {
				return;
			}

			if (result.error != SearchResult.ERRORNO.NO_ERROR) {
				baseFragment.showNiftyTip(
						ac.getString(R.string.monitor_location_error),
						R.id.mobile_monitor_tip_container);
				return;
			}

			if (null != result) {

				String address = result.getAddress();
				int radius = (int) mobileLocation.getRadius();
				baseFragment.showNiftyTip(ac.getString(
						R.string.monitor_location_result, address, radius),
						R.id.mobile_monitor_tip_container);
			}

		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {

		}
	};

	public boolean reverseUserLocationCoder() {

		if (null == userLocation) {
			return false;
		}

		ReverseGeoCodeOption option = new ReverseGeoCodeOption();
		option.location(new LatLng(userLocation.getLatitude(), userLocation
				.getLongitude()));

		GeoCoder coder = GeoCoder.newInstance();
		coder.setOnGetGeoCodeResultListener(userlocationReverse);

		return coder.reverseGeoCode(option);
	}

	public boolean reverseMobileLocationCoder() {

		if (null == mobileLocation) {
			return false;
		}

		ReverseGeoCodeOption option = new ReverseGeoCodeOption();
		option.location(new LatLng(mobileLocation.getLatitude(), mobileLocation
				.getLongitude()));

		GeoCoder coder = GeoCoder.newInstance();
		coder.setOnGetGeoCodeResultListener(mobileLocationReverse);

		return coder.reverseGeoCode(option);
	}

	public void setUserLocation(BDLocation userLocation) {
		this.userLocation = userLocation;
	}

	public void setMobileLocation(BDLocation mobileLocation) {
		this.mobileLocation = mobileLocation;
	}

	public String getMobileLocationCache() {
		return mobileLocationCache;
	}

	public void setMobileLocationCache(String mobileLocationCache) {
		this.mobileLocationCache = mobileLocationCache;
	}

	public OnGetGeoCoderResultListener getMobileLocationReverse() {
		return mobileLocationReverse;
	}

	public void setMobileLocationReverse(
			OnGetGeoCoderResultListener mobileLocationReverse) {
		this.mobileLocationReverse = mobileLocationReverse;
	}

	public BDLocation getUserLocation() {
		return userLocation;
	}

	public BDLocation getMobileLocation() {
		return mobileLocation;
	}

}
