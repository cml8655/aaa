package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;

import com.special.ResideMenu.FontIconResideMenuItem;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import cn.com.cml.dbl.model.RequestModel;
import cn.com.cml.dbl.net.PetsApiHelper;
import cn.com.cml.dbl.view.BaiduApiFragment_;
import cn.com.cml.dbl.view.CameraScanFragment_;
import cn.com.cml.dbl.view.MessageFragment_;
import cn.com.cml.dbl.view.UserInfoFragment_;
import cn.com.cml.dbl.view.VolumeControlFragment_;
import cn.com.cml.pets.R;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseMainActivity {

	@RestService
	PetsApiHelper apiHelper;

	private int currentFragmentIndex = -1;

	@AfterViews
	public void show() {
		// request();
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

	@Override
	public void onClick(View v) {

		FontIconResideMenuItem clickView = (FontIconResideMenuItem) v;

		if (resideMenu.isOpened()) {
			resideMenu.closeMenu();
		}

		final int index = clickView.getItemIndex();

		if (index == currentFragmentIndex) {
			return;
		} else {
			currentFragmentIndex = index;
		}

		Fragment fragment = null;

		switch (v.getId()) {

		case 0:
			fragment = CameraScanFragment_.builder().build();
			break;

		case 1:
			fragment = MessageFragment_.builder().build();
			// fragment = OpenGLFragment_.builder().build();
			break;

		case 2:
			fragment = BaiduApiFragment_.builder().build();
			break;

		case 3:
			fragment = UserInfoFragment_.builder().build();

		case 4:
			fragment = VolumeControlFragment_.builder().build();

			break;
		}

		if (null != fragment) {
			super.replaceContainer(fragment);
		}

	}
}
