package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

public class SystemConfig extends BmobObject {
	
	private static final long serialVersionUID = -4071694871277479633L;
	private int checkingScore;
	private int checkingMaxScore;
	private int checkingSeriesScore;// 连续登陆获得的积分
	private int recommendScore;
	
	public int getCheckingSeriesScore() {
		return checkingSeriesScore;
	}

	public void setCheckingSeriesScore(int checkingSeriesScore) {
		this.checkingSeriesScore = checkingSeriesScore;
	}

	public int getCheckingMaxScore() {
		return checkingMaxScore;
	}

	public void setCheckingMaxScore(int checkingMaxScore) {
		this.checkingMaxScore = checkingMaxScore;
	}

	public int getCheckingScore() {
		return checkingScore;
	}

	public void setCheckingScore(int checkingScore) {
		this.checkingScore = checkingScore;
	}

	public int getRecommendScore() {
		return recommendScore;
	}

	public void setRecommendScore(int recommendScore) {
		this.recommendScore = recommendScore;
	}

}
