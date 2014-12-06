package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

public class SystemConfig extends BmobObject {

	private int loginSocre;
	/**
	 * 推荐获得积分
	 */
	private int recommendScore;
	private int unbindSocre;

	private String recommendTitle;
	private String recommendContent;

	private String downloadTitle;
	private String downloadContent;

	private String rankRaiseTitle;
	private String rankRaiseContent;

	public String getRankRaiseTitle() {
		return rankRaiseTitle;
	}

	public void setRankRaiseTitle(String rankRaiseTitle) {
		this.rankRaiseTitle = rankRaiseTitle;
	}

	public String getRankRaiseContent() {
		return rankRaiseContent;
	}

	public void setRankRaiseContent(String rankRaiseContent) {
		this.rankRaiseContent = rankRaiseContent;
	}

	public int getLoginSocre() {
		return loginSocre;
	}

	public void setLoginSocre(int loginSocre) {
		this.loginSocre = loginSocre;
	}

	public int getRecommendScore() {
		return recommendScore;
	}

	public void setRecommendScore(int recommendScore) {
		this.recommendScore = recommendScore;
	}

	public int getUnbindSocre() {
		return unbindSocre;
	}

	public void setUnbindSocre(int unbindSocre) {
		this.unbindSocre = unbindSocre;
	}

	public String getRecommendTitle() {
		return recommendTitle;
	}

	public void setRecommendTitle(String recommendTitle) {
		this.recommendTitle = recommendTitle;
	}

	public String getRecommendContent() {
		return recommendContent;
	}

	public void setRecommendContent(String recommendContent) {
		this.recommendContent = recommendContent;
	}

	public String getDownloadTitle() {
		return downloadTitle;
	}

	public void setDownloadTitle(String downloadTitle) {
		this.downloadTitle = downloadTitle;
	}

	public String getDownloadContent() {
		return downloadContent;
	}

	public void setDownloadContent(String downloadContent) {
		this.downloadContent = downloadContent;
	}

}
