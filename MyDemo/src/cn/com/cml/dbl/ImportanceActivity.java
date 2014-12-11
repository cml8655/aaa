package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.Button;
import cn.bmob.v3.BmobUser;
import cn.com.cml.dbl.listener.BaseSaveListener;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.util.DeviceUtil;
import cn.com.cml.dbl.util.DialogUtil;

/**
 * 通知信息
 * 
 * @author Administrator
 * 
 */
@EActivity(R.layout.activity_importance)
public class ImportanceActivity extends BaseActivity implements OnClickListener {

	@ViewById(R.id.btn_bind_device)
	Button bindDeviceBtn;

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

	private void startDeviceBindActivity() {
		DeviceBindActivity_.intent(this).start();
	}

	@Override
	public void onClick(DialogInterface clickDialog, int which) {

		if (which == DialogInterface.BUTTON_POSITIVE) {
			startDeviceBindActivity();
			// bindDevice();
		}

	}

}
