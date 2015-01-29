package cn.com.cml.dbl;

import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import cn.bmob.v3.BmobUser;
import cn.com.cml.dbl.helper.MenuItems;
import cn.com.cml.dbl.listener.CheckingListener;
import cn.com.cml.dbl.mode.api.User;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.AnimUtils;
import cn.com.cml.dbl.util.CommonUtils;
import cn.com.cml.dbl.util.DialogUtil;
import cn.com.cml.dbl.view.MenuFragment;
import cn.com.cml.dbl.view.MenuFragment_;
import cn.com.cml.dbl.view.UserInfoFragment;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity {

	@Bean
	ApiRequestServiceClient apiClient;

	@Extra
	MenuItems initMenuItem = MenuItems.HOME;// 初始化时默认的菜单

	@ViewById(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;

	@ViewById(R.id.over_view_container)
	RelativeLayout overViewLayout;

	@Extra
	public boolean isBindDevice;// 是否绑定了此手机

	private boolean backClicked;

	private Handler handler = new Handler();

	private ActionBarDrawerToggle mDrawerToggle;

	private boolean todayChecking = true;

	@AfterViews
	protected void initLayouts() {

		// 检查是否已经签到
		todayCheckingCheck();

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.left_drawer, MenuFragment_.builder()
				.initMenuItem(initMenuItem).build());
		transaction.commit();

		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setLogo(R.drawable.ic_home);
		setCustomTitle(R.string.home);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				android.R.color.transparent, R.string.app_name,
				R.string.app_name) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.closeDrawers();

		getFragmentManager().addOnBackStackChangedListener(
				new OnBackStackChangedListener() {

					@Override
					public void onBackStackChanged() {

						boolean showIndicator = getFragmentManager()
								.getBackStackEntryCount() > 0;

						mDrawerToggle.setDrawerIndicatorEnabled(!showIndicator);
					}
				});

	}

	@Background
	void todayCheckingCheck() {

		User user = BmobUser
				.getCurrentUser(getApplicationContext(), User.class);

		String lastCheckingDateStr = user.getLastChecking();

		// 没有签到记录
		if (null == lastCheckingDateStr) {
			todayCheckingResult(false);
			return;
		}

		Date lastCheckingDate = CommonUtils.parseDate(lastCheckingDateStr,
				CommonUtils.FORMAT_YMD, new Date());

		todayCheckingResult(CommonUtils.isDateBefore(lastCheckingDate, 0));

	}

	@UiThread
	void todayCheckingResult(boolean check) {
		if (!check) {
			todayChecking = false;
			invalidateOptionsMenu();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@OptionsItem(R.id.menu_checking)
	protected void settingItemClick() {

		if (todayChecking) {
			return;
		}

		if (!CommonUtils.isNetworkConnected(getApplicationContext())) {

			DialogUtil.showTip(getApplicationContext(),
					getString(R.string.network_error));
			return;
		}

		// 提前设置为已签到
		todayChecking = true;

		// 今日未签到
		AnimUtils.checkingAnim(overViewLayout);

		apiClient.dailyChecking(new CheckingListener() {

			@Override
			public void onSuccess(int series, int score) {

				String checkingTip = getString(R.string.checking_start);

				if (series > 0) {

					checkingTip = getString(R.string.checking_series, series,
							score);

				}

				DialogUtil.showTip(getApplicationContext(), checkingTip);

				Intent intent = new Intent(
						UserInfoFragment.ACTION_USERINFO_CHANGE);
				intent.putExtra(UserInfoFragment.EXTRA_SCORE, score);
				sendBroadcast(intent);

				todayChecking = true;
				invalidateOptionsMenu();
			}

			@Override
			public void onFail() {
				// 还原签到设置，防止连续点击导致bug
				todayChecking = false;
				invalidateOptionsMenu();
				DialogUtil.showTip(getApplicationContext(),
						getString(R.string.checking_fail));
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if (todayChecking) {
			return false;
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && !backClicked) {

			backClicked = true;

			DialogUtil.toast(this, R.string.click_exit);

			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					backClicked = false;
				}
			}, 1000);

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void logout() {
		//
		// List<Fragment> fragments =
		// getSupportFragmentManager().getFragments();
		//
		// StringBuffer buffer = new StringBuffer();
		//
		// for (Fragment fragment : fragments) {
		//
		// if (fragment instanceof RemainTask) {
		//
		// RemainTask task = (RemainTask) fragment;
		//
		// if(task.hasTask()){
		// buffer.append(task.taskTip()).append("\n");
		// }
		//
		// }
		//
		// }

		LoginActivity_.intent(this).start();
		finish();
	}

	@Override
	protected void onDestroy() {

		BmobUser.logOut(getApplicationContext());
		super.onDestroy();
	}

	public void closeMenu() {
		mDrawerLayout.closeDrawers();
	}

	public void changeContent(int menuId) {

		Intent intent = new Intent(MenuFragment.ACTION_MENU_CHANGE);
		intent.putExtra(MenuFragment.EXTRA_MENUITEM, menuId);
		sendBroadcast(intent);

	}

}
