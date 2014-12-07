package cn.com.cml.dbl.listener;

import android.content.Context;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public abstract class BaseSaveListener extends SaveListener {

	private Context context;

	public BaseSaveListener(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onFailure(int errorCode, String msg) {

		switch (errorCode) {

		case 9005:
			showError("批量操作只支持最多50条");
			break;

		case 9006:
			showError("objectId为空");
			break;

		case 9009:
			showError("没有缓存数据");
			break;

		case 9010:
			showError("网络超时");
			break;

		case 9015:
			showError("其他错误");

		case 9016:
			showError("无网络连接，请检查您的手机网络");
			break;

		default:
			showError(msg);
		}
	}

	public void showError(String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSuccess() {

	}

}
