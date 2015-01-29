package cn.com.cml.dbl.helper;

import android.support.v4.app.Fragment;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.view.AlarmFragment_;
import cn.com.cml.dbl.view.HomeFragment_;
import cn.com.cml.dbl.view.MobileMonitorFragment_;
import cn.com.cml.dbl.view.SecureSetFragment_;
import cn.com.cml.dbl.view.SuggestFragment_;
import cn.com.cml.dbl.view.UserInfoFragment_;

public enum MenuItems {

	HOME(R.id.menu_home, HomeFragment_.class, R.string.menu_home), //
	MAP(R.id.menu_monitor, MobileMonitorFragment_.class, R.string.menu_monitor), //
	ALARM(R.id.menu_alarm, AlarmFragment_.class, R.string.menu_alarm), // ..
	USERINFO(R.id.menu_userinfo, UserInfoFragment_.class,
			R.string.menu_userinfo), //
	SECURE(R.id.menu_secure, SecureSetFragment_.class, R.string.menu_secure), // 安全设置
	SUGGEST(R.id.menu_suggest, SuggestFragment_.class, R.string.menu_suggest);//

	private int id;
	private Class<? extends Fragment> clazz;
	private int title;

	public static MenuItems getById(int id) {

		MenuItems[] values = MenuItems.values();

		for (MenuItems value : values) {
			if (value.getId() == id) {
				return value;
			}
		}

		return null;
	}

	private MenuItems(int id, Class<? extends Fragment> clazz, int title) {
		this.id = id;
		this.clazz = clazz;
		this.title = title;
	}

	public int getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	public Class<? extends Fragment> getClazz() {
		return clazz;
	}

}
