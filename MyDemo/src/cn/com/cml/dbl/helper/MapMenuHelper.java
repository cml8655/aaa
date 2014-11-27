package cn.com.cml.dbl.helper;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import cn.com.cml.dbl.R;

@EBean
public class MapMenuHelper {

	public static enum MenuType {
		TYPE_SETTING, TYPE_USER, TYPE_MOBILE
	}

	public static interface OnMenuClickListener {
		void onClick(View v, MenuType menuType);
	}

	private OnMenuClickListener clickListener;

	@RootContext
	Context context;

	@Click(R.id.map_menu_setting)
	public void settingClick(View v) {

		if (null != clickListener) {
			clickListener.onClick(v, MenuType.TYPE_SETTING);
		}

	}

	@Click(R.id.map_menu_user)
	public void userClick(View v) {

		if (null != clickListener) {
			clickListener.onClick(v, MenuType.TYPE_USER);
		}

	}

	@Click(R.id.map_menu_mobile)
	public void mobileClick(View v) {

		if (null != clickListener) {
			clickListener.onClick(v, MenuType.TYPE_MOBILE);
		}
	}

	public void bindListener(OnMenuClickListener listener) {
		this.clickListener = listener;
	}

}
