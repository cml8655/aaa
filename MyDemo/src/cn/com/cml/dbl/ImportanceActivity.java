package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import cn.com.cml.dbl.helper.MenuItems;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;

/**
 * 通知信息
 * 
 * @author Administrator
 * 
 */
@EActivity(R.layout.activity_importance)
public class ImportanceActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private static final int REQUEST_MONITER_SELECT = 20;

	private static final int MONITOR_MAP = 0;
	private static final int MONITOR_ALARM = 1;

	@ViewById(R.id.btn_bind_device)
	Button bindDeviceBtn;

	private DialogFragment monitorSelectDialog;

	@AfterViews
	protected void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_user,
				R.string.data_requesting);
		dialog.setCancelable(false);

		setCustomTitle(R.string.system_tip);
	}

	@Click(R.id.btn_bind_device)
	protected void bindDeviceClick() {
		startDeviceBindActivity();
	}

	@Click(R.id.btn_monitor_device)
	protected void monitorDevice() {

		if (null == monitorSelectDialog) {
			monitorSelectDialog = DialogUtil.defaultSingleSelectDialog(
					R.array.monitor_select_list, null, REQUEST_MONITER_SELECT);
		}
		monitorSelectDialog.show(getSupportFragmentManager(), "monitor_select");
	}

	private void startDeviceBindActivity() {
		DeviceBindActivity_.intent(this).start();
	}

	@Click(R.id.btn_ignore)
	protected void onIgnoreClicked() {
		startMain(MenuItems.HOME);
	}

	@Override
	public void onClick(DialogInterface clickDialog, int which) {

		if (which == DialogInterface.BUTTON_POSITIVE) {
			startDeviceBindActivity();
			// bindDevice();
		}

	}

	@Override
	public void onClick(DialogInterface nullDialog, long id, int requestId) {

		monitorSelectDialog.dismiss();

		if (requestId != REQUEST_MONITER_SELECT) {
			return;
		}

		// 地图定位
		if (id == MONITOR_MAP) {
			startMain(MenuItems.MAP);
		} else if (id == MONITOR_ALARM) {
			startMain(MenuItems.ALARM);
		}

	}

	private void startMain(MenuItems initMenu) {

		MainActivity_.intent(this).initMenuItem(initMenu).start();

		finish();

	}

}
