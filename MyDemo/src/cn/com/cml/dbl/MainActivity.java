package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import cn.com.cml.dbl.model.RequestModel;
import cn.com.cml.dbl.net.PetsApiHelper;
import cn.com.cml.dbl.view.MenuFragment_;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends FragmentActivity {

	@RestService
	PetsApiHelper apiHelper;

	@ViewById(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;

	private ActionBarDrawerToggle mDrawerToggle;

	@AfterViews
	protected void initLayouts() {

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.left_drawer, MenuFragment_.builder().build());
		transaction.commit();

		final ActionBar actionBar = getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		actionBar.setLogo(R.drawable.ic_home);

		View customView = getLayoutInflater().inflate(R.layout.view_actionbar,
				null);

		LayoutParams params = new LayoutParams(Gravity.CENTER
				| Gravity.CENTER_VERTICAL);
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;

		actionBar.setCustomView(customView, params);

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

	// @Override
	// public void onClick(View v) {
	//
	// FontIconResideMenuItem clickView = (FontIconResideMenuItem) v;
	//
	// if (resideMenu.isOpened()) {
	// resideMenu.closeMenu();
	// }
	//
	// final int index = clickView.getItemIndex();
	//
	// if (index == currentFragmentIndex) {
	// return;
	// } else {
	// currentFragmentIndex = index;
	// }
	//
	// Fragment fragment = null;
	//
	// switch (v.getId()) {
	//
	// case 0:
	// fragment = CameraScanFragment_.builder().build();
	// break;
	//
	// case 1:
	// fragment = MessageFragment_.builder().build();
	// // fragment = OpenGLFragment_.builder().build();
	// break;
	//
	// case 2:
	// fragment = BaiduApiFragment_.builder().build();
	// break;
	//
	// case 3:
	// fragment = UserInfoFragment_.builder().build();
	//
	// case 4:
	// fragment = VolumeControlFragment_.builder().build();
	//
	// break;
	// }
	//
	// if (null != fragment) {
	// super.replaceContainer(fragment);
	// }
	//
	// }
}
