package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
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
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.helper.MenuHelper;
import cn.com.cml.dbl.helper.MenuItems;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.DefaultDialogFragment.OnItemClickListener;
import cn.volley.toolbox.ClearCacheRequest;

@EFragment(R.layout.fragment_menu)
public class MenuFragment extends Fragment implements OnItemClickListener,
		MenuHelper.OnMenuSelectedListener {

	private static final String TAG = "MenuFragment";

	public static final String ACTION_MENU_CHANGE = "cn.com.cml.dbl.view.MenuFragment.ACTION_MENU_CHANGE";
	public static final String EXTRA_MENUITEM = "cn.com.cml.dbl.view.MenuFragment.EXTRA_MENUITEM";

	@FragmentArg
	MenuItems initMenuItem = MenuItems.HOME;// 初始化时默认的菜单

	@ViewById(R.id.menu_username)
	TextView usernameView;

	@ViewById(R.id.menu_score)
	TextView scoreView;

	private BroadcastReceiver itemChangeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();

			Log.d(TAG, "收到广播:" + action);

			if (ACTION_MENU_CHANGE.equals(action)
					&& intent.hasExtra(EXTRA_MENUITEM)) {

				int menuId = intent.getIntExtra(EXTRA_MENUITEM, -1);
				// toggleMenu(menuId, false);
				menuHelper.onMenuSelected(getActivity().findViewById(menuId));
			}
		}
	};

	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(ACTION_MENU_CHANGE);
		getActivity().registerReceiver(itemChangeReceiver, filter);
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(itemChangeReceiver);
		super.onDestroy();
	}

	@Bean
	MenuHelper menuHelper;

	@AfterViews
	public void initConfig() {

		menuHelper.setFragmentManager(getFragmentManager());
		menuHelper.setMenuSelectedListener(this);

		menuHelper.onMenuSelected(getActivity().findViewById(
				initMenuItem.getId()));
	}

	@Click(R.id.menu_logout)
	public void logoutClicked() {
		menuHelper.clearSelected();
		DialogUtil.defaultDialog(R.string.exit_confirm, 1, this).show(
				getFragmentManager(), "logout");

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

	@Override
	public void onMenuSelected(View menu, MenuItems item) {
		closeMenu();
		MainActivity ac = (MainActivity) getActivity();
		ac.setCustomTitle(item.getTitle());
	}
}
