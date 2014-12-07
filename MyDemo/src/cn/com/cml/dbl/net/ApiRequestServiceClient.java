package cn.com.cml.dbl.net;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.util.CommonUtils;

@EBean
public class ApiRequestServiceClient implements ApiRequestService {

	@RootContext
	Context context;

	@Override
	public void login(String username, String password, SaveListener listener) {

		User user = new User();

		// BmobUser user = new BmobUser();
		password = CommonUtils.encodeSHA1(password);

		user.setUsername(username);
		user.setPassword(password);

		user.login(context, listener);

	}

	@Override
	public void signUp(String username, String password, SaveListener listener) {

		User user = new User();
		// BmobUser user = new BmobUser();

		password = CommonUtils.encodeSHA1(password);

		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(username);
		user.setLastLogin(String.valueOf(System.currentTimeMillis()));
		user.setNickName(username);
		user.setLastChecking("");
		user.setScore(0);

		user.signUp(context, listener);
	}

	@Override
	public void modifyLastLoginTime() {

		User user = BmobUser.getCurrentUser(context, User.class);
		user.setLastLogin(String.valueOf(System.currentTimeMillis()));
		user.update(context);
	}

}
