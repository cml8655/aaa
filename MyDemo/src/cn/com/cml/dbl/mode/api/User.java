package cn.com.cml.dbl.mode.api;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser implements Serializable {

	private static final long serialVersionUID = -8836844838034995863L;

	private String nickName;
	private String lastLogin;
	private String lastChecking;

	private Integer score;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastChecking() {
		return lastChecking;
	}

	public void setLastChecking(String lastChecking) {
		this.lastChecking = lastChecking;
	}

	@Override
	public String toString() {
		return "username:" + getUsername() + "User [nickName=" + nickName
				+ ", lastLogin=" + lastLogin + ", lastChecking=" + lastChecking
				+ "]";
	}

}
