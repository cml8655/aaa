package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

/**
 * 用户推荐下载
 * 
 * @author Administrator
 * 
 */
public class Recommend extends BmobObject {

	public static final int DOWNLOAD_OK = 1;
	public static final int DOWNLOAD_NO = 0;

	private String toUser;
	private String fromUser;
	/**
	 * 0：为下载 1：已下载
	 */
	private int downloadFlg;

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public int getDownloadFlg() {
		return downloadFlg;
	}

	public void setDownloadFlg(int downloadFlg) {
		this.downloadFlg = downloadFlg;
	}

}
