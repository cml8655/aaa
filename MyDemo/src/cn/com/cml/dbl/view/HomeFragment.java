package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;
import cn.com.cml.dbl.view.MenuFragment.MenuItems;

@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements OnItemClickListener {

	private static final int REQUEST_MONITER_SELECT = 100;
	private static final int MONITOR_MAP = 0;
	private static final int MONITOR_ALARM = 1;

	private DialogFragment monitorSelectDialog;

	@ViewById(R.id.home_mobile_monitor)
	Button monitorButton;

	@AfterViews
	void afterViews() {

		MainActivity ac = (MainActivity) getActivity();
		if (ac.isBindDevice) {
			monitorButton.setVisibility(View.GONE);
		}

	}

	@Click(R.id.home_mobile_monitor)
	public void mobileMonitorClicked() {

		if (null == monitorSelectDialog) {
			monitorSelectDialog = DialogUtil.defaultSingleSelectDialog(
					R.array.monitor_select_list, null, REQUEST_MONITER_SELECT,
					this);
		}
		monitorSelectDialog.show(getFragmentManager(), "monitor_select");
	}

	@Override
	public void onClick(DialogInterface nullDialog, long id, int requestId) {

		monitorSelectDialog.dismiss();

		if (requestId != REQUEST_MONITER_SELECT) {
			return;
		}

		// 地图定位
		if (id == MONITOR_MAP) {
			changeContent(MenuItems.MAP.getId());
		} else if (id == MONITOR_ALARM) {
			changeContent(MenuItems.ALARM.getId());
		}
	}

	private void changeContent(int menuId) {
		MainActivity ac = (MainActivity) getActivity();
		ac.changeContent(menuId);
	}
}
