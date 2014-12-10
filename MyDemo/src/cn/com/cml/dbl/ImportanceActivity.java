package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

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
public class ImportanceActivity extends BaseActivity {

	@Extra
	boolean hasBindDevice;

	@ViewById(R.id.btn_bind_device)
	Button bindDeviceBtn;

	@AfterViews
	protected void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_user,
				R.string.login_request);
		dialog.setCancelable(false);

		setCustomTitle(R.string.system_tip);
	}

	@Click(R.id.btn_bind_device)
	protected void bindDeviceClick() {

		dialog.show(getSupportFragmentManager(), "bind");

		// 提示用户会先解绑其他手机
		if (hasBindDevice) {

			DialogUtil.toast(getApplicationContext(),
					R.string.system_bind_device_confirm);

		} else {

			MobileBind bind = new MobileBind();

			bind.setBindPassword("1234");// TODO 设置
			bind.setBindType(MobileBind.TYPE_BIND);
			bind.setImei(DeviceUtil.deviceImei(this));
			bind.setUser(BmobUser.getCurrentUser(this));

			bind.save(this, new BaseSaveListener(this, dialog) {
				@Override
				public void onSuccess() {

					Log.d(TAG, "手机信息绑定成功!");
					bindDeviceBtn.setClickable(false);
					bindDeviceBtn
							.setText(getString(R.string.system_bind_device_success));
					super.onSuccess();
				}
			});
		}

	}
}
