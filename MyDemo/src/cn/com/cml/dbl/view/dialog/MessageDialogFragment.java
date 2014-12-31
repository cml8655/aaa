package cn.com.cml.dbl.view.dialog;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.ui.FontIconTextView;

/**
 * 提示对话框
 * 
 * 2014年12月31日
 */
@EFragment
public class MessageDialogFragment extends BaseDialogFragment {

	@FragmentArg
	Integer dialogText;

	@FragmentArg
	Integer iconAnim;

	@FragmentArg
	Integer iconText;

	private class MessageDialog extends Dialog {

		public MessageDialog(Context context) {
			super(context, R.style.dialogStyle);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			View view = getLayoutInflater().inflate(R.layout.view_dialog_msg,
					null);

			setContentView(view);

			FontIconTextView iconView = (FontIconTextView) view
					.findViewById(R.id.dialog_icon);

			iconView.setText(iconText);

			if (null != iconAnim) {

				iconView.startAnimation(AnimationUtils.loadAnimation(
						getActivity(), iconAnim));
			}

			TextView dialogTextView = (TextView) view
					.findViewById(R.id.dialog_msg);

			dialogTextView.setText(dialogText);
		}

	}

	@Override
	protected Dialog getCustomDialog() {
		return new MessageDialog(getActivity());
	}
}
