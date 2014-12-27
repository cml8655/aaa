package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

public class Suggestion extends BmobObject {

	private String text;
	private String username;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
