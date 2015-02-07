package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.com.cml.dbl.LoginActivity_;
import cn.com.cml.dbl.ModalActivity_;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.AppUtil;
import cn.com.cml.dbl.util.PrefUtil_;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.widget.Button;

@EFragment(R.layout.fragment_introduce3)
public class Introduce3Fragment extends Fragment {

	@Pref
	PrefUtil_ pref;

	@ViewById(R.id.secure_btn)
	Button secureButton;

	@AnimationRes(R.anim.shake)
	Animation shakeAnim;

	@AfterViews
	public void afterViews() {
		secureButton.startAnimation(shakeAnim);
	}

	@Click(R.id.entrance_btn)
	public void onEntranceClicked() {

		String currentVersion = AppUtil.version(getActivity());
		pref.edit().introduceVersion().put(currentVersion).apply();
		LoginActivity_.intent(this).start();
		getActivity().finish();

	}

	@Click(R.id.secure_btn)
	public void onSecureClicked() {

		ModalActivity_.intent(getActivity())
				.container(SecureSetFragment_.class)
				.fragmentTitle(R.string.menu_secure).start();

	}

}
