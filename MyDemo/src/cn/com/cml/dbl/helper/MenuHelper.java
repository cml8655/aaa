package cn.com.cml.dbl.helper;

import java.nio.channels.IllegalSelectorException;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import cn.com.cml.dbl.ModalActivity_;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.SettingFragment_;

@EBean
public class MenuHelper {

	public static interface OnMenuSelectedListener {
		public void onMenuSelected(View menu, MenuItems item);
	}

	private static final String TAG = "MenuHelper";

	private View lastSelectView;
	private SparseArray<Fragment> menus = new SparseArray<Fragment>(5);
	private OnMenuSelectedListener menuSelectedListener;

	@RootContext
	protected Context mContext;

	private FragmentManager fragmentManager;

	@Click(value = { R.id.menu_home, R.id.menu_alarm, R.id.menu_monitor,
			R.id.menu_suggest, R.id.menu_secure, R.id.menu_userinfo })
	public void onMenuSelected(View v) {

		MenuItems menuItem = MenuItems.getById(v.getId());

		if (null == menuItem) {
			throw new IllegalSelectorException();
		}

		// 选中菜单
		if (v != lastSelectView) {

			v.setSelected(true);

			if (null != lastSelectView) {
				lastSelectView.setSelected(false);
			}

			replaceContaner(menuItem, v.getId());

			lastSelectView = v;
		}

		// 回调菜单选中监听
		if (null != menuSelectedListener) {
			menuSelectedListener.onMenuSelected(v, menuItem);
		}

	}

	
	@Click(R.id.menu_setting)
	public void onSettingCliked(View v) {

		if (v != lastSelectView) {
			if (lastSelectView != null) {
				lastSelectView.setSelected(false);
			}
			lastSelectView = v;
		}

		v.setSelected(true);

		ModalActivity_.intent(mContext).container(SettingFragment_.class)
				.fragmentTitle(R.string.menu_setting).start();

	}
	public void clearSelected(){
		if(null!=lastSelectView){
			lastSelectView.setSelected(false);
		}
	}

	private void replaceContaner(MenuItems menuItem, int id) {

		FragmentTransaction transaction = fragmentManager.beginTransaction();

		transaction.setCustomAnimations(R.anim.right_in, R.anim.left_fadeout,
				R.anim.right_fadein, R.anim.left_fadeout);

		Fragment fragment = menus.get(id);

		if (null == fragment) {

			try {
				fragment = menuItem.getClazz().newInstance();
				menus.append(id, fragment);
			} catch (Exception e) {

				Log.e(TAG, "实例化菜单失败");
				return;
			}

		}

		transaction.replace(R.id.content_frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void toggleMenu(int menuId) {

		if (lastSelectView == null) {
			if (lastSelectView.getId() != menuId) {

			}
		}

	}

	public void setMenuSelectedListener(
			OnMenuSelectedListener menuSelectedListener) {
		this.menuSelectedListener = menuSelectedListener;
	}

	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}

}
