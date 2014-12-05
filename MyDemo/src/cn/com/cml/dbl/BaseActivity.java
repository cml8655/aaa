package cn.com.cml.dbl;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends Activity {

	public static final String TAG = "BaseActivity";

	protected TextView titleView;
	protected ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setupActionBar();
		Log.d(TAG, "BaseActivity-->onCreate");
	}

	protected void setupActionBar() {

		actionBar = this.getActionBar();

		View customView = getLayoutInflater().inflate(R.layout.view_actionbar,
				null);

		titleView = (TextView) customView.findViewById(R.id.actionbar_title);

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, Gravity.CENTER
						| Gravity.CENTER_VERTICAL);

		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setCustomView(customView, params);

	}

	protected void setCustomTitle(String text) {

		if (null != titleView) {
			titleView.setText(text);
		}
	}

	protected void setCustomTitle(int titleId) {
		setCustomTitle(getString(titleId));
	}
}
