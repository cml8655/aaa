package cn.com.cml.dbl.net;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.SaveListener;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.mode.api.MobileBind;

public interface ApiRequestService {

	void login(String username, String password, SaveListener listener);

	void signUp(String username, String password, SaveListener listener);

	void bindDevice(final String pass, final SaveListener listener);

	void bindDeviceQuery(FindListener listener);

	void bindPassQuery(String username, String bindPass,
			FindListener<MobileBind> listener);

	/**
	 * 查找是否绑定此手机
	 * 
	 * @param listener
	 */
	void bindCurrentDeviceQuery(FindListener listener);

	/**
	 * 给绑定的手机发送指令
	 * 
	 * @param command
	 */
	void sendPushCommand(Constant.Command command, String deviceImei,
			PushListener listener);

}
