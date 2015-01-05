package cn.com.cml.dbl.view;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.ui.TopTipView;
import cn.com.cml.dbl.ui.TopTipView_;
import cn.com.cml.dbl.util.DialogUtil;

public class BaseFragment extends Fragment {

	protected void showNiftyTip(String text) {
		DialogUtil.showNiftyTip(getActivity(), text, R.id.over_view_container);
	}

	protected void showNiftyTip(String text, int containerId) {

		final ViewGroup parent = (ViewGroup) getActivity().findViewById(
				containerId);

		parent.removeAllViews();

		TopTipView tipView = TopTipView_.build(getActivity(),
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						parent.removeAllViews();
					}
				}, text, getString(R.string.icon_setting));

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
