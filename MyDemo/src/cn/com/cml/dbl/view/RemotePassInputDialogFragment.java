package cn.com.cml.dbl.view;

import org.androidannotations.annotations.EFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import cn.com.cml.dbl.R;

@EFragment
public class RemotePassInputDialogFragment extends DefaultDialogFragment {

	public interface OnPassFinishListener {
		public void onClick(DialogInterface dialog, long id, int requestId,
				String pass);
	}

	private OnPassFinishListener listener;
	private EditText passView;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Fragment target = getTargetFragment();
		Activity activity = getActivity();

		if (target instanceof OnPassFinishListener) {
			listener = (OnPassFinishListener) target;
		} else if (activity instanceof OnPassFinishListener) {
			listener = (OnPassFinishListener) activity;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		if (null != positiveBtnText) {
			builder.setPositiveButton(positiveBtnText, this);
		}

		if (null != negativeBtnText) {
			builder.setNegativeButton(negativeBtnText, this);
		}
		passView = (EditText) LayoutInflater.from(getActivity()).inflate(
				R.layout.view_remotepass_input, null);

		builder.setView(passView);

		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		if (null != listener) {
			String pass = passView.getText().toString();
			listener.onClick(dialog, which, requestId, pass);
		}
	}

}
