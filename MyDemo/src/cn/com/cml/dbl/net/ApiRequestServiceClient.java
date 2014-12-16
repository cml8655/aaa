package cn.com.cml.dbl.net;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.SaveListener;
import cn.com.cml.dbl.PetApplication;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.mode.api.InstallationModel;
import cn.com.cml.dbl.mode.api.MobileBind;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.model.PushModel;
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
	public void bindDevice(final String pass, final SaveListener listener) {

		final String deviceId = PetApplication.deviceId;

		// 更新所有已经绑定的数据
		this.bindDeviceQuery(new FindListener<MobileBind>() {

			@Override
			public void onError(int arg0, String arg1) {
				if (null != listener) {
					listener.onFailure(arg0, arg1);
				}
			}

			@Override
			public void onSuccess(List<MobileBind> datas) {

				for (MobileBind data : datas) {
					data.setBindType(MobileBind.TYPE_UNBIND);
					data.update(context);
				}

				MobileBind bindUpdate = new MobileBind();

				bindUpdate.setUser(BmobUser.getCurrentUser(context));
				bindUpdate.setBindType(MobileBind.TYPE_BIND);
				bindUpdate.setBindPassword(pass);
				bindUpdate.setImei(deviceId);

				// 重新绑定
				bindUpdate.save(context, listener);
			}
		});

	}

	@Override
	public void bindDeviceQuery(FindListener listener) {

		User user = BmobUser.getCurrentUser(context, User.class);

		BmobQuery<MobileBind> mobileBindQuery = new BmobQuery<MobileBind>();

		mobileBindQuery.addWhereEqualTo("user", user).addWhereEqualTo(
				"bindType", MobileBind.TYPE_BIND);

		mobileBindQuery.findObjects(context, listener);

	}

	@Override
	public void bindCurrentDeviceQuery(FindListener listener) {

		BmobQuery<MobileBind> mobileBindQuery = new BmobQuery<MobileBind>();

		mobileBindQuery.addWhereEqualTo("bindType", MobileBind.TYPE_BIND)
				.addWhereEqualTo("imei", PetApplication.deviceId)
				.include("user");

		mobileBindQuery.findObjects(context, listener);

	}

	@Override
	public void sendPushCommand(Constant.Command command, String imei,
			PushListener listener) {

		PushModel model = new PushModel();

		model.setCommand(command.getCommand());
		model.setEndTime(System.currentTimeMillis() + command.getEndTime());
		model.setFromUserName(BmobUser.getCurrentUser(context).getUsername());
		model.setBindPass(command.getBindPass());

		BmobPushManager<InstallationModel> pushManager = new BmobPushManager<InstallationModel>(
				context);

		Gson gson = new Gson();

		BmobQuery<InstallationModel> deviceQuery = InstallationModel.getQuery();

		// BmobQuery<InstallationModel> deviceQuery = new
		// BmobQuery<InstallationModel>();
		deviceQuery.addWhereEqualTo("imei", imei);

		pushManager.setQuery(deviceQuery);

		pushManager.pushMessage(gson.toJson(model), listener);
	}

	@Override
	public void bindPassQuery(String username, String bindPass,
			FindListener<MobileBind> listener) {

		BmobQuery<MobileBind> bindQuery = new BmobQuery<MobileBind>();

		BmobQuery<BmobUser> userQuery = new BmobQuery<BmobUser>();
		userQuery.addWhereEqualTo("username", username);
		userQuery.setLimit(1);

		bindQuery.addWhereMatchesQuery("user", BmobUser.class.getSimpleName(),
				userQuery);

		bindQuery.addWhereEqualTo("bindType", MobileBind.TYPE_BIND);
		bindQuery.addWhereEqualTo("bindPassword", bindPass);

		bindQuery.findObjects(context, listener);
	}

}
