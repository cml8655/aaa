package cn.com.cml.dbl.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.PushListener;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant.Command;
import cn.com.cml.dbl.helper.LocationHelper;
import cn.com.cml.dbl.helper.MapFactory;
import cn.com.cml.dbl.helper.MapHelper;
import cn.com.cml.dbl.helper.MapHelper.LocationStatusListener;
import cn.com.cml.dbl.listener.BaseFindListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.model.LocationModel;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.util.PrefUtil_;
import cn.com.cml.dbl.util.ValidationUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;
import cn.com.cml.dbl.view.RemotePassInputDialogFragment.OnPassFinishListener;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;

@EFragment(R.layout.fragment_mobile_monitor)
public class MobileMonitorFragment extends BaseFragment implements
		OnItemClickListener, LocationStatusListener, OnPassFinishListener {

	private static final String TAG = "MobileMonitorFragment";

	public static final String ACTION_LOCATION_RESULT = "cn.com.cml.dbl.view.MobileMonitorFragment.ACTION_LOCATION_RESULT";
	public static final String EXTRA_LOCATION_RESULT = "MobileMonitorFragment.EXTRA_LOCATION_RESULT";

	private static final int LOCATE_INTERVAL = 10000;
	private static final int REQUEST_REMOTE_PASS = 3001;

	@FragmentById(R.id.map_fragment)
	SupportMapFragment mapFragment;

	@Bean
	ApiRequestServiceClient apiClient;

	@Pref
	PrefUtil_ prefUtil;

	private MapHelper mapHelper;
	private LocationHelper locationHelper;

	private DialogFragment loadingDialog;
	private boolean remotePassValid;

	// 手机定位返回数据
	private BroadcastReceiver mobileLocationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (!intent.hasExtra(EXTRA_LOCATION_RESULT)) {
				return;
			}

			String locationStr = intent.getStringExtra(EXTRA_LOCATION_RESULT);

			LocationModel model = new Gson().fromJson(locationStr,
					LocationModel.class);

			BDLocation bdLocation = new BDLocation();

			bdLocation.setLatitude(model.getLatitude());
			bdLocation.setLongitude(model.getLongitude());
			bdLocation.setLocType(model.getLocType());
			bdLocation.setRadius(model.getRadius());

			locationHelper.setMobileLocation(bdLocation);
			mapHelper.addMarker(new LatLng(model.getLatitude(), model
					.getLongitude()));
			mapHelper.animateTo(new LatLng(model.getLatitude(), model
					.getLongitude()));
		}
	};

	@AfterViews
	public void initConfig() {

		Log.d("MenuHelper", "mapFragment:" + mapFragment);

		mapHelper = new MapFactory().createBaiduMapHelper(mapFragment,
				this.getActivity());

		mapHelper.initMap(LOCATE_INTERVAL);// 10s定位一次
		mapHelper.setLocationStatusListener(this);

		locationHelper = new LocationHelper(this);

		loadingDialog = DialogUtil.dataLoadingDialog(R.string.locate_user);
		loadingDialog.show(getFragmentManager(), "location");

		// 输入远程密码
		// showRemotePassRequiredDialog();

	}

	private void showRemotePassRequiredDialog() {
		// 远程密码输入
		DialogUtil.remotePassInputDialog(R.string.alarm_cancel,
				REQUEST_REMOTE_PASS, this).show(getFragmentManager(),
				"remote_pass_required");

	}

	@Click(R.id.map_menu_setting)
	public void settingClick(View v) {

		BDLocation userLocation = locationHelper.getUserLocation();
		BDLocation mobileLocation = locationHelper.getMobileLocation();

		if (null != userLocation && null != mobileLocation) {
			LatLng start = new LatLng(userLocation.getLatitude(),
					userLocation.getLongitude());
			LatLng end = new LatLng(mobileLocation.getLatitude(),
					mobileLocation.getLongitude());
			mapHelper.drawLine(start, end);
			int distance = (int) DistanceUtil.getDistance(start, end);
			String distanceStr = getActivity().getString(
					R.string.monitor_distance, distance);
			showNiftyTip(distanceStr, R.string.icon_user,
					R.id.mobile_monitor_tip_container);
		}
	}

	@Click(R.id.map_menu_user)
	public void userClick(View v) {

		BDLocation userLocation = locationHelper.getUserLocation();

		if (null != userLocation) {

			LatLng lat = new LatLng(userLocation.getLatitude(),
					userLocation.getLongitude());

			mapHelper.animateTo(lat);
			mapHelper.addWindowInfo(lat, R.string.icon_spin5,
					R.string.monitor_my_location);
			locationHelper.reverseUserLocationCoder();
		} else {
			mapHelper.requestLocation();
			loadingDialog.show(getFragmentManager(), "location");
		}
	}

	@Click(R.id.map_menu_mobile)
	public void mobileClick(View v) {

		if (prefUtil.isBindDevice().get()) {
			DialogUtil.tipDialog(R.string.icon_user,
					R.string.bind_current_device).show(getFragmentManager(),
					"current_device");
			return;
		}

		if (!remotePassValid) {
			this.showRemotePassRequiredDialog();
			return;
		}

		// TODO 去除
		// locationHelper.setMobileLocation(new BDLocation(121.51377, 31.245951,
		// 11));
		BDLocation mobileLocation = locationHelper.getMobileLocation();

		if (null != mobileLocation) {

			LatLng lat = new LatLng(mobileLocation.getLatitude(),
					mobileLocation.getLongitude());

			mapHelper.animateTo(lat);
			mapHelper.addWindowInfo(lat, R.string.icon_spin5,
					R.string.monitor_mobile_location);
			locationHelper.reverseMobileLocationCoder();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		IntentFilter filter = new IntentFilter(ACTION_LOCATION_RESULT);
		getActivity().registerReceiver(mobileLocationReceiver, filter);
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mobileLocationReceiver);

		getFragmentManager().beginTransaction().remove(mapFragment).commit();

		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		mapHelper.startLocationMonitor();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapHelper.stopLocationMonitor();
	}

	/**
	 * 当调用show/hide时，fragment生命周期不继续走动了
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (hidden) {// 不在最前端界面显示
			mapHelper.stopLocationMonitor();
		} else {// 重新显示到最前端中
			mapHelper.startLocationMonitor();
		}
	}

	private void sendLocationCommand(final String pass) {

		loadingDialog = DialogUtil
				.dataLoadingDialog(R.string.monitor_pass_check);
		loadingDialog.show(getFragmentManager(), "remote_pass_check");

		// 查询用户手机绑定情况
		apiClient.bindDeviceQuery(new BaseFindListener<MobileBind>(
				getActivity()) {

			@Override
			public void onFinish() {
				loadingDialog.dismissAllowingStateLoss();
			}

			@Override
			public void onSuccess(List<MobileBind> result) {

				// TODO 没有绑定手机，跳到绑定界面
				if (result.size() == 0) {
					DialogUtil.showTip(getActivity(), "没有找到绑定手机");
					return;
				}

				MobileBind bindDevice = result.get(0);

				// TODO 提示用户找回密码，密码错误
				if (!ValidationUtil.equals(pass, bindDevice.getBindPassword())) {

					DialogUtil.showTip(getActivity(),
							getString(R.string.password_error));

					return;
				}

				// 启动警报
				Command locationCommand = Command.DINGWEI_ENUM;
				locationCommand.setBindPass(pass);
				locationCommand.setFrom(BmobUser.getCurrentUser(getActivity())
						.getUsername());

				apiClient.sendPushCommand(locationCommand,
						bindDevice.getImei(), new PushListener() {

							@Override
							public void onSuccess() {
								remotePassValid = true;
								DialogUtil.showTip(getActivity(), "推送发送成功！！");
							}

							@Override
							public void onFailure(int arg0, String errorMsg) {
								Log.d(TAG, "发送失败：" + errorMsg);
								DialogUtil.showTip(getActivity(), "推送发送失败！！");
							}
						});
			}
		});
	}

	@Override
	public void onClick(DialogInterface dialog, long id, int requestId) {

	}

	@Override
	public void onLocationReceive(boolean isValid, BDLocation location) {
		if (isValid) {
			locationHelper.setUserLocation(location);
		}
		if (!loadingDialog.isHidden()) {
			loadingDialog.dismiss();
		}

	}

	@Override
	public void onClick(DialogInterface dialog, long id, int requestId,
			String pass) {

		if (id == DialogInterface.BUTTON_POSITIVE) {
			if (TextUtils.isEmpty(pass)) {
				showRemotePassRequiredDialog();
				DialogUtil.toast(getActivity(),
						R.string.monitor_remote_pass_empty);
			} else {
				sendLocationCommand(pass);
			}
		}
	}

}
