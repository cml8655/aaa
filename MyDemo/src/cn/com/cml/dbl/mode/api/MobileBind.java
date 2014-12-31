package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class MobileBind extends BmobObject {

	public static final int TYPE_BIND = 0;
	public static final int TYPE_UNBIND = 1;

	private BmobUser user;
	private String imei;
	/**
	 * 手机绑定密码
	 */
	private String bindPassword;
	/**
	 * 0 :绑定 ，1：解绑
	 */
	private int bindType;
	/**
	 * 设备名称
	 */
	private String deviceName;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public BmobUser getUser() {
		return user;
	}

	public void setUser(BmobUser user) {
		this.user = user;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getBindPassword() {
		return bindPassword;
	}

	public void setBindPassword(String bindPassword) {
		this.bindPassword = bindPassword;
	}

	public int getBindType() {
		return bindType;
	}

	public void setBindType(int bindType) {
		this.bindType = bindType;
	}

}
