package cn.com.cml.dbl.view;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import cn.com.cml.dbl.ModalActivity;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.ui.TopTipView;
import cn.com.cml.dbl.ui.TopTipView_;

public class BaseFragment extends Fragment {

	public void showNiftyTip(String text) {
		this.showNiftyTip(text, R.string.icon_mobile, R.id.mobile_monitor_tip_container);
	}

	protected void changeContainer(Fragment target, Integer titleId) {

		ModalActivity ac = (ModalActivity) getActivity();
		ac.setCustomTitle(titleId);

		FragmentManager manager = getFragmentManager();

		FragmentTransaction transaction = manager.beginTransaction();
		transaction.setCustomAnimations(R.anim.right_in, R.anim.left_fadeout,
				R.anim.right_fadein, R.anim.left_fadeout);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.add(R.id.modal_container, target);
		// transaction.replace(R.id.modal_container, target);
		transaction.addToBackStack(null);

		List<Fragment> fragments = manager.getFragments();

		if (fragments.size() > 0) {
			transaction.hide(fragments.get(0));
		}
		transaction.commit();
	}

	public void showNiftyTip(String text, int iconRes, int containerId) {

		final ViewGroup parent = (ViewGroup) getActivity().findViewById(
				containerId);

		parent.removeAllViews();

		TopTipView tipView = TopTipView_.build(getActivity(),
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						parent.removeAllViews();
					}
				}, text, getString(iconRes));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		int margin = getActivity().getResources().getDimensionPixelSize(
				R.dimen.half_default_margin);

		params.bottomMargin = margin;
		params.topMargin = margin;

		parent.addView(tipView, params);
		tipView.show();

	}
}
