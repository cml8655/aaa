package cn.com.cml.dbl.view;

import cn.com.cml.dbl.R;
import cn.com.cml.dbl.util.DialogUtil;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	protected void showNiftyTip(String text) {
		DialogUtil.showNiftyTip(getActivity(), text, R.id.over_view_container);
	}

	protected void showNiftyTip(int textId) {
		DialogUtil.showNiftyTip(getActivity(), getString(textId),
				R.id.over_view_container);
	}

	protected void showNiftyTip(int textId, int containerId) {
		DialogUtil.showNiftyTip(getActivity(), getString(textId), containerId);
	}

	protected void showNiftyTip(String text, int containerId) {
		DialogUtil.showNiftyTip(getActivity(), text, containerId);
	}
}
