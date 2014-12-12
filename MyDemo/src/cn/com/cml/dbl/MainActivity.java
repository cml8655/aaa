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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import cn.com.cml.dbl.model.RequestModel;
import cn.com.cml.dbl.net.PetsApiHelper;
import cn.com.cml.dbl.view.MenuFragment.MenuItems;
import cn.com.cml.dbl.view.MenuFragment_;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity {

	@RestService
	PetsApiHelper apiHelper;

	@Extra
	MenuItems initMenuItem = MenuItems.HOME;// 初始化时默认的菜单

	@ViewById(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;

	private ActionBarDrawerToggle mDrawerToggle;

	@AfterViews
	protected void initLayouts() {

		// 耳机口监听
		// HeadsetService_.intent(this).start();
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
