package cn.com.cml.dbl.view;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.com.cml.dbl.LoginActivity_;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.listener.BaseFindListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;

@EFragment(R.layout.fragment_userinfo)
public class UserInfoFragment extends Fragment implements OnItemClickListener {

	private static final String INIT_CHARACTOR = "--";

	private DialogFragment dialog;

	@ViewById(R.id.userinfo_account)
	TextView accountView;

	@ViewById(R.id.userinfo_point)
	TextView pointView;

	@ViewById(R.id.userinfo_device)
	TextView deviceView;

	@Bean
	ApiRequestServiceClient apiClient;

	@AfterViews
	public void afterView() {

		dialog = DialogUtil.dataLoadingDialog();
		dialog.show(getFragmentManager(), "user_info");

		User user = BmobUser.getCurrentUser(getActivity(), User.class);

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
				dialog.dismiss();
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

	@Override
	public void onClick(DialogInterface dialog, long id, int requestId) {
		if (id == DialogInterface.BUTTON_POSITIVE) {
			LoginActivity_.intent(this).start();
			getActivity().finish();
		}
	}
}
