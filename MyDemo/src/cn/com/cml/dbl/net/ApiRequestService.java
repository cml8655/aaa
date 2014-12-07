package cn.com.cml.dbl.net;

import cn.bmob.v3.listener.SaveListener;
import cn.com.cml.dbl.mode.api.User;

public interface ApiRequestService {

	void login(String username, String password, SaveListener listener);

	void signUp(String username, String password, SaveListener listener);

	void modifyLastLoginTime();

}
