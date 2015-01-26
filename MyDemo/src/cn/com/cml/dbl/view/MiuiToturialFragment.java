package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.webkit.WebView;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.util.AppUtil;

@EFragment(R.layout.fragment_toturial)
public class MiuiToturialFragment extends Fragment {

	@ViewById(R.id.webview)
	WebView webview;

	@AfterViews
	public void afterView() {
		webview.loadUrl(Constant.Url.URL_SECURE_MIUI);
	}

	@Click(R.id.setting_btn)
	public void onSettingClicked() {

		AppUtil.setAppPriority(getActivity());

	}
}
