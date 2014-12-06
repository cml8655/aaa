package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

public class MobileBind extends BmobObject {

	private User user;
	private String imei;
	private Long bindTime;
	/**
	 * 手机绑定密码
	 */
	private String bindPassword;
	/**
	 * 0 :绑定 ，1：解绑
	 */
	private int bindType;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Long getBindTime() {
		return bindTime;
	}

	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
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
