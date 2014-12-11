package cn.com.cml.dbl.net;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public interface ApiRequestService {

	void login(String username, String password, SaveListener listener);

	void signUp(String username, String password, SaveListener listener);

	void modifyLastLoginTime();

	void bindDevice(final String pass, final SaveListener listener);

	void bindDeviceQuery(FindListener listener);

	/**
	 * 查找是否绑定此手机
	 * 
	 * @param listener
	 */
	void bindCurrentDeviceQuery(FindListener listener);

}
