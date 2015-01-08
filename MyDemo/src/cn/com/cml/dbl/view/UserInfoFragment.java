package cn.com.cml.dbl.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.listener.BaseFindListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;

@EFragment(R.layout.fragment_userinfo)
public class UserInfoFragment extends Fragment implements OnItemClickListener {

	private static final String INIT_CHARACTOR = "--";
	public static final String ACTION_USERINFO_CHANGE = "cn.com.cml.dbl.view.UserInfoFragment.ACTION_USERINFO_CHANGE";
	public static final String EXTRA_SCORE = "cn.com.cml.dbl.view.UserInfoFragment.EXTRA_SCORE";

	private static final int REQUEST_REMOTE_PASS = 1001;

	private DialogFragment loadingDialog;

	@ViewById(R.id.userinfo_account)
	TextView accountView;

	@ViewById(R.id.userinfo_point)
	TextView pointView;

	@ViewById(R.id.userinfo_device)
	TextView deviceView;

	@Bean
	ApiRequestServiceClient apiClient;

	private int userScore;

	private BroadcastReceiver userInfoReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();

			if (ACTION_USERINFO_CHANGE.equals(action)) {

				if (getActivity() != null) {

					int changeScore = intent.getIntExtra(EXTRA_SCORE, 0);

					userScore += changeScore;

					pointView.setText(getString(R.string.userinfo_point,
							userScore));
				}
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActivity().registerReceiver(userInfoReceiver,
				new IntentFilter(ACTION_USERINFO_CHANGE));
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(userInfoReceiver);
		super.onDestroy();
	}

	@AfterViews
	public void afterView() {

		loadingDialog = DialogUtil.dataLoadingDialog();
		loadingDialog.show(getFragmentManager(), "user_info");

		// 用户信息加载
		User user = BmobUser.getCurrentUser(getActivity(), User.class);

		userScore = user.getScore();

		accountView.setText(getString(R.string.userinfo_account,
				user.getUsername()));
		pointView.setText(getString(R.string.userinfo_point, user.getScore()));

		deviceView.setText(getString(R.string.userinfo_bind_device,
				INIT_CHARACTOR));

		apiClient.bindDeviceQuery(new BaseFindListener<MobileBind>(
				getActivity()) {
			@Override
			public void onFinish() {
				super.onFinish();
				loadingDialog.dismiss();
			}

			@Override
			public void onSuccess(List<MobileBind> result) {

				if (result.size() > 0) {
					String deviceName = result.get(0).getDeviceName();
					setBindDevice(deviceName);
				}

			}
		});

	}

	@UiThread
	protected void setBindDevice(String device) {
		deviceView.setText(getString(R.string.userinfo_bind_device, device));
	}

	@Click(R.id.userinfo_remote_pass_query)
	public void onRemotePassQueryClicked() {
		DialogFragment confirmDialog = DialogUtil.defaultDialog(
				R.string.userinfo_query_confirm, REQUEST_REMOTE_PASS, this);
		confirmDialog.show(getFragmentManager(), "remote_pass_query");
	}

	@Click(R.id.userinfo_secure)
	public void onSecureClicked() {
		MainActivity ac = (MainActivity) getActivity();
		ac.changeContent(MenuFragment.MenuItems.SECURE.getId());
	}

	@Override
	public void onClick(DialogInterface dialog, long id, int requestId) {

		if (id == DialogInterface.BUTTON_POSITIVE
				&& requestId == REQUEST_REMOTE_PASS) {

			loadingDialog.show(getFragmentManager(), "remote_pass_query");
			loadingDialog.setCancelable(false);

			// 积分不足
			if (userScore < Constant.Gloable.REMOTE_PASS_QUERY_SCORE) {
				DialogUtil.toast(getActivity(), R.string.userinfo_score_less);
				loadingDialog.dismiss();
				return;
			}

			// 绑定手机查询
			apiClient.bindDeviceQuery(new BaseFindListener<MobileBind>(
					getActivity()) {

				@Override
				public void onFinish() {
					super.onFinish();
					loadingDialog.dismiss();
				}

				@Override
				public void onSuccess(List<MobileBind> result) {

					if (result.size() == 0) {
						DialogUtil.toast(getActivity(),
								R.string.userinfo_device_unbind);
						return;
					}

					MobileBind bindDevice = result.get(0);

					String pass = bindDevice.getBindPassword();

					DialogFragment remotePassDialog = DialogUtil.defaultDialog(
							getString(R.string.userinfo_query_result, pass), 1,
							UserInfoFragment.this);
					remotePassDialog.setCancelable(false);
					remotePassDialog.show(getFragmentManager(),
							"remote_pass_result");

					// 扣除用户积分
					User user = BmobUser.getCurrentUser(getActivity(),
							User.class);

					// 扣除查询的积分
					user.increment("score",
							-Constant.Gloable.REMOTE_PASS_QUERY_SCORE);
					user.update(getActivity(), new UpdateListener() {

						@Override
						public void onSuccess() {

							Intent intent = new Intent(ACTION_USERINFO_CHANGE);
							intent.putExtra(EXTRA_SCORE,
									-Constant.Gloable.REMOTE_PASS_QUERY_SCORE);
							getActivity().sendBroadcast(intent);
						}

						@Override
						public void onFailure(int errCode, String errMsg) {

						}
					});
				}
			});

		}
	}
}
