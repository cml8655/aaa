package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.listener.BaseSaveListener;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import cn.com.cml.dbl.util.DialogUtil;

@EFragment(R.layout.fragment_suggest)
public class SuggestFragment extends Fragment {

	@Bean
	ApiRequestServiceClient apiClient;

	@ViewById(R.id.suggest_text)
	EditText suggestText;

	private DialogFragment dialog;

	@AfterViews
	void afterViews() {

		dialog = DialogUtil.dataLoadingDialog();
		dialog.setCancelable(false);

	}

	@Click(R.id.suggest_send)
	void onSuggestSend() {

		final String text = suggestText.getText().toString().trim();

		if (TextUtils.isEmpty(text)) {
			DialogUtil.toast(getActivity(), R.string.suggest_empty);
			return;
		}

		dialog.show(getFragmentManager(), "suggest");

		apiClient.suggestAdd(text, new BaseSaveListener(getActivity(), dialog) {
			@Override
			public void onSuccess() {

				super.onSuccess();

				Activity ac = getActivity();

				if (null != ac && !ac.isFinishing()) {
					DialogUtil.toast(getActivity(), R.string.suggest_success);
					suggestText.setText(null);
				}

			}

		});

	}

}
