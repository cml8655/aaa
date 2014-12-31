package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import cn.com.cml.dbl.LoginActivity_;
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;

@EFragment(R.layout.fragment_userinfo)
public class UserInfoFragment extends Fragment implements OnItemClickListener {

	@ViewById(R.id.userinfo_account)
	TextView accountView;

	@ViewById(R.id.userinfo_point)
	TextView pointView;

	@ViewById(R.id.userinfo_device)
	TextView deviceView;

	@AfterViews
	public void afterView() {

		accountView.setText(getString(R.string.userinfo_account, 1));
		pointView.setText(getString(R.string.userinfo_point, 2));
		deviceView.setText(getString(R.string.userinfo_unbind_device));
	}

	@Click(R.id.userinfo_logout)
	protected void onLogoutClicked() {
		DialogUtil.defaultDialog(R.string.exit_confirm, 1, this).show(
				getFragmentManager(), "logout");
	}

	@Override
	public void onClick(DialogInterface dialog, long id, int requestId) {
		if (id == DialogInterface.BUTTON_POSITIVE) {
			LoginActivity_.intent(this).start();
			getActivity().finish();
		}
	}
}
