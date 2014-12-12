package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import cn.com.cml.dbl.MainActivity;
import cn.com.cml.dbl.R;

import com.baidu.mapapi.map.SupportMapFragment;

@EFragment(R.layout.fragment_menu)
public class MenuFragment extends Fragment {

	private SparseArray<Fragment> menus = new SparseArray<Fragment>(5);

	private int selectedId = -1;

	@AfterViews
	public void initConfig() {
	}

	@Click(value = { R.id.menu_home, R.id.menu_photo, R.id.menu_sms,
			R.id.menu_volume, R.id.menu_monitor })
	public void click(View clickView) {

		final int id = clickView.getId();

		// 点击当前菜单
		if (id == selectedId) {
			closeMenu();
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.setCustomAnimations(R.anim.right_in, R.anim.left_fadeout,
				R.anim.right_fadein, R.anim.left_fadeout);

		Fragment fragment = null;

		switch (id) {

		case R.id.menu_photo:

			if (menus.get(R.id.menu_photo) == null) {
				fragment = CameraScanFragment_.builder().build();
				transaction.add(R.id.content_frame, fragment);
				menus.append(id, fragment);
			} else {
				fragment = menus.get(R.id.menu_photo);
			}

			break;

		case R.id.menu_sms:

			if (menus.get(R.id.menu_sms) == null) {
				fragment = MessageFragment_.builder().build();
				transaction.add(R.id.content_frame, fragment);
				menus.append(id, fragment);
			} else {
				fragment = menus.get(R.id.menu_sms);
			}

			break;

		case R.id.menu_monitor:
			if (menus.get(R.id.menu_monitor) == null) {
				fragment = BaiduApiFragment_.builder().build();
				transaction.add(R.id.content_frame, fragment);
				menus.append(id, fragment);
			} else {
				fragment = menus.get(R.id.menu_monitor);
			}

			break;

		case R.id.menu_command:

			if (menus.get(R.id.menu_command) == null) {
				fragment = SupportMapFragment.newInstance();
				transaction.add(R.id.content_frame, fragment);
				menus.append(id, fragment);
			} else {
				fragment = menus.get(R.id.menu_command);
			}
			break;

		case R.id.menu_home:// 暂时替代为百度地图
			if (menus.get(R.id.menu_command) == null) {
				fragment = MobileMonitorFragment_.builder().build();
				transaction.add(R.id.content_frame, fragment);
				menus.append(id, fragment);
			} else {
				fragment = menus.get(R.id.menu_command);
			}
			// break;
			// if (menus.get(R.id.menu_home) == null) {
			// fragment = UserInfoFragment_.builder().build();
			// transaction.add(R.id.content_frame, fragment);
			// menus.append(id, fragment);
			// } else {
			// fragment = menus.get(R.id.menu_home);
			// }
			break;

		case R.id.menu_volume:
			if (menus.get(R.id.menu_volume) == null) {
				fragment = VolumeControlFragment_.builder().build();
				transaction.add(R.id.content_frame, fragment);
				menus.append(id, fragment);
			} else {
				fragment = menus.get(R.id.menu_volume);
			}
			break;
		}

		// transaction.addToBackStack(null);

		if (selectedId != -1) {
			transaction.hide(menus.get(selectedId));
		}

		transaction.show(fragment);

		transaction.commit();

		selectedId = id;

		closeMenu();

	}

	private void closeMenu() {
		((MainActivity) getActivity()).closeMenu();
	}
}
