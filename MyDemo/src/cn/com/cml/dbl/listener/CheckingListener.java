package cn.com.cml.dbl.listener;

import cn.com.cml.dbl.mode.api.ScoreHistory;

public interface CheckingListener {

	public void onFail();

	public void onSuccess(int series);

}
