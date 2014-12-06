package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

public class AppDownload extends BmobObject {

	private String appName;
	private String appDesc;
	private long size;
	private String downloadUrl;
	private String iconUrl;
	/**
	 * 可获得积分
	 */
	private int signScore;
	private int priority;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getSignScore() {
		return signScore;
	}

	public void setSignScore(int signScore) {
		this.signScore = signScore;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
