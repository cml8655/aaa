package cn.com.cml.dbl.view;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.R;

@EFragment(R.layout.fragment_menu)
public class MenuFragment extends Fragment {

	@Click(value = { R.id.menu_home, R.id.menu_icon, R.id.menu_map,
			R.id.menu_photo, R.id.menu_sms, R.id.menu_volume })
	public void click(View clickView) {

		final int id = clickView.getId();

		Fragment fragment = null;

		switch (id) {

		case R.id.menu_photo:
			fragment = CameraScanFragment_.builder().build();
			break;

		case R.id.menu_sms:
			fragment = MessageFragment_.builder().build();
			break;

		case R.id.menu_map:
			fragment = BaiduApiFragment_.builder().build();
			break;

		case R.id.menu_home:
			fragment = UserInfoFragment_.builder().build();
			break;

		case R.id.menu_volume:
			fragment = VolumeControlFragment_.builder().build();
			break;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(R.anim.right_in, R.anim.left_fadeout,
				R.anim.right_fadein, R.anim.left_fadeout);
		transaction.replace(R.id.content_frame, fragment);
		transaction.commit();

		((MainActivity) getActivity()).closeMenu();

	}
}
