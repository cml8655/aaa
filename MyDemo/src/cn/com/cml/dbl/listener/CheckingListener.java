package cn.com.cml.dbl.listener;


public interface CheckingListener {

	public void onFail();

	public void onSuccess(int series,int gainScore);

}
