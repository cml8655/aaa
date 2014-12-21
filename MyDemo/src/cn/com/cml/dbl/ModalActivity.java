package cn.com.cml.dbl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

@OptionsMenu(R.menu.modal)
@EActivity(R.layout.activity_modal)
public class ModalActivity extends BaseActivity {

	private static final String TAG = "ModalActivity";

	@Extra
	Class<? extends Fragment> container;

	@Extra
	int fragmentTitle;

	@AfterViews
	void afterViews() {

		try {

			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			setCustomTitle(fragmentTitle);

			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.modal_container, container.newInstance());
			transaction.commit();

		} catch (Exception e) {
			Log.e(TAG, "ModalActivity.newInstance", e);
		}

	}

	@OptionsItem(android.R.id.home)
	public void onKeyBackClick() {
		onBackPressed();
	}
}
