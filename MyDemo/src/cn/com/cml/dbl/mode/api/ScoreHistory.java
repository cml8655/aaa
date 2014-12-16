package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

/**
 * 积分记录
 * 
 * @author Administrator
 * 
 */
public class ScoreHistory extends BmobObject {

	public static final int TYPE_CHECKING = 0;
	public static final int TYPE_RECOMMEND = 1;
	public static final int TYPE_DOWNLOAD = 2;
	public static final int TYPE_OTHER = 3;

	private User user;
	/**
	 * 0:签到。1：推荐 ，2：下载 ，3：其他
	 */
	private int scoreType;
	private int score;
	private String scoreDescribe;
	private int series;// 连续号，start with 0


	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getScoreType() {
		return scoreType;
	}

	public void setScoreType(int scoreType) {
		this.scoreType = scoreType;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getScoreDescribe() {
		return scoreDescribe;
	}

	public void setScoreDescribe(String scoreDescribe) {
		this.scoreDescribe = scoreDescribe;
	}

}
