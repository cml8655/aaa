package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import cn.com.cml.dbl.util.DialogUtil;

/**
 * 通知信息
 * 
 * @author Administrator
 * 
 */
@EActivity(R.layout.activity_importance)
public class ImportanceActivity extends BaseActivity {
	@AfterViews
	protected void initConfig() {

		dialog = DialogUtil.notifyDialogBuild(R.string.icon_user,
				R.string.login_request);
		dialog.setCancelable(false);

		setCustomTitle(R.string.system_tip);
	}
}
