package cn.com.cml.dbl.view;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.com.cml.dbl.LoginActivity_;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.PrefUtil_;
import android.support.v4.app.Fragment;

@EFragment(R.layout.fragment_introduce3)
public class Introduce3Fragment extends Fragment {

	@Pref
	PrefUtil_ pref;

	@Click(R.id.entrance_btn)
	public void onEntranceClicked() {

		String currentVersion = AppUtil.version(getActivity());
		pref.edit().introduceVersion().put(currentVersion).apply();
		LoginActivity_.intent(this).start();
		getActivity().finish();

	}

}
