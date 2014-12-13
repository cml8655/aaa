package cn.com.cml.dbl.mode.api;

import android.content.Context;
import cn.bmob.v3.BmobInstallation;

public class InstallationModel extends BmobInstallation {

	private String imei;

	public InstallationModel(Context context) {
		super(context);
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}
