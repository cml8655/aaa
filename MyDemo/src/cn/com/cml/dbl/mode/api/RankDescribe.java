package cn.com.cml.dbl.mode.api;

import cn.bmob.v3.BmobObject;

/**
 * 等级说明
 * 
 * @author Administrator
 * 
 */
public class RankDescribe extends BmobObject {

	private String rankName;
	private int rankScore;

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public int getRankScore() {
		return rankScore;
	}

	public void setRankScore(int rankScore) {
		this.rankScore = rankScore;
	}

}
