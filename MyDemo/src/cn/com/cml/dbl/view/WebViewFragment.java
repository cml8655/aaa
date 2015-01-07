package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.com.cml.dbl.R;

@EFragment(R.layout.fragment_webview)
public class WebViewFragment extends Fragment {

	@FragmentArg()
	String mLoadUrl;

	@FragmentArg("errorUrl")
	String mErrorUrl;

	@ViewById(R.id.webview_container)
	WebView mWebView;

	@AfterViews
	protected void afterView() {

		mWebView.loadUrl(mLoadUrl);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				view.loadUrl(mErrorUrl);
			}
		});

	}
}
