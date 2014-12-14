package cn.com.cml.dbl.listener;

import java.util.List;

import android.content.Context;
import cn.bmob.v3.listener.FindListener;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DialogUtil;

public class BaseFindListener<T> extends FindListener<T> {

	private Context context;

	public BaseFindListener(Context context) {
		this.context = context;
	}

	@Override
	public void onError(int errorCode, String errorMsg) {
		if (errorCode == 9016) {
			DialogUtil.toast(context, R.string.network_error);
		}
	}

	@Override
	public void onSuccess(List<T> result) {
	}
}
