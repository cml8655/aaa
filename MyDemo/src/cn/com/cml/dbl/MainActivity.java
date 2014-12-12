package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;

import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import cn.com.cml.dbl.model.RequestModel;
import cn.com.cml.dbl.net.PetsApiHelper;
import cn.com.cml.dbl.view.MenuFragment_;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity {

	public static enum MenuItems {

		MOBILE_MONITOR(0), SMS_ALARM(1), COMMAND_DESC(2), SETTING(3);

		private int id;

		private MenuItems(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

	}

	@RestService
	PetsApiHelper apiHelper;

	@Extra
	MenuItems initMenuItem;// 初始化时默认的菜单

	@ViewById(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;

	private ActionBarDrawerToggle mDrawerToggle;

	// 菜单栏
	private SparseArray<Fragment> menuItems;

	@AfterViews
	protected void initLayouts() {

		// 耳机口监听
		// HeadsetService_.intent(this).start();

		menuItems = new SparseArray<Fragment>(5);

		// 设置初始化主界面
		if (null == initMenuItem) {
			initMenuItem = MenuItems.COMMAND_DESC;
		}

		this.changeContentView(initMenuItem);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.left_drawer, MenuFragment_.builder().build());
		transaction.commit();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
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

	/**
	 * 切换主界面信息
	 * 
	 * @param id
	 *            界面信息id
	 */
	public void changeContentView(MenuItems items) {

		Fragment fragment = menuItems.get(items.getId());

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

	@Background
	protected void request() {
		try {
			apiHelper.getRestTemplate().setRequestFactory(
					new HttpComponentsClientHttpRequestFactory());

			apiHelper.setRestErrorHandler(new RestErrorHandler() {

				@Override
				public void onRestClientExceptionThrown(RestClientException e) {
					Log.e("TAG", "RestClientException", e);
					e.printStackTrace();
				}
			});
			Log.d("TAG", "执行请求");
			// String request = apiHelper.index();
			// Map<String, String> params = new HashMap<String, String>();
			// params.put("11", "v11");
			// params.put("22", "v22");
			RequestModel model = new RequestModel();
			model.setUsername("111");
			model.setPasswrod("pp");
			Log.d("TAG", "请求返回" + apiHelper.indexWithModel(model));
		} catch (Exception e) {
			Log.e("TAG", "", e);
		}
	}

	@OptionsItem(R.id.action_settings)
	protected void settingItemClick() {
		Toast.makeText(this, "菜单。。。", Toast.LENGTH_LONG).show();
	}

	public void closeMenu() {
		mDrawerLayout.closeDrawers();
	}
}
