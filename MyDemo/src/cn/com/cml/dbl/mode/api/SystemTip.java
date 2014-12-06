package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

public class SystemTip extends BmobObject {

	public static final int TIP_SHOW = 1;
	public static final int TIP_NO = 0;

	private User user;
	private String title;
	private String content;
	private int showFlg;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getShowFlg() {
		return showFlg;
	}

	public void setShowFlg(int showFlg) {
		this.showFlg = showFlg;
	}

}
