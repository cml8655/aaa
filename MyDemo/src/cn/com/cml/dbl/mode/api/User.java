package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {

	private String nickName;
	private BmobFile headImg;
	private Long lastLogin;
	private Long lastChecking;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public BmobFile getHeadImg() {
		return headImg;
	}

	public void setHeadImg(BmobFile headImg) {
		this.headImg = headImg;
	}

	public Long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getLastChecking() {
		return lastChecking;
	}

	public void setLastChecking(Long lastChecking) {
		this.lastChecking = lastChecking;
	}

}
