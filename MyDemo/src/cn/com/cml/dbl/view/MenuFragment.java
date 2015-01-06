package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.ModalActivity_;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;

@EFragment(R.layout.fragment_menu)
public class MenuFragment extends Fragment implements OnItemClickListener {

	private static final String TAG = "MenuFragment";

	public static final String ACTION_MENU_CHANGE = "cn.com.cml.dbl.view.MenuFragment.ACTION_MENU_CHANGE";
	public static final String ACTION_USERINFO_CHANGE = "cn.com.cml.dbl.view.MenuFragment.ACTION_USERINFO_CHANGE";

	public static final String EXTRA_MENUITEM = "cn.com.cml.dbl.view.MenuFragment.EXTRA_MENUITEM";
	public static final String EXTRA_SCORE = "cn.com.cml.dbl.view.MenuFragment.EXTRA_SCORE";

	public static enum MenuItems {

		HOME(R.id.menu_home, HomeFragment_.class, R.string.menu_home), //
		MAP(R.id.menu_monitor, MobileMonitorFragment_.class,
				R.string.menu_monitor), //
		ALARM(R.id.menu_alarm, AlarmFragment_.class, R.string.menu_alarm), //..
		USERINFO(R.id.menu_userinfo, UserInfoFragment_.class, R.string.menu_userinfo), //
		SECURE(R.id.menu_secure, SecureSetFragment_.class, R.string.menu_secure), // 安全设置
		SUGGEST(R.id.menu_suggest, SuggestFragment_.class,
				R.string.menu_suggest);//

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

	private SparseArray<Fragment> menus = new SparseArray<Fragment>(5);

	private int selectedId = -1;

	@FragmentArg
	MenuItems initMenuItem = MenuItems.HOME;// 初始化时默认的菜单

	@ViewById(R.id.menu_username)
	TextView usernameView;

	@ViewById(R.id.menu_score)
	TextView scoreView;

	@ViewById(R.id.menu_rank)
	TextView rankView;

	private BroadcastReceiver itemChangeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();

			Log.d(TAG, "收到广播:" + action);

			if (ACTION_USERINFO_CHANGE.equals(action)) {

				scoreView.setText(getString(R.string.menu_score_text,
						intent.getIntExtra(EXTRA_SCORE, 0)));

				return;
			}

			if (ACTION_MENU_CHANGE.equals(action)
					&& intent.hasExtra(EXTRA_MENUITEM)) {

				Activity activity = getActivity();
				if (null != activity && !activity.isFinishing()) {

					int menuId = intent.getIntExtra(EXTRA_MENUITEM, -1);
					toggleMenu(menuId, false);
				}
			}
		}
	};

	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(ACTION_MENU_CHANGE);
		filter.addAction(ACTION_USERINFO_CHANGE);
		getActivity().registerReceiver(itemChangeReceiver, filter);
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(itemChangeReceiver);
		super.onDestroy();
	}

	@AfterViews
	public void initConfig() {

		final int id = initMenuItem.getId();

		Fragment initFragment = null;

		try {
			initFragment = initMenuItem.getClazz().newInstance();
		} catch (Exception e) {
			Log.e(TAG, "initClass error", e);
			initFragment = HomeFragment_.builder().build();
		}

		selectedId = id;
		menus.put(id, initFragment);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.add(R.id.content_frame, initFragment);
		transaction.commit();

		initUserMessage();

	}

	private void initUserMessage() {

		User user = BmobUser.getCurrentUser(getActivity(), User.class);

		usernameView.setText(user.getUsername());
		scoreView.setText(getString(R.string.menu_score_text, user.getScore()));
		// TODO 获取级别
		rankView.setText(getString(R.string.menu_rank_text, "一级"));
	}

	@Click(value = { R.id.menu_home, R.id.menu_alarm, R.id.menu_monitor,
			R.id.menu_suggest, R.id.menu_secure ,R.id.menu_userinfo})
	public void click(View clickView) {

		final int id = clickView.getId();

		toggleMenu(id, true);
	}

	@Click(R.id.menu_setting)
	public void settingClicked() {
		ModalActivity_.intent(getActivity()).container(SettingFragment_.class)
				.fragmentTitle(R.string.menu_setting).start();
	}

//	@Click(R.id.menu_userinfo)
//	public void userinfoClicked() {
//		ModalActivity_.intent(getActivity()).container(UserInfoFragment_.class)
//				.fragmentTitle(R.string.menu_userinfo).start();
//	}

	@Click(R.id.menu_logout)
	public void logoutClicked() {
		DialogUtil.defaultDialog(R.string.exit_confirm, 1, this).show(
				getFragmentManager(), "logout");
	}

	private void toggleMenu(int id, boolean leftMenuShow) {

		// 点击当前菜单
		if (id == selectedId) {

			if (leftMenuShow) {
				closeMenu();
			}

			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.setCustomAnimations(R.anim.right_in, R.anim.left_fadeout,
				R.anim.right_fadein, R.anim.left_fadeout);

		MenuItems menu = MenuItems.getById(id);

		if (null == menu) {
			return;
		}

		Fragment fragment = menus.get(id);

		if (null == fragment) {

			try {

				fragment = menu.getClazz().newInstance();
				transaction.add(R.id.content_frame, fragment);
				menus.append(id, fragment);

			} catch (Exception e) {

				Log.e(TAG, "实例化菜单失败");
				return;
			}

		}

		if (selectedId != -1) {
			transaction.hide(menus.get(selectedId));
		}

		transaction.show(fragment);

		transaction.commit();

		((MainActivity) getActivity()).setCustomTitle(menu.getTitle());

		selectedId = id;

		if (leftMenuShow) {
			closeMenu();
		}
	}

	private void closeMenu() {
		((MainActivity) getActivity()).closeMenu();
	}

	@Override
	public void onClick(DialogInterface dialog, long id, int requestId) {
		if (id == DialogInterface.BUTTON_POSITIVE) {
			MainActivity ac = (MainActivity) getActivity();
			ac.logout();
		}
	}
}
